package com.ab1209.videoinshape.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Copyright (C) 2018 Arun Badole.
 *
 * SurfaceView for video player. Can crop the view in different shape.
 *
 * @author arunbadole
 */
public class VideoSurfaceView extends SurfaceView {

    private final static String TAG = "VideoSurfaceView";
    private boolean inOtherShape;
    private Path shapePath;

    public VideoSurfaceView(Context context) {
        super(context);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (this.inOtherShape)
            canvas.clipPath(shapePath);
        super.dispatchDraw(canvas);
    }

    /**
     * Crops the view in circular shape
     * @param centerX
     * @param centerY
     * @param radius
     */
    public void cropCircle(float centerX, float centerY, int radius) {
        shapePath = new Path();
        shapePath.addCircle(centerX, centerY, radius, Path.Direction.CW);
    }

    /**
     * Crops the view in oval shape
     * @param left
     * @param top
     * @param width
     * @param height
     */
    public void cropOval(float left, float top, int width, int height) {
        RectF rectF = new RectF(left, top, width, height);
        shapePath = new Path();
        shapePath.addOval(rectF, Path.Direction.CW);
    }

    /**
     * Crops the view in rectangular shape
     * @param left
     * @param top
     * @param width
     * @param height
     */
    public void cropRect(float left, float top, int width, int height) {
        RectF rectF = new RectF(left, top, width, height);
        shapePath = new Path();
        shapePath.addRect(rectF, Path.Direction.CW);
    }
    
    /**
     * Sets the flag for cropping the view in shape
     * @param inOtherShape
     */
    public void setOtherShape(boolean inOtherShape) {
        this.inOtherShape = inOtherShape;
        invalidate();
    }
}