package com.ab1209.videoinshape.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Copyright (C) 2018 Arun Badole.
 *
 * Utility class contains frequently used methods.
 *
 * @author arunbadole
 */
public class Utility {

    private final static String TAG = "Utility";

    /**
     * Converts dps to device pixels.
     *
     * @param context
     * @param dps
     * @return converted pixels
     */
    public static int getPixelFromDP(Context context, int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    /**
     * Calculates the dimensions for a video for maintaining aspect ratio.
     */
    public static int[] getVideoDimensions(MediaPlayer player, int width, int height) {
        int layoutWidth = width;
        int layoutHeight = height;

        float mediaWidth = player.getVideoWidth();
        float mediaHeight = player.getVideoHeight();

        float ratioWidth = layoutWidth / mediaWidth;
        float ratioHeight = layoutHeight / mediaHeight;
        float aspectRatio = mediaWidth / mediaHeight;

        if (ratioWidth > ratioHeight) {
            layoutWidth = (int) (layoutHeight * aspectRatio);
        } else {
            layoutHeight = (int) (layoutWidth / aspectRatio);
        }

        Log.i(TAG, "layoutWidth: " + layoutWidth);
        Log.i(TAG, "layoutHeight: " + layoutHeight);
        Log.i(TAG, "aspectRatio: " + aspectRatio);

        return new int[]{layoutWidth, layoutHeight};
    }
}
