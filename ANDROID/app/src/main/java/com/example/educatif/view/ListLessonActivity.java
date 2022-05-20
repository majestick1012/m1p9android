package com.example.educatif.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.educatif.R;

public class ListLessonActivity extends AppCompatActivity {

    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lesson);
        linearLayout = findViewById(R.id.constraintLayoutVideo);
        listlesson();
    }

    public void listlesson(){

        Button button = new Button(this);
        button.setLayoutParams(linearLayout.getLayoutParams());
        button.setTextSize(22);
        button.setText("Excercice");
        button.setHeight(100);
        button.setWidth(100);
        button.setGravity(Gravity.CENTER_HORIZONTAL);

        Button button2 = new Button(this);
        button2.setLayoutParams(linearLayout.getLayoutParams());
        button2.setTextSize(22);
        button2.setText("Excercice");
        button2.setHeight(100);
        button2.setWidth(100);
        button2.setGravity(Gravity.CENTER_HORIZONTAL);

        Button button3 = new Button(this);
        button3.setLayoutParams(linearLayout.getLayoutParams());
        button3.setTextSize(22);
        button3.setText("Excercice");
        button3.setHeight(100);
        button3.setWidth(100);
        button3.setGravity(Gravity.CENTER_HORIZONTAL);

        linearLayout.addView(button);
        linearLayout.addView(button2);
        linearLayout.addView(button3);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("videoclick","onclick");
            }
        });

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ListLessonActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ListLessonActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ListLessonActivity.this, LessonActivity.class);
                startActivity(intent);
            }
        });
    }
}