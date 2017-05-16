package com.androidproject.androidproject.Infrastructure;

import com.androidproject.androidproject.Entities.CroppedImage;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;

import okhttp3.OkHttpClient;

public class TranslateHttpClient {

    private OkHttpClient HttpClient;

    public void POST(CroppedImage Image) {
        Gson gson = new Gson();
        String json = gson.toJson(Image);

    }

}
