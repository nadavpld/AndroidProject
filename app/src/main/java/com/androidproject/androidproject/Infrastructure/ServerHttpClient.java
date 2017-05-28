package com.androidproject.androidproject.Infrastructure;

import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

import com.androidproject.androidproject.Entities.CroppedImage;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerHttpClient {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public void AddImageToServer(CroppedImage croppedImage) {
        AddImageTask addImageTask = new AddImageTask();
        addImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, croppedImage);
    }

    private class AddImageTask extends AsyncTask<CroppedImage, Void, Void> {

        @Override
        protected Void doInBackground(CroppedImage... croppedImages) {
            CroppedImage croppedImage = croppedImages[0];
            String url = "https://learnguage.herokuapp.com/api/image";
            Gson gson = new Gson();
            String json = gson.toJson(croppedImage);
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                // TODO handle it somehow
            }
            return null;
        }
    }
}