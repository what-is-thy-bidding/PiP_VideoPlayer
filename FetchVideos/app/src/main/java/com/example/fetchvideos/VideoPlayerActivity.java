package com.example.fetchvideos;

import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {
    Button button;
    ActionBar actionBar;

    VideoView videoView;
    ImageView imageView;
    SeekBar seekBar;
    String str_video_url;
    boolean isPlay=false;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        actionBar=getSupportActionBar();
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

                    Display display=getWindowManager().getDefaultDisplay();
                    Point size=new Point();
                    display.getSize(size);
                    int width=size.x;
                    int height=size.y;
                    Rational aspectRatio=new Rational(width,height);
                    PictureInPictureParams.Builder mPictureInPictureBuilder=
                            new PictureInPictureParams.Builder();

                    mPictureInPictureBuilder.setAspectRatio(aspectRatio).build();
                    enterPictureInPictureMode(mPictureInPictureBuilder.build());

                }
            }
        });

        init();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if(isInPictureInPictureMode){
            actionBar.hide();
            button.setVisibility(View.GONE);
        }else{
            actionBar.show();
            button.setVisibility(View.VISIBLE);

        }

    }

    private void init(){
        videoView=(VideoView)findViewById(R.id.videoView);

        imageView=(ImageView)findViewById(R.id.toggleButton);

        seekBar=(SeekBar)findViewById(R.id.seekBar);

        str_video_url=getIntent().getStringExtra("video");

        videoView.setVideoPath(str_video_url);

        handler=new Handler();

        videoView.start();

        isPlay=true;

        imageView.setImageResource(R.drawable.pausebutton);

        updateSeekBar();




    }
    private void updateSeekBar(){
        handler.postDelayed(updateTimeTask,100);

    }
    public Runnable updateTimeTask=new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(videoView.getCurrentPosition());
            seekBar.setMax(videoView.getDuration());
            handler.postDelayed(this,100);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    handler.removeCallbacks(updateTimeTask);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    handler.removeCallbacks(updateTimeTask);
                    videoView.seekTo(seekBar.getProgress());
                    updateSeekBar();
                }
            });
        }
    };

    public void toggle_method(View v){
        if(isPlay==true){
            videoView.pause();
            isPlay=false;
            imageView.setImageResource(R.drawable.playbutton);
        }
        else if(isPlay==false){
            videoView.start();
            updateSeekBar();
            isPlay=true;
            imageView.setImageResource(R.drawable.pausebutton);
        }
    }

    /*ActionBar actionBar;
    Button button;
    String str_video_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        str_video_path=getIntent().getStringExtra("video");
        actionBar=getSupportActionBar();
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

                    Display display=getWindowManager().getDefaultDisplay();
                    Point size=new Point();
                    display.getSize(size);
                    int width=size.x;
                    int height=size.y;
                    Rational aspectRatio=new Rational(width,height);
                    PictureInPictureParams.Builder mPictureInPictureBuilder=
                            new PictureInPictureParams.Builder();

                    mPictureInPictureBuilder.setAspectRatio(aspectRatio).build();
                    enterPictureInPictureMode(mPictureInPictureBuilder.build());

                }
            }
        });


        String fileName="video";
        String filePlace=str_video_path;
        VideoView videoView=(VideoView)findViewById(R.id.videoView);

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        // can also set the URI to local video with Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4"
        //Uri uri= Uri.parse("https://streamable.com/vpghe");
        Uri uri= Uri.parse(filePlace);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        //videoView.requestFocus();
        videoView.start();
    }


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if(isInPictureInPictureMode){
            actionBar.hide();
            button.setVisibility(View.GONE);
        }else{
            actionBar.show();
            button.setVisibility(View.VISIBLE);

        }

    }*/




}
