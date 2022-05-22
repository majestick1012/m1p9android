package com.example.educatif.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
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
import com.example.educatif.model.Preferences;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;
import java.util.ArrayList;
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
    private String base_Url="https://m1p9android-jm.herokuapp.com";
    SwitchCompat switchCompat;
    int color;
    private LoadingDialog loadingDialog;
    List<LessonData> searchLesson = new ArrayList<>();
    Button buttonSearch;
    TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lesson);
        linearLayout = findViewById(R.id.constraintLayoutVideo);
        lessonController = LessonController.getInstance();
        retrofit = new Retrofit.Builder().baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitLessonInterface = retrofit.create(RetrofitLessonInterface.class);
        loadingDialog = new LoadingDialog(ListLessonActivity.this);
        setListLesson();
        addSearchLesson();


        if(lessonController.preference==null){
           /* lessonController.preference = new Preferences(Color.parseColor("#2bc48e"),Color.WHITE,
                    1000,
                    500,
                    250,
                    130,
                    1000,
                    500,
                    1);*/

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
            ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundResource(imageResourceDark);

        }
        else {
            ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundColor(lessonController.preference.getBackgroundColor());
        }

    }
    public void setListLesson()
    {
        if(lessonController.lesson==null){
            Call<Lesson> call = retrofitLessonInterface.GetAllYoutube();
            loadingDialog.startLoadingDialog();
            call.enqueue(new Callback<Lesson>() {
                @Override
                public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                    lessonController.lesson = response.body();

                    //gotolesson(listLessonButton);
                    //color = Color.GRAY;
                    //constructiconlessonlist(color);
                    searchlessonlist(lessonController.lesson.getData());
                    loadingDialog.dismissDialog();
                    Log.d("SetLesson","lecon mis a jour");
                }
                @Override
                public void onFailure(Call<Lesson> call, Throwable t) {
                    //erreur connexion ou autre exception test 2
                    loadingDialog.dismissDialog();
                    Toast.makeText(ListLessonActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            loadingDialog.startLoadingDialog();
            loadingDialog.dismissDialog();
            searchlessonlist(lessonController.lesson.getData());
            loadingDialog.dismissDialog();
        }
    }

   public void gotolesson(List<Button>buttons){
        for(int i=0;i<lessonController.lesson.getData().size();i++){
            LessonData lessonData = lessonController.lesson.getData().get(i);
            Button button = new Button(this);
            ViewGroup.MarginLayoutParams test = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
            test.setMargins(25,0,25,25);
            test.height = 250;
            button.setLayoutParams(test);
            button.setTextSize(22);
            button.setTextColor(Color.parseColor("#FFFFFF"));
            button.setText(lessonData.getTitle());
            button.setHeight(100);
            button.setWidth(100);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_bg));
            //linearLayout.addView(button);

            View view = getLayoutInflater().inflate(R.layout.row, null);
            view.setId(i);
            TextView textViewToChange = (TextView) view.findViewById(R.id.title);
            textViewToChange.setText(lessonData.getTitle());
            textViewToChange.setAllCaps(true);

            TextView textviewDesc = (TextView) view.findViewById(R.id.artist);
            textviewDesc.setText(lessonData.getDescription());

            ImageView icone = (ImageView) view.findViewById(R.id.list_image);

            Picasso.get().load(lessonData.getImage()).into(icone);

            linearLayout.addView(view);
            linearLayout.addView(new TextView(this));


            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    lessonController.lessonData = lessonController.lesson.getData().get(view.getId());
                    Intent intent = new Intent(ListLessonActivity.this, LessonActivity.class);
                    startActivity(intent);
                }
            });
        }
   }


    @SuppressLint("ResourceType")
    public void constructiconlessonlist(int color){

       View view = getLayoutInflater().inflate(R.layout.theme, null);
        tableLayout = view.findViewById(R.id.table_layout);
        tableLayout.removeAllViews();
        int length = lessonController.lesson.getData().size();
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
                //CardView.LayoutParams layoutParamscardView = new CardView.LayoutParams(60,60);

                layoutParamscardView.height = (int)lessonController.preference.getImageHeight();
                layoutParamscardView.width = (int)lessonController.preference.getImageWidth();
                //layoutParamscardView.height = 200;
                //layoutParamscardView.width = 200;
                cardView.setRadius(50);
                ImageView imageView = new ImageView(view.getContext());
                ViewGroup.LayoutParams imageparams = new ViewGroup.LayoutParams(150,150);

                imageparams.height= (int)lessonController.preference.getImageHeight();
                imageparams.width=(int)lessonController.preference.getImageWidth();

                Picasso.get().load(lessonController.lesson.getData().get(k).getImage()).into(imageView);
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
                //textView.setTextColor(color);
                textView.setTextColor(lessonController.preference.getForegroundColor());

                textView.setAllCaps(true);
                textView.setTextSize(20);
                textView.setText(lessonController.lesson.getData().get(k).getTitle());

                textView.setLayoutParams(textparams);
                texttableRow.addView(new TextView(this),40,40);
                texttableRow.addView(textView,(int)lessonController.preference.getTextParamsWidth(),(int)lessonController.preference.getTextParamsHeight());

                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        lessonController.lessonData = lessonController.lesson.getData().get(imageView.getId());
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
                //CardView.LayoutParams layoutParamscardView = new CardView.LayoutParams(60,60);

                layoutParamscardView.height = (int)lessonController.preference.getImageHeight();
                layoutParamscardView.width = (int)lessonController.preference.getImageWidth();
                //layoutParamscardView.height = 200;
                //layoutParamscardView.width = 200;
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
                //textView.setTextColor(color);
                textView.setTextColor(lessonController.preference.getForegroundColor());


                textView.setAllCaps(true);
                textView.setTextSize(20);
                textView.setText(lessonData.get(k).getTitle());

                textView.setLayoutParams(textparams);
                texttableRow.addView(new TextView(this),40,40);
                texttableRow.addView(textView,(int)lessonController.preference.getTextParamsWidth(),(int)lessonController.preference.getTextParamsHeight());

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
    }

    public void buttonClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialogpreferences, null);
        switchCompat = alertLayout.findViewById(R.id.switchNight);

        String uri = "@drawable/bg_image";  // where myresource (without the extension) is the file
        String uridark = "@drawable/bg_dark";  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        int imageResourceDark = getResources().getIdentifier(uridark, null, getPackageName());
        switchCompat.setChecked(true);
        if (switchCompat.isChecked()){
            ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundResource(imageResourceDark);
            //constructiconlessonlist(Color.WHITE);
        }
        else {
            ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundResource(imageResource);
            //constructiconlessonlist(Color.BLACK);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (switchCompat.isChecked()){
                    ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundResource(imageResourceDark);
                    //constructiconlessonlist(Color.WHITE);
                }
                else {
                    color = Color.BLUE;
                    ListLessonActivity.this.findViewById(R.id.principalViewLesson).setBackgroundResource(imageResource);
                }
            }
        });

       AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Retour", (dialog, which) -> Toast.makeText(getBaseContext(), "Theme Modifier", Toast.LENGTH_SHORT).show());
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void buttonClickedPreferences(View view) {
        Intent intent = new Intent(ListLessonActivity.this, SettingsActivity.class);
        startActivity(intent);
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
                                 //Log.d("jererererer",searchLesson.get(i).getTitle());
                             }
                         }
                         Log.d("jererererer",""+searchLesson.size());
                     }

                     if(searchLesson!=null){
                         Toast.makeText(ListLessonActivity.this,searchLesson.size()+" result",Toast.LENGTH_SHORT).show();
                     }
                     else {
                         searchLesson = lessonController.lesson.getData();
                     }
                     searchlessonlist(searchLesson);
                 }
             });

        }
        catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT);
            Log.d("la message est ",ex.getMessage());
        }

    }

}