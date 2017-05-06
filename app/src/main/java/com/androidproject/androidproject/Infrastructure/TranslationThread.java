package com.androidproject.androidproject.Infrastructure;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidproject.androidproject.Common.Coordinate;
import com.androidproject.androidproject.R;

/**
 * Created by Emma Portin on 03-May-17.
 */

public class TranslationThread implements Runnable {

    String translation;
    Activity activity;
    Coordinate coordinate;
    Bitmap bitmap;

    public TranslationThread(Activity activity, Coordinate coordinate, Bitmap bitmap, String translation) {
        this.translation = translation;
        this.activity = activity;
        this.coordinate = coordinate;
        this.bitmap = bitmap;
    }

    @Override
    public void run() {
        AddTranslationToLayout(coordinate, translation);
    }

    private void AddTranslationToLayout(Coordinate first, String translation) {
        RelativeLayout relativeLayout = (RelativeLayout) activity.findViewById(R.id.ImageLayout);
        ImageView imageView = (ImageView) activity.findViewById(R.id.Image);
        relativeLayout.requestLayout();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Drawable drawable = imageView.getDrawable();
//you should call after the bitmap drawn
        Rect bounds = drawable.getBounds();
        int bitmapWidth = drawable.getIntrinsicWidth(); //this is the bitmap's width
        int bitmapHeight = drawable.getIntrinsicHeight(); //this is the bitmap's height
        //int w = bitmap.getScaledWidth(displayMetrics);
        //int h = bitmap.getScaledHeight(displayMetrics);
        int imagew = imageView.getWidth();
        int imageh = imageView.getHeight();
        float wratio = bitmapWidth / (float) imagew;
        float hratio = bitmapHeight / (float) imageh;
        TextView textView = new TextView(activity);
        textView.setText(translation);
        textView.setBackgroundColor(Color.parseColor("#FFE70B0B"));
        textView.setX(first.x0 / wratio);
        textView.setY(first.y0 / hratio);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        relativeLayout.addView(textView);
    }
}
