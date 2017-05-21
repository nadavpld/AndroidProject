package com.androidproject.androidproject.Infrastructure;

import android.util.Log;

import com.androidproject.androidproject.Entities.CroppedImage;
import com.androidproject.androidproject.Entities.UserConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TranslateHttpClient {

    private OkHttpClient HttpClient = new OkHttpClient();

    private final String API_KEY = "trnsl.1.1.20170518T202702Z.2b7b3b97d32b105f.17b3f462314f42622ae76c4d7eae45281478108d";

    public String GetTranslation(String word) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://translate.yandex.net/api/v1.5/tr.json/translate").newBuilder();
        urlBuilder.addQueryParameter("key", API_KEY);
        urlBuilder.addQueryParameter("text", word);
        urlBuilder.addQueryParameter("lang", "en-fr" /* TODO + Configuration.Lang*/);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        try {
             response = HttpClient.newCall(request).execute();
        } catch (Exception e) {
            Log.d("Translation Error : ",e.getMessage());
            return null;
        }
        try {
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
        } catch (Exception e) {
            Log.d("Translation Error : ",e.getMessage());
            return null;
        }
        // TODO - check how the body is returned, might need to parse it
        return null;
    }

}
