package com.example.educatif.Utils;

import com.example.educatif.model.Lesson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RetrofitLessonInterface {

        @GET("activity/all")
        Call<Lesson> GetAllYoutube(@Header("authorization") String token);
}
