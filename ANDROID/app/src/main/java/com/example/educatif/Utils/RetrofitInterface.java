package com.example.educatif.Utils;

import com.example.educatif.model.Login;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @Headers("authorization:token")
    @POST("/api/user/login")
    Call<Login> executeLogin(@Body HashMap<String,String>map);

    @Headers("authorization:token")
    @POST("/api/user/signup")
    Call<Login> executeSignUp(@Body HashMap<String,String>map);


}
