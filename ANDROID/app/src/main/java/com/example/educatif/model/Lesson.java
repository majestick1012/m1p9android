package com.example.educatif.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Lesson {
 private String success;
 private String message;
 private List<LessonData> data;

 public Lesson(){

 }
public Lesson(String success,String message,List<LessonData>data){
    this.success = success;
    this.message = message;
    this.data = data;
}

    @SerializedName("success")
    public String getSuccess() {
        return success;
    }

    @SerializedName("message")
    public String getMessage() {
        return message;
    }

    @SerializedName("data")
    public List<LessonData> getData() {
        return data;
    }

}
