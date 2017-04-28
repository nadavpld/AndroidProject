package com.androidproject.androidproject;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CropActivity extends AppCompatActivity {

    ImageView Image;

    LinearLayout TranslateButton;

    FloatingActionButton AddPOIButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        Image = (ImageView)findViewById(R.id.Image);

        TranslateButton = (LinearLayout) findViewById(R.id.Translate);

        AddPOIButton = (FloatingActionButton) findViewById(R.id.AddPOI);

        TranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        AddPOIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
