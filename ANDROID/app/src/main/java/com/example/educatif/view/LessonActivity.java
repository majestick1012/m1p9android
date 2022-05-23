package com.example.educatif.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.educatif.R;
import com.example.educatif.Utils.RetrofitLessonInterface;
import com.example.educatif.controller.LessonController;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;

public class LessonActivity extends YouTubeBaseActivity {
    List<Button> buttonExercice = new ArrayList<>();
    LessonController lessonController;

    private String base_Url="https://m1p9android-jm.herokuapp.com";
    private TextView description,title;
    private ImageView imageView;

    private int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        init();
        //Toast.makeText(this,lessonController.lessonData.getVideo(),Toast.LENGTH_SHORT).show();
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
                    //start video
                    youTubePlayer.play();
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
    }

    public void PlayWithButton(Button button){

        button.setOnClickListener(new Button.OnClickListener(){
           @Override
           public void onClick(View view){
               Log.d("videoclick","onclick");
               //getyoutube();
               playYoutubeVideo(findViewById(R.id.youtube),lessonController.lessonData.getVideo());
           }
       });
    }

    public void init(){
        View view = getLayoutInflater().inflate(R.layout.row, null);

        description = view.findViewById(R.id.artist);
        title = view.findViewById(R.id.title);
        imageView = view.findViewById(R.id.list_image);
        RelativeLayout relativeLayout = view.findViewById(R.id.relativelayoutdesc);

       LinearLayout layoutDescription = findViewById(R.id.layoutDescription);
       layoutDescription.addView(view);
        lessonController = LessonController.getInstance();
        //PlayWithButton(findViewById(R.id.bouton));
        playYoutubeVideo(findViewById(R.id.youtube),lessonController.lessonData.getVideo());
        title.setText(lessonController.lessonData.getTitle());
        description.setText(lessonController.lessonData.getDescription());
        Picasso.get().load(lessonController.lessonData.getImage()).into(imageView);

        if(lessonController.preference != null){
            if(lessonController.preference.getBackgroundColor()==Color.BLACK){
                String uridark = "@drawable/bg_dark_2";
                int imageResourceDark = getResources().getIdentifier(uridark, null, getPackageName());
                LessonActivity.this.findViewById(R.id.lessonvideoprincipal).setBackgroundResource(imageResourceDark);
                relativeLayout.setBackgroundColor(lessonController.preference.getBackgroundColor());
            }
        }

    }
}