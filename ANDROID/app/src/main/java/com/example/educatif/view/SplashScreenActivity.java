package com.example.educatif.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.educatif.R;
import com.example.educatif.Utils.RetrofitLessonInterface;
import com.example.educatif.controller.LessonController;
import com.example.educatif.model.Lesson;
import com.example.educatif.model.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreenActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitLessonInterface retrofitLessonInterface;
    private String base_Url="https://m1p9android-jm.herokuapp.com";
    LessonController lessonController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        retrofit = new Retrofit.Builder().baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitLessonInterface = retrofit.create(RetrofitLessonInterface.class);
        setListLesson();

        lessonController.preference = new Preferences(Color.BLACK,Color.WHITE,
                1000,
                500,
                250,
                130,
                1000,
                500,
                1);


    }

    public void setListLesson()
    {
        lessonController = LessonController.getInstance();
        Call<Lesson> call = retrofitLessonInterface.GetAllYoutube();
        call.enqueue(new Callback<Lesson>() {
            @Override
            public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                lessonController.lesson = response.body();
                Log.d("SetLesson","lecon mis a jour");
                Toast.makeText(SplashScreenActivity.this,"Succès",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<Lesson> call, Throwable t) {
                Toast.makeText(SplashScreenActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}