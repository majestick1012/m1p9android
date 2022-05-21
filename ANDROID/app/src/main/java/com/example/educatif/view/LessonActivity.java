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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.educatif.R;
import com.example.educatif.Utils.RetrofitInterface;
import com.example.educatif.Utils.RetrofitLessonInterface;
import com.example.educatif.controller.LessonController;
import com.example.educatif.model.Lesson;
import com.example.educatif.model.Login;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LessonActivity extends YouTubeBaseActivity {
    List<Button> buttonExercice = new ArrayList<>();
    LessonController lessonController;

    private Retrofit retrofit;
    private RetrofitLessonInterface retrofitLessonInterface;
    private String base_Url="https://m1p9android-jm.herokuapp.com";
    private TextView description,title;
    private ImageView imageView;

    List<YouTubePlayerView> LessonVideo = new ArrayList<>();
    private int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        LinearLayout layout = findViewById(R.id.constraintLayoutVideo);
        init();
        Toast.makeText(this,lessonController.lessonData.getVideo(),Toast.LENGTH_SHORT).show();
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

       LinearLayout layoutDescription = findViewById(R.id.layoutDescription);
       layoutDescription.addView(view);
        lessonController = LessonController.getInstance();
        //PlayWithButton(findViewById(R.id.bouton));
        playYoutubeVideo(findViewById(R.id.youtube),lessonController.lessonData.getVideo());
        title.setText(lessonController.lessonData.getTitle());
        description.setText(lessonController.lessonData.getDescription());
        Picasso.get().load(lessonController.lessonData.getImage()).into(imageView);
    }
}