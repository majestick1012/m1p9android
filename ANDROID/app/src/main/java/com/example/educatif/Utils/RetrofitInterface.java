package com.example.educatif.Utils;

import com.example.educatif.model.Login;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/api/user/login")
    Call<Login> executeLogin(@Body HashMap<String,String>map);

    @POST("/api/user/signup")
    Call<Login> executeSignUp(@Body HashMap<String,String>map);

    @POST("/api/user/logout")
    Call<Login> executeLogout(@Header("authorization") String token , @Body HashMap<String,String>map);
}
