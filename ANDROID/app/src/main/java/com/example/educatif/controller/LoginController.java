package com.example.educatif.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.educatif.Utils.RetrofitInterface;
import com.example.educatif.model.AccessApi;
import com.example.educatif.model.Login;
import com.example.educatif.view.LessonActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class LoginController {

    public Login login;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_Url="http://testnodeekaly.herokuapp.com";
    /**
     * controller du login
     */
    private LoginController(){
        super();
    }

    private static LoginController loginController = null;

    public static final LoginController getInstanceLogin(){
        if(loginController==null){
            loginController = new LoginController();
        }
        return LoginController.loginController;
    }

    public Intent verifyLogin(String name, String password, Context context) {
        login = new Login();
        AccessApi accessApi = new AccessApi();
        return accessApi.findUser(login,context);
    }

    public void InsertNewUser(Login login,Context context){
        AccessApi accessApi = new AccessApi();
        accessApi.insertUser(login,context);
    }




}
