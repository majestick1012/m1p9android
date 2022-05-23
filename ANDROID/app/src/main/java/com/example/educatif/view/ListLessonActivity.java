package com.example.educatif.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educatif.R;
import com.example.educatif.Utils.RetrofitInterface;
import com.example.educatif.Utils.RetrofitLessonInterface;
import com.example.educatif.controller.LessonController;
import com.example.educatif.model.Lesson;
import com.example.educatif.model.LessonData;
import com.example.educatif.model.Login;
import com.example.educatif.model.Preferences;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListLessonActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    LessonController lessonController;
    private Retrofit retrofit;
    private RetrofitLessonInterface retrofitLessonInterface;
    private RetrofitInterface retrofitInterface;
    private SharedPreferences sharedPreferences;
    private LoadingDialog loadingDialog;
    List<LessonData> searchLesson = new ArrayList<>();
    Button buttonSearch;
    TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);
        String firstname = sp1.getString("firstname", null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            if(firstname != null && !firstname.isEmpty()){
                actionBar.setTitle("ABC | Bonjour, " + firstname);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lesson);
        linearLayout = findViewById(R.id.constraintLayoutVideo);
        lessonController = LessonController.getInstance();
        retrofit = new Retrofit.Builder().baseUrl(getString(R.string.urlAPI)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitLessonInterface = retrofit.create(RetrofitLessonInterface.class);
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        loadingDialog = new LoadingDialog(ListLessonActivity.this);
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        setListLesson();
        addSearchLesson();


        if(lessonController.preference==null){
            lessonController.preference = new Preferences(Color.BLACK,Color.WHITE,
                    1000,
                    500,
                    250,
                    130,
                    1000,
                    500,
                    1);
        }
        if(lessonController.preference.getBackgroundColor()==Color.BLACK){
            String uridark = "@drawable/bg_dark";
            int imageResourceDark = getResources().getIdentifier(uridark, null, getPackageName());
            //ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundResource(imageResourceDark);
            ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundColor(lessonController.preference.getBackgroundColor());
        }
        else {
            ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundColor(lessonController.preference.getBackgroundColor());
        }

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.xml.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(ListLessonActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed () {

    }

    public void logout()
    {
        String bearerToken = "Bearer ";
        String id = "";

        String tokenStored = sharedPreferences.getString("token", null);
        String userId = sharedPreferences.getString("id", null);
        if(tokenStored != null && !tokenStored.isEmpty()){
            bearerToken += tokenStored;
        }
        if(userId != null && !userId.isEmpty()){
            id = userId;
        }


        HashMap<String,String> map = new HashMap<>();
        map.put("id", id);
        Log.d("tag", bearerToken);
        Log.d("tag", id);

        Call<Login> call = retrofitInterface.executeLogout(bearerToken, map);
        loadingDialog.startLoadingDialog();

        SharedPreferences.Editor Ed = sharedPreferences.edit();
        Ed.clear();
        Ed.commit();

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                if(login!=null && login.getSuccess()) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    loadingDialog.dismissDialog();
                    Toast.makeText(getBaseContext(),R.string.logout_success,Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    loadingDialog.dismissDialog();
                    Toast.makeText(getBaseContext(),R.string.session_expired,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                // Error
                loadingDialog.dismissDialog();
                Toast.makeText(getBaseContext(),R.string.no_internet,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setListLesson()
    {
        loadingDialog.startLoadingDialog();
        if(lessonController.lesson==null){
            String token = sharedPreferences.getString("token", null);
            String bearerToken = "";
            if(token != null && !token.isEmpty()){
                bearerToken = "Bearer " + token;
            }
            Call<Lesson> call = retrofitLessonInterface.GetAllYoutube(bearerToken);
            call.enqueue(new Callback<Lesson>() {
                @Override
                public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                    Lesson result = response.body();
                    lessonController.lesson = result;
                    searchlessonlist(lessonController.lesson.getData());
                    if(result != null && !result.getSuccess()){
                        SharedPreferences.Editor Ed = sharedPreferences.edit();
                        Ed.clear();
                        Ed.commit();
                        Intent intent = new Intent(ListLessonActivity.this, LoginActivity.class);
                        startActivity(intent);
                        loadingDialog.dismissDialog();
                        Toast.makeText(getBaseContext(),R.string.session_expired,Toast.LENGTH_LONG).show();
                    }
                    loadingDialog.dismissDialog();
                }
                @Override
                public void onFailure(Call<Lesson> call, Throwable t) {
                    // Error
                    loadingDialog.dismissDialog();
                    Toast.makeText(ListLessonActivity.this,R.string.no_internet,Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            searchlessonlist(lessonController.lesson.getData());
            loadingDialog.dismissDialog();
        }
    }

    @SuppressLint("ResourceType")
    public void searchlessonlist(List<LessonData> lessonData){

        View view = getLayoutInflater().inflate(R.layout.theme, null);
        LinearLayout linearLayout = findViewById(R.id.constraintLayoutVideo);
        linearLayout.removeAllViews();
        tableLayout = view.findViewById(R.id.table_layout);
        int length = lessonData.size();
        int k = 0;
        while(1<2){
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.CENTER);
            TableRow texttableRow = new TableRow(this);
            texttableRow.setGravity(Gravity.CENTER);
            for(int i=0;i<lessonController.preference.getRows();i++){
                if(k>=length)break;
                CardView cardView = new CardView(view.getContext());
                CardView.LayoutParams layoutParamscardView = new CardView.LayoutParams(160,160);

                layoutParamscardView.height = (int)lessonController.preference.getImageHeight();
                layoutParamscardView.width = (int)lessonController.preference.getImageWidth();
                cardView.setRadius(50);
                ImageView imageView = new ImageView(view.getContext());
                ViewGroup.LayoutParams imageparams = new ViewGroup.LayoutParams(150,150);

                imageparams.height= (int)lessonController.preference.getImageHeight();
                imageparams.width=(int)lessonController.preference.getImageWidth();

                Picasso.get().load(lessonData.get(k).getImage()).into(imageView);
                imageView.setLayoutParams(imageparams);
                imageView.setBackgroundColor(Color.parseColor("#2bc48e"));


                cardView.addView(imageView,imageparams.width,imageparams.height);
                cardView.setLayoutParams(layoutParamscardView);
                tableRow.addView(new TextView(this),40,40);

                tableRow.addView(cardView,(int)lessonController.preference.getTextTableWidth(),(int)lessonController.preference.getTextTableHeight());


                imageView.setId(k);

                TextView textView = new TextView(view.getContext());
                ViewGroup.LayoutParams textparams = new ViewGroup.LayoutParams(150,300);

                textparams.height=(int)lessonController.preference.getTextParamsHeight();
                textparams.width=(int)lessonController.preference.getTextParamsWidth();

                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextColor(lessonController.preference.getForegroundColor());


                textView.setAllCaps(true);
                textView.setTextSize(20);
                textView.setText(lessonData.get(k).getTitle());

                textView.setLayoutParams(textparams);
                texttableRow.addView(new TextView(this),40,40);
                //texttableRow.addView(textView,(int)lessonController.preference.getTextParamsWidth(),(int)lessonController.preference.getTextParamsHeight());

                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        lessonController.lessonData = lessonData.get(imageView.getId());
                        Intent intent = new Intent(ListLessonActivity.this, LessonActivity.class);
                        startActivity(intent);
                    }
                });
                k++;
            }
            tableLayout.addView(tableRow);
            tableLayout.addView(texttableRow);
            if(k>=length)break;
        }
        linearLayout.addView(view);
        createNotif();
    }

    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public void createNotif(){
        String id=getAlphaNumericString(10);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel  = manager.getNotificationChannel(id);
            if(channel ==null){
                channel= new NotificationChannel(id,"Nombre de Lecon",NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Educative pour vos Enfants");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100,1000,200,340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);
                Intent notificationIntent =new Intent(this,NotificationActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id)
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo))
                        .setContentTitle("ABC application Educatives pour les Enfant")
                        .setContentText("La liste des Activités que vous rechercher compte "+Integer.toString(lessonController.lesson.getData().size()))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[]{100,1000,200,340})
                        .setAutoCancel(true)//tounch on notification menu dismissed,but swipe to dismis
                        .setTicker("notification");

                        builder.setContentIntent(contentIntent);
                        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());

                        //id to generate new notification in build
                        m.notify(1, builder.build());








            }
        }
    }

    public void addSearchLesson(){
        try{
             buttonSearch = findViewById(R.id.buttonSearch);
             TextInputEditText textInputEditText = findViewById(R.id.textSearch);
             buttonSearch.setOnClickListener(new Button.OnClickListener(){
                 @Override
                 public void onClick(View view) {
                     if(lessonController.lesson.getData()!=null){
                         searchLesson = new ArrayList<>();
                         List<LessonData> lessonData = lessonController.lesson.getData();
                         for(int i=0;i<lessonData.size();i++){
                             if(lessonData.get(i).getTitle().toLowerCase(Locale.ROOT).contains(textInputEditText.getText().toString().toLowerCase(Locale.ROOT))){
                                 searchLesson.add(lessonData.get(i));
                             }
                         }
                         Log.d("Info: ",""+searchLesson.size());
                     }
                     if(searchLesson == null) {
                         searchLesson = lessonController.lesson.getData();
                     }
                     searchlessonlist(searchLesson);
                 }
             });

        }
        catch (Exception ex){
            //Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT);
            Log.d("Error: ",ex.getMessage());
        }

    }

}