package com.example.educatif.model;

import com.example.educatif.Utils.AccesHTTP;

public class Login {
   private String id;
   private String name;
   private String email;
   private String password;
   private String phoneNumber;

    /**
     * constructeur de login des parents
     * @param id
     * @param name
     * @param email
     * @param password
     * @param phoneNumber
     */
    public Login(String id,String name,String email,String password,String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Login(String name,String password) {
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setNom(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
