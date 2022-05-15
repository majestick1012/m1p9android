package com.example.educatif.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.VideoView;

import com.example.educatif.R;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {
    List<VideoView> LessonVideo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);





        //Uri video = Uri.parse("C:\\M1\\Mobile\\gitcheck\\m1p9android\\ANDROID\\app\\src\\main\\java\\com\\example\\educatif\\video\\videomp.mp4");
        //String video = "android.resource://" + getPackageName() + "/" + R.raw.aurora;
        //videoView.setVideoURI(Uri.parse(video));
        //videoView.start();
        //videoView.requestFocus();
        //LessonVideo.add(new VideoView(this));
        //LessonVideo.add(new VideoView(this));
        //LessonVideo.add(new VideoView(this));
        //LessonVideo.add(new VideoView(this));
        //LessonVideo.add(new VideoView(this));

        ScrollView sv= new ScrollView(this);//(ScrollView) findViewById(R.id.svScroll);
        sv.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT));

        String viduri = "android.resource://" + getPackageName() + "/" + R.raw.aurora;
        String viduri2 = "android.resource://" + getPackageName() + "/" + R.raw.videomp;


        LinearLayout layout = findViewById(R.id.constraintLayoutVideo);
       // layout.addView(sv);

        VideoView vid = new VideoView(this);
        vid.setVideoURI(Uri.parse(viduri));
        layout.addView(vid,1100,800);


        VideoView vid2 = new VideoView(this);
        vid2.setVideoURI(Uri.parse(viduri2));
        layout.addView(vid2,1100,800);

        VideoView vid3 = new VideoView(this);

        layout.addView(vid3,1100,800);
        vid3.setVideoURI(Uri.parse(viduri2));

        VideoView vid4 = new VideoView(this);
        layout.addView(vid4,1100,800);

        //vid.start();
        vid.start();
        vid2.start();



       // vid2.requestFocus();

    }

}