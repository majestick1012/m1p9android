package com.example.educatif.controller;

import com.example.educatif.model.Lesson;
import com.example.educatif.model.LessonData;
import com.example.educatif.view.LessonActivity;

public final class LessonController {
    public Lesson lesson;
    private static LessonController lessonController = null;
    public LessonData lessonData;

    public static LessonController getInstance(){
        if(lessonController==null){
            lessonController = new LessonController();
        }
        return lessonController;
    }
}
