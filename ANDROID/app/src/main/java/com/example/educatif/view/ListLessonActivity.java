package com.example.educatif.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.educatif.R;
import com.example.educatif.Utils.RetrofitInterface;
import com.example.educatif.Utils.RetrofitLessonInterface;
import com.example.educatif.controller.LessonController;
import com.example.educatif.model.Lesson;
import com.example.educatif.model.LessonData;

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
                gotolesson(listLessonButton);
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
            button.setLayoutParams(linearLayout.getLayoutParams());
            button.setTextSize(22);
            button.setText(lessonData.getTitle());
            button.setHeight(100);
            button.setWidth(100);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            button.setId(i);
            buttons.add(button);
            linearLayout.addView(button);

            button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    lessonController.lessonData = lessonController.lesson.getData().get(button.getId());
                    Intent intent = new Intent(ListLessonActivity.this, LessonActivity.class);
                    startActivity(intent);
                }
            });
        }
   }
}