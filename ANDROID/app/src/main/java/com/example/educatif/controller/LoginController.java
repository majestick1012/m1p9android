package com.example.educatif.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.educatif.model.AccessApi;
import com.example.educatif.model.Login;

public final class LoginController {

    public Login login;
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
        login = new Login(name,password);
        AccessApi accessApi = new AccessApi();
        return accessApi.findUser(login,context);
    }

    public void InsertNewUser(Login login,Context context){
        AccessApi accessApi = new AccessApi();
        accessApi.insertUser(login,context);
    }

}
