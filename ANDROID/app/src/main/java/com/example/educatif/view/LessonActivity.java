package com.example.educatif.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educatif.R;
import com.example.educatif.controller.LessonController;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class LessonActivity extends YouTubeBaseActivity {
    LessonController lessonController;
    private TextView description,title;
    private ImageView imageView;
    EditText textInputEditText;
    TextView textScore;
    GifImageView bravo;
    int rand_int;
    int score = 0;

    private int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        init();

        if(lessonController.lessonData.getTitle().toLowerCase(Locale.ROOT).trim().equals("les chiffres")){
            similationChiffre();
        }
        else {
            similation();
        }
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

    public void init(){
        View view = getLayoutInflater().inflate(R.layout.row, null);

        description = view.findViewById(R.id.artist);
        title = view.findViewById(R.id.title);

        imageView = view.findViewById(R.id.list_image);
        RelativeLayout relativeLayout = view.findViewById(R.id.relativelayoutdesc);

       LinearLayout layoutDescription = findViewById(R.id.layoutDescription);
       layoutDescription.addView(view);
        lessonController = LessonController.getInstance();
        playYoutubeVideo(findViewById(R.id.youtube),lessonController.lessonData.getVideo());
        title.setText(lessonController.lessonData.getTitle());
        description.setText(Html.fromHtml(lessonController.lessonData.getDescription()));
        description.setTextColor(lessonController.preference.getForegroundColor());
        Picasso.get().load(lessonController.lessonData.getImage()).into(imageView);

        if(lessonController.preference != null){
            if(lessonController.preference.getBackgroundColor()==Color.BLACK){
                String uridark = "@drawable/bg_dark_2";
                int imageResourceDark = getResources().getIdentifier(uridark, null, getPackageName());
                LessonActivity.this.findViewById(R.id.lessonvideoprincipal).setBackgroundColor(lessonController.preference.getBackgroundColor());
               //LessonActivity.this.findViewById(R.id.lessonvideoprincipal).setBackgroundResource(imageResourceDark);
                relativeLayout.setBackgroundColor(lessonController.preference.getBackgroundColor());
            }
        }

    }

    public void similation(){
        textInputEditText = (EditText)findViewById(R.id.editTextTextPersonName);
        String alp = "abcdefghijklmnopqrstuvwxyz";

        int min=0, max=alp.length()-4;
        rand_int = (int)(Math.random()*((max-min)+1))+min;
        TextView textView = findViewById(R.id.alphabetprev);
        textView.setText(alp.substring(rand_int,rand_int+1));
        TextView textViewnext = findViewById(R.id.alphabetnext);
        textView.setText(alp.substring(rand_int,rand_int+1));
        textViewnext.setText(alp.substring(rand_int+2,rand_int+3));
        bravo = findViewById(R.id.bravo);

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length()>1){
                    textInputEditText.setText("");
                }

                int rep = alp.indexOf(charSequence.toString().toLowerCase(Locale.ROOT));
                //int truerep = alp.indexOf(rand_int);
                if(rep>=0){
                    String result = alp.substring(rep,rep+1);
                    String repres = alp.substring(rand_int,rand_int+1);
                    Log.d("messabe mivandravandra",alp.substring(rand_int,rand_int+1)+" "+result);
                    if(alp.indexOf(result)==alp.indexOf(repres)+1)
                    {
                        rand_int = (int)(Math.random()*((max-min)+1))+min;
                        Toast.makeText(LessonActivity.this,"Bravo",Toast.LENGTH_LONG).show();
                        score++;
                        textScore = findViewById(R.id.score);
                        textScore.setText(Integer.toString(score));

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                textViewnext.setText(alp.substring(rand_int+2,rand_int+3));
                                textView.setText(alp.substring(rand_int,rand_int+1));
                                textInputEditText.setText("");

                            }
                        }, 2000L);

                        try{
                            bravo.setVisibility(View.VISIBLE);
                        }
                        catch (Exception ex){
                        }
                        //textInputEditText.setEnabled(false);
                    }
                    else {
                        try{
                            bravo.setVisibility(View.INVISIBLE);
                        }
                        catch (Exception ex){
                        }
                    }
                }



            }


            @Override
            public void afterTextChanged(Editable editable) {
                //Toast.makeText(LessonActivity.this,alp.indexOf(rand_int),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void similationChiffre(){
        textInputEditText = (EditText)findViewById(R.id.editTextTextPersonName);
        int min=2, max=20;
        rand_int = (int)(Math.random()*((max-min)+1))+min;
        TextView textView = findViewById(R.id.alphabetprev);
        TextView textViewnext = findViewById(R.id.alphabetnext);
        String textprev = Integer.toString(rand_int-1);
        String textnext = Integer.toString(rand_int+1);
        textView.setText(textprev);
        textViewnext.setText(textnext);
        bravo = findViewById(R.id.bravo);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Toast.makeText(LessonActivity.this,rand_int,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length()>2){
                    textInputEditText.setText("");
                }
                // Toast.makeText(LessonActivity.this,alp,Toast.LENGTH_SHORT).show();
                int rep = 0;
                try{
                    rep = Integer.parseInt(charSequence.toString());
                }
                catch (Exception ex){

                }

                //int truerep = alp.indexOf(rand_int);
                if(rep==rand_int){
                    rand_int = (int)(Math.random()*((max-min)+1))+min;
                    Toast.makeText(LessonActivity.this,"Bravo",Toast.LENGTH_LONG).show();
                    score++;
                    textScore = findViewById(R.id.score);
                    textScore.setText(Integer.toString(score));
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // here goes your code to delay
                            String textprev = Integer.toString(rand_int-1);
                            String textnext = Integer.toString(rand_int+1);
                            textView.setText(textprev);
                            textViewnext.setText(textnext);
                            textInputEditText.setText("");
                            //return;
                        }
                    }, 2000L);
                    try{
                        bravo.setVisibility(View.VISIBLE);
                    }
                    catch (Exception ex){
                    }
                    //textInputEditText.setEnabled(false);
                }
                else {
                    try{
                        bravo.setVisibility(View.INVISIBLE);
                    }
                    catch (Exception ex){
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Toast.makeText(LessonActivity.this,alp.indexOf(rand_int),Toast.LENGTH_SHORT).show();
            }
        });
    }
}