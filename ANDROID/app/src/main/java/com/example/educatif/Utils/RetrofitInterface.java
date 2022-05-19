package com.example.educatif.Utils;

import com.example.educatif.model.Login;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/finduser")
    Call<Login> executeLogin(@Body HashMap<String,String>map);

    @POST("/insertuser")
    Call<Void> executeSignUp(@Body HashMap<String,String>map);


}
