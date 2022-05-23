package com.example.educatif.model;

public class Login {
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
