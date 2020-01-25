package com.pip_video_player;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    ActionBar actionBar;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        String filePlace="android.resource://"+getPackageName()+"/raw/"+fileName;
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

    }



}
