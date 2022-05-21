package com.example.educatif.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListLessonActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    List<Button> listLessonButton = new ArrayList<>();
    LessonController lessonController;
    private Retrofit retrofit;
    private RetrofitLessonInterface retrofitLessonInterface;
    private String base_Url="https://m1p9android-jm.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lesson);
        linearLayout = findViewById(R.id.constraintLayoutVideo);
        lessonController = LessonController.getInstance();
        retrofit = new Retrofit.Builder().baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitLessonInterface = retrofit.create(RetrofitLessonInterface.class);
        setListLesson();

    }
    public void setListLesson()
    {
        Call<Lesson> call = retrofitLessonInterface.GetAllYoutube();
        call.enqueue(new Callback<Lesson>() {
            @Override
            public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                lessonController.lesson = response.body();
                //gotolesson(listLessonButton);
                constructiconlessonlist();
                Log.d("SetLesson","lecon mis a jour");
            }
            @Override
            public void onFailure(Call<Lesson> call, Throwable t) {
                //erreur connexion ou autre exception test 2
                Toast.makeText(ListLessonActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
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
    public void constructiconlessonlist(){
        View view = getLayoutInflater().inflate(R.layout.theme, null);
        TableLayout tableLayout = view.findViewById(R.id.table_layout);
        int length = lessonController.lesson.getData().size();
        int k = 0;
        while(1<2){
        TableRow tableRow = new TableRow(this);
        tableRow.setGravity(Gravity.CENTER);
        TableRow texttableRow = new TableRow(this);
        texttableRow.setGravity(Gravity.CENTER);
            for(int i=0;i<4;i++){
                if(k>=length)break;
                CardView cardView = new CardView(view.getContext());
                CardView.LayoutParams layoutParamscardView = new CardView.LayoutParams(160,160);
                layoutParamscardView.height = 160;
                layoutParamscardView.width = 160;
                cardView.setRadius(40);
                ImageView imageView = new ImageView(view.getContext());
                ViewGroup.LayoutParams imageparams = new ViewGroup.LayoutParams(150,150);
                imageparams.height=150;
                imageparams.width=150;
                Picasso.get().load(lessonController.lesson.getData().get(k).getImage()).into(imageView);
                imageView.setLayoutParams(imageparams);
                imageView.setBackgroundColor(Color.rgb(239,210,83));
                cardView.addView(imageView,imageparams);
                cardView.setLayoutParams(layoutParamscardView);
                tableRow.addView(new TextView(this),40,40);
                tableRow.addView(cardView,150,150);

                imageView.setId(k);

                TextView textView = new TextView(view.getContext());
                ViewGroup.LayoutParams textparams = new ViewGroup.LayoutParams(150,300);
                textparams.height=150;
                textparams.width=50;
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextColor(Color.BLACK);
                textView.setText(lessonController.lesson.getData().get(k).getTitle());
                textView.setLayoutParams(textparams);
                texttableRow.addView(new TextView(this),40,40);
                texttableRow.addView(textView,150,150);



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


}