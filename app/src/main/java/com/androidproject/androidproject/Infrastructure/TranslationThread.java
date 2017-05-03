package com.androidproject.androidproject.Infrastructure;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

    public TranslationThread(Activity activity, Coordinate coordinate, String translation) {
        this.translation = translation;
        this.activity = activity;
        this.coordinate = coordinate;
    }

    @Override
    public void run() {
        AddTranslationToLayout(coordinate, translation);
    }

    private void AddTranslationToLayout(Coordinate first, String translation) {
        LinearLayout relativeLayout = (LinearLayout) activity.findViewById(R.id.ImageLayout);
        relativeLayout.requestLayout();
        TextView textView = new TextView(activity);
        textView.setText(translation);
        textView.setBackgroundColor(Color.parseColor("#FFE70B0B"));
        textView.setX(first.x0);
        textView.setY(first.y0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        relativeLayout.addView(textView);
    }
}
