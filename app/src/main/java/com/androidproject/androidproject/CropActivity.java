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
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidproject.androidproject.Common.Coordinate;
import com.androidproject.androidproject.Common.Translation;
import com.androidproject.androidproject.Entities.CroppedImage;
import com.androidproject.androidproject.Entities.Image;
import com.androidproject.androidproject.Infrastructure.ClarifaiHttpClient;
import com.androidproject.androidproject.Infrastructure.GoogleVisionClient;
import com.androidproject.androidproject.Infrastructure.TranslateHttpClient;
import com.androidproject.androidproject.Infrastructure.TranslationThread;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import clarifai2.dto.input.image.Crop;

public class CropActivity extends AppCompatActivity {

    ImageView ImageV;

    LinearLayout TranslateButton, ChooseObject, RetakeImage;

    File imageFile = null;

    Bitmap bitmap = null;

    CroppedImage Image;

    ArrayList<Pair<Coordinate,Bitmap>> CroppedImagesList;

    TextView Selected = null;

    Integer SelectedObjects = 0;

    static final int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        Image = new CroppedImage();

        CroppedImagesList = new ArrayList<>();

        ImageV = (ImageView)findViewById(R.id.Image);

        TranslateButton = (LinearLayout) findViewById(R.id.Translate);

        ChooseObject = (LinearLayout) findViewById(R.id.chooseObject);

        Selected = (TextView) findViewById(R.id.Selected);

        Selected.setText(SelectedObjects.toString());

        RetakeImage = (LinearLayout) findViewById(R.id.retakeImage);

        String path = getIntent().getStringExtra("Uri");

        imageFile = new File(path);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = BitmapFactory.decodeFile(path);
        ImageV.setImageBitmap(bitmap);

        Image.SetPicture(bitmap);

        Image.SetUserId(UUID.randomUUID());

        TranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunTranslations();
            }
        });

        ChooseObject.setOnClickListener(new View.OnClickListener() {
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

        RetakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    imageFile = createImageFile();
                } catch (IOException e) {
                    Log.d("Error : ", e.getMessage());
                    return;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if(resultCode == RESULT_OK) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    DrawRectangle(result.getCropPoints());
                    SaveCroppedImage(result.getUri(), result.getCropPoints());
                    SelectedObjects++;
                    Selected.setText(SelectedObjects.toString());
                } else {

                }
                break;
            case TAKE_PICTURE :
                if(resultCode == RESULT_OK) {
                    CroppedImagesList.clear();
                    RelativeLayout imageLayout = (RelativeLayout) findViewById(R.id.ImageLayout);
                    for(int i = 0 ; i < imageLayout.getChildCount() ; i++) {
                        View v = imageLayout.getChildAt(i);
                        if(v instanceof TextView) {
                            imageLayout.removeView(v);
                        }
                    }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    bitmap = BitmapFactory.decodeFile(imageFile.getPath());
                    ImageV.setImageBitmap(bitmap);
                    SelectedObjects = 0;
                    Selected.setText(SelectedObjects.toString());
                } else {
                    Toast.makeText(this, "Error Occurred", Toast.LENGTH_LONG).show();
                    break;
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
        tempCanvas.drawRect(new RectF(points[0], points[1], points[4], points[5]), paint);
        //Attach the canvas to the ImageView
        ImageV.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
        //Saving the updated bitmap
        Bitmap prevBitmap = bitmap;
        bitmap = tempBitmap;
        prevBitmap.recycle();
    }

    private void SaveCroppedImage(Uri uri, float[] points) {
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch(Exception e) {
            return;
        }
        CroppedImagesList.add(new Pair<>(new Coordinate(points[0], points[4], points[1], points[5]), bitmap));
    }

    private void RunTranslations() {
        for(Pair<Coordinate, Bitmap> p : CroppedImagesList) {
            TranslationRunner runner = new TranslationRunner();
            runner.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, p);
        }
    }

    private class TranslationRunner extends AsyncTask<Pair<Coordinate, Bitmap>, Void, String> {
        public TranslationRunner() {
            super();
        }

        @Override
        protected String doInBackground(Pair<Coordinate, Bitmap>... params) {
            Pair<Coordinate, Bitmap> p = params[0];
            //ClarifaiHttpClient client = new ClarifaiHttpClient(getApplicationContext());
            GoogleVisionClient googleVisionClient = new GoogleVisionClient();
            String recognition = null;
            try {
                //recognition = client.POST(p.second);
                recognition = googleVisionClient.callCloudVision(p.second);
            } catch (Exception e) {
                //TODO open snackbar with error
                Snackbar.make(findViewById(R.id.CropActivity), "Translation Failed, Please Try Again", Snackbar.LENGTH_LONG).show();
                return null;
            }
            //TranslateHttpClient translateHttpClient = new TranslateHttpClient();
            //String translation = translateHttpClient.GetTranslation(recognition);
//            TranslationThread translationThread = new TranslationThread(CropActivity.this, p.first , p.second, translation);
//            Image.Translations.add(new Translation(p.first, translation));
            TranslationThread translationThread = new TranslationThread(CropActivity.this, p.first , p.second, recognition);
            Image.Translations.add(new Translation(p.first, recognition));
            // Run the Translation Client on another Thread
            runOnUiThread(translationThread);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO open dialog
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //TODO close dialog
        }
    }

    public void AddTranslation(Coordinate coordinate, String translation) {
        Translation p = new Translation(coordinate, translation);
        Image.Translations.add(p);
    }

    public void RemoveTranslation(Coordinate coordinate) {
        int index = -1;
        for(int i = 0 ; i < Image.Translations.size() ; i++) {
            if((Image.Translations.get(i)).GetCoordinate().equals(coordinate)) {
                index = i;
                break;
            }
        }
        if(index != -1) {
            Image.Translations.remove(index);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File myFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), imageFileName + ".jpg");
        return myFile;
    }

}
