package com.example.educatif.Utils;

import com.example.educatif.model.Lesson;
import com.example.educatif.model.Login;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitLessonInterface {
        @GET("/api/activity/all")
        Call<Lesson> GetAllYoutube();
}
