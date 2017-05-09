package com.androidproject.androidproject.Infrastructure;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidproject.androidproject.Common.Coordinate;
import com.androidproject.androidproject.R;

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
        int bitmapWidth = drawable.getIntrinsicWidth();
        int bitmapHeight = drawable.getIntrinsicHeight();
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
