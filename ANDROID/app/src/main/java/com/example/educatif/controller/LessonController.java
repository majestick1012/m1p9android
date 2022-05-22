package com.example.educatif.controller;

import androidx.preference.Preference;

import com.example.educatif.model.Lesson;
import com.example.educatif.model.LessonData;
import com.example.educatif.model.Preferences;
import com.example.educatif.view.LessonActivity;

import java.lang.ref.Reference;

public final class LessonController {
    public Lesson lesson;
    private static LessonController lessonController = null;
    public LessonData lessonData;
    public Preferences preference;

    public static LessonController getInstance(){
        if(lessonController==null){
            lessonController = new LessonController();
        }
        return lessonController;
    }
}
