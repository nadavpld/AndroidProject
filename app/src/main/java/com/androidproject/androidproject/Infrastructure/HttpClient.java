package com.androidproject.androidproject.Infrastructure;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient<TEntity> {

    public HttpClient(HttpUrl url) {
        Url = url;
        Client = new OkHttpClient();
    }

    private OkHttpClient Client;

    private HttpUrl Url;

    //GET network request
    public String GET() throws IOException {
        Request request = new Request.Builder()
                .url(Url)
                .build();
        Response response = Client.newCall(request).execute();
        return response.body().string();
    }

    //POST network request
    public String POST(RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(Url)
                .post(body)
                .build();
        Response response = Client.newCall(request).execute();
        return response.body().string();
    }
}