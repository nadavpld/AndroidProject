package com.androidproject.androidproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    File imageFile = null;

    LinearLayout TakePic;

    static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isStoragePermissionGranted();

        TakePic = (LinearLayout)findViewById(R.id.TakePicLayout);

        TakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    imageFile = createImageFile();
                } catch (IOException e) {
                    Log.d("Error : ", e.getMessage());
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                        getApplicationContext(),
                        getApplicationContext()
                                .getPackageName() + ".provider", imageFile));
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });

    }

    public void LaunchBrowsePicture(View view) {
    }

    public void LaunchRandomPicture(View view) {
    }

    public void LaunchQuiz(View view) {
    }

    public void LaunchProfile(View view) {
    }

    public void LaunchSettings(View view) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case TAKE_PICTURE :
                if(resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, CropActivity.class);
                    intent.putExtra("Uri", imageFile.getPath());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();
                    break;
                }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File myFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), imageFileName + ".jpg");
        return myFile;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}
