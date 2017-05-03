package com.androidproject.androidproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidproject.androidproject.Entities.CroppedImage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;

import clarifai2.dto.input.image.Crop;

public class CropActivity extends AppCompatActivity {

    ImageView Image;

    LinearLayout TranslateButton;

    FloatingActionButton AddPOIButton;

    File imageFile = null;

    Bitmap bitmap = null;

    ArrayList<Bitmap> CroppedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        CroppedImages = new ArrayList<>();

        Image = (ImageView)findViewById(R.id.Image);

        TranslateButton = (LinearLayout) findViewById(R.id.Translate);

        AddPOIButton = (FloatingActionButton) findViewById(R.id.AddPOI);

        String path = getIntent().getStringExtra("Uri");

        imageFile = new File(path);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = BitmapFactory.decodeFile(path);
        Image.setImageBitmap(bitmap);

        TranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunTranslations();
            }
        });

        AddPOIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity(FileProvider.getUriForFile(
                        getApplicationContext(),
                        getApplicationContext()
                                .getPackageName() + ".provider", imageFile))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(CropActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if(resultCode == RESULT_OK) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    DrawRectangle(result.getCropPoints());
                    SaveCroppedImage(result.getUri());
                } else {

                }
        }
    }

    private void DrawRectangle(float[] points) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#FFE70B0B"));
        paint.setStrokeWidth(5.0f);

        //Create a new image bitmap and attach a brand new canvas to it
        Bitmap tempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(tempBitmap);

        //Draw the image bitmap into the cavas
        tempCanvas.drawBitmap(bitmap, 0, 0, null);

        //Draw everything else you want into the canvas, in this example a rectangle with rounded edges
        tempCanvas.drawRoundRect(new RectF(points[0], points[1], points[4], points[5]), 2, 2, paint);

        //Attach the canvas to the ImageView
        Image.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));

        //Saving the updated bitmap
        bitmap = tempBitmap;
    }

    private void SaveCroppedImage(Uri uri) {
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch(Exception e) {
            return;
        }
        CroppedImages.add(bitmap);
    }

    private void RunTranslations() {
        
    }

}
