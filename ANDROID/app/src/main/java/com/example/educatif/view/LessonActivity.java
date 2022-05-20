package com.example.educatif.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.educatif.R;
import com.example.educatif.Utils.RetrofitInterface;
import com.example.educatif.Utils.RetrofitLessonInterface;
import com.example.educatif.model.Lesson;
import com.example.educatif.model.Login;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LessonActivity extends YouTubeBaseActivity {
    //List<VideoView> LessonVideo = new ArrayList<>();
    List<Button> buttonExercice = new ArrayList<>();
    //initialise youtube
    YouTubePlayerView youTubePlayerView;
    YouTubePlayerView youTubePlayerView1;

    private Retrofit retrofit;
    private RetrofitLessonInterface retrofitLessonInterface;
    private String base_Url="https://m1p9android-jm.herokuapp.com";

    List<YouTubePlayerView> LessonVideo = new ArrayList<>();

    private int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);



        ScrollView sv= new ScrollView(this);//(ScrollView) findViewById(R.id.svScroll);
        sv.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT));

        String viduri = "android.resource://" + getPackageName() + "/" + R.raw.videotest;
        String viduri2 = "android.resource://" + getPackageName() + "/" + R.raw.videotest;
        String viduri3 = "android.resource://" + getPackageName() + "/" + R.raw.aurora;
        String viduri4 = "android.resource://" + getPackageName() + "/" + R.raw.videomp;

        LinearLayout layout = findViewById(R.id.constraintLayoutVideo);

        VideoView vid = new VideoView(this);
        vid.setVideoURI(Uri.parse(viduri));
        //LessonVideo.add(vid);
        //buttonExercice.add(button);

        VideoView vid2 = new VideoView(this);
        vid2.setVideoURI(Uri.parse(viduri2));
        //LessonVideo.add(vid2);
        //buttonExercice.add(button);

        VideoView vid3 = new VideoView(this);
        vid3.setVideoURI(Uri.parse(viduri3));
        //LessonVideo.add(vid3);
        //buttonExercice.add(button);

        VideoView vid4 = new VideoView(this);
        vid4.setVideoURI(Uri.parse(viduri4));
        //LessonVideo.add(vid4);
        //buttonExercice.add(button);


        //add youtube lesson video
       // LessonVideo.add(youTubePlayerView);
       // LessonVideo.add(youTubePlayerView);
       // LessonVideo.add(youTubePlayerView);


        for(j=0;j<LessonVideo.size();j++){
            LessonVideo.get(j).setLayoutParams(layout.getLayoutParams());
            LessonVideo.get(j).setId(j);
           layout.addView(LessonVideo.get(j),1100,800);

            //bouton config
            Button button = new Button(this);
            button.setLayoutParams(layout.getLayoutParams());
            button.setTextSize(22);
            button.setText("Excercice");
            button.setHeight(100);
            button.setWidth(100);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            buttonExercice.add(button);
            layout.addView(buttonExercice.get(j));
            buttonExercice.get(j).setVisibility(View.GONE);
            buttonExercice.get(j).setId(j);
            //playVideo(LessonVideo.get(j));
            //playYoutubeVideo(LessonVideo.get(j));
        }
        //assign youtube variable
        //youTubePlayerView = findViewById(R.id.youtube_player_view);

        youTubePlayerView1 = new YouTubePlayerView(this);
        youTubePlayerView1.setLayoutParams(layout.getLayoutParams());

        YouTubePlayerView youTubePlayerView2 = new YouTubePlayerView(this);
        youTubePlayerView1.setLayoutParams(layout.getLayoutParams());

        LessonVideo.add(youTubePlayerView1);
        LessonVideo.add(youTubePlayerView2);
       // layout.addView(LessonVideo.get(0),1100,800);

        Button button = new Button(this);
        button.setLayoutParams(layout.getLayoutParams());
        button.setTextSize(22);
        button.setText("Excercice");
        button.setHeight(100);
        button.setWidth(100);
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        Button button2 = new Button(this);
        button2.setLayoutParams(layout.getLayoutParams());
        button2.setTextSize(22);
        button2.setText("Excercice");
        button2.setHeight(100);
        button2.setWidth(100);
        button2.setGravity(Gravity.CENTER_HORIZONTAL);
       // buttonExercice.add(button);
       // buttonExercice.add(button2);
       // layout.addView(buttonExercice.get(0));
       // layout.addView(LessonVideo.get(1),1100,800);
       // layout.addView(buttonExercice.get(1));

       // playYoutubeVideo(LessonVideo.get(1),"qAHMCZBwYo4");
        //playYoutubeVideo(LessonVideo.get(1),"qAHMCZBwYo4");
        PlayWithButton(findViewById(R.id.bouton),LessonVideo.get(1));

        retrofit = new Retrofit.Builder().baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitLessonInterface = retrofit.create(RetrofitLessonInterface.class);



    }

    public void playVideo(VideoView video){
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("videoclick","onclick");
                if(video.isPlaying())  video.pause();
                else  video.start();
            }
        });


           // video.setOnTouchListener(new VideoView.OnTouchListener() {
              //  @Override
               // public boolean onTouch(View view, MotionEvent motionEvent) {
                        //Log.d("video","ACTION_DOWN");

                        //if(video.isPlaying())  video.pause();
                        //else  video.start();


                 //   return false;
               // }


           // });

        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
               Log.d("messageId","numero"+video.getId());
               buttonExercice.get(video.getId()).setVisibility(View.VISIBLE);
            }
        });
    }

    public void playYoutubeVideo(YouTubePlayerView videoYoutube,String id){
        try{
            //initialize listener
            YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener(){
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    //load video
                    youTubePlayer.loadVideo(id);

                    //startvideo
                    youTubePlayer.play();
                    //youTubePlayer.play();
                    // if(youTubePlayer.isPlaying())youTubePlayer.pause();
                    // else youTubePlayer.play();
                }
                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(getApplicationContext(),youTubeInitializationResult.name().toString(),Toast.LENGTH_SHORT).show();
                }

            };
            videoYoutube.initialize("AIzaSyAetr811-xxB2NelrAHbKRq_ohGfYWXwxU",listener);
        }
        catch (Exception ex){
            Log.d("Message d'exception",ex.getMessage());
        }


        //videoYoutube.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View view) {
             //   Log.d("videoclick","onclick");

           // }
       // });
    }

    public void PlayWithButton(Button button,YouTubePlayerView youTubePlayerView){

        button.setOnClickListener(new Button.OnClickListener(){
           @Override
           public void onClick(View view){
               Log.d("videoclick","onclick");
               getyoutube();
           }
       });
    }

    public void getyoutube()
    {
        Call<Lesson> call = retrofitLessonInterface.GetAllYoutube();

        call.enqueue(new Callback<Lesson>() {
            @Override
            public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                Lesson lesson = response.body();

                if(lesson!=null) {
                    String lessonData = lesson.getData().get(0).getVideo();
                    Toast.makeText(LessonActivity.this,lessonData,Toast.LENGTH_SHORT).show();
                    playYoutubeVideo(findViewById(R.id.youtube),lessonData);
                }
            }
            @Override
            public void onFailure(Call<Lesson> call, Throwable t) {
                //erreur connexion ou autre exception test 2
                Toast.makeText(LessonActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}