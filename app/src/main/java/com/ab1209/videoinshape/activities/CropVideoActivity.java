package com.ab1209.videoinshape.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ab1209.videoinshape.R;
import com.ab1209.videoinshape.util.Shape;
import com.ab1209.videoinshape.util.Utility;
import com.ab1209.videoinshape.views.VideoSurfaceView;

import static com.ab1209.videoinshape.util.Shape.CIRCLE;
import static com.ab1209.videoinshape.util.Shape.FULL;
import static com.ab1209.videoinshape.util.Shape.OVAL;
import static com.ab1209.videoinshape.util.Shape.RECTANGLE;

/**
 * Copyright (C) 2018 Arun Badole.
 * <p>
 * Activity which uses the custom surface view for video to crop in different shapes.
 *
 * @author arunbadole
 */
public class CropVideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, SurfaceHolder.Callback, View.OnClickListener {
    private final static String TAG = "MainActivity";

    private VideoSurfaceView videoView;

    private Button btnCropCircle;
    private Button btnCropOval;
    private Button btnFull;
    private Button btnCropRectangle;

    //Parameters for video view.
    private int fullLayoutWidth;
    private int fullLayoutHeight;
    private MediaPlayer player;

    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_video);
        initViews();
        initParameters();
    }

    void initViews() {
        videoView = findViewById(R.id.activity_crop_video_surface_view);
        btnCropCircle = findViewById(R.id.activity_crop_video_btn_circle);
        btnCropOval = findViewById(R.id.activity_crop_video_btn_oval);
        btnCropRectangle = findViewById(R.id.activity_crop_video_btn_rectangle);
        btnFull = findViewById(R.id.activity_crop_video_btn_full);

        btnCropCircle.setOnClickListener(this);
        btnCropOval.setOnClickListener(this);
        btnFull.setOnClickListener(this);
        btnCropRectangle.setOnClickListener(this);
    }

    /**
     * Initialise the parameters used.
     */
    private void initParameters() {
        SurfaceHolder holder = videoView.getHolder();
        holder.addCallback(this);

        player = MediaPlayer.create(this, R.raw.bird_l);
        player.setOnCompletionListener(this);

        //Getting the screen width & height.
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        //Setting the video with proper aspect ratio on full screen width & height.
        int dimenFull[] = Utility.getVideoDimensions(player, screenWidth, screenHeight);
        fullLayoutWidth = dimenFull[0];
        fullLayoutHeight = dimenFull[1];
        setVideoLayout(FULL, fullLayoutWidth, fullLayoutHeight);
    }

    /**
     * Change the layout dimensions for video view.
     */
    private void setVideoLayout(Shape shape, int width, int height) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;

        boolean inOtherShape = true;
        switch (shape) {
            case CIRCLE:
                //You can change the center coordinates & radius.
                videoView.cropCircle(width / 2, width / 2, width / 2);
                break;

            case OVAL:
                videoView.cropOval(0, 0, width, height);
                break;

            case RECTANGLE:
                videoView.cropRect(0, 0, width, height);
                break;

            case FULL:
                inOtherShape = false;
                break;
        }
        videoView.setOtherShape(inOtherShape);
        videoView.setLayoutParams(layoutParams);
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        player.setDisplay(surfaceHolder);
        player.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void cropCircle() {
        //croppedWidth & croppedHeight are calculated by the required width & height provided.
        // getVideoDimensions() calculates it by maintaining aspect ratio of the video.
        int dimen[] = Utility.getVideoDimensions(player, screenWidth / 4, screenHeight / 4);
        int croppedWidth = dimen[0];
        int croppedHeight = dimen[1];
        setVideoLayout(CIRCLE, croppedWidth, croppedHeight);
    }

    private void cropOval() {
        int dimen[] = Utility.getVideoDimensions(player, screenWidth / 2, screenHeight / 2);
        int croppedWidth = dimen[0];
        int croppedHeight = dimen[1];
        setVideoLayout(OVAL, croppedWidth, croppedHeight);
    }

    private void cropRectangle() {
        int dimen[] = Utility.getVideoDimensions(player, screenWidth / 2, screenHeight / 2);
        int croppedWidth = dimen[0];
        int croppedHeight = dimen[1];
        setVideoLayout(RECTANGLE, croppedWidth, croppedHeight);
    }

    private void fullVideo() {
        setVideoLayout(FULL, fullLayoutWidth, fullLayoutHeight);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_crop_video_btn_circle:
                cropCircle();
                break;
            case R.id.activity_crop_video_btn_oval:
                cropOval();
                break;
            case R.id.activity_crop_video_btn_rectangle:
                cropRectangle();
                break;
            case R.id.activity_crop_video_btn_full:
                fullVideo();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player.isPlaying())
            player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null && !player.isPlaying())
            player.start();
    }
}
