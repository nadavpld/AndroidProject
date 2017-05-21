package com.androidproject.androidproject.Infrastructure;

import com.androidproject.androidproject.Entities.CroppedImage;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;

public class ServerHttpClient {

    private OkHttpClient okHttpClient;

    public void AddTranslation(CroppedImage croppedImage) {
        Gson gson = new Gson();
        String json = gson.toJson(croppedImage);
    }

}
