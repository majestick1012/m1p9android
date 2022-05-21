package com.example.educatif.model;

import com.example.educatif.Utils.AccesHTTP;
import com.google.gson.annotations.SerializedName;

public class Login {
    private Double expiresIn;
    private boolean success;
    private String message;
    private LoginData data;

    public Login(){

    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public LoginData getData() {
        return data;
    }
}
