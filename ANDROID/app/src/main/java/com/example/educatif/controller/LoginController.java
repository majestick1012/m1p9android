package com.example.educatif.controller;

public final class LoginController {

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

}
