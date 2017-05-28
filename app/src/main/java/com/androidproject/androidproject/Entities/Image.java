package com.androidproject.androidproject.Entities;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public abstract class Image {

    public Image() {
        Id = UUID.randomUUID();
        UserId = null; // TODO change to the user's Id - should be saved globally
        //Picture = null;
    }

    private UUID Id;

    private UUID UserId;

    //private Bitmap Picture;

    private String image; // Base64

    public void SetPicture(Bitmap picture) {

        //Picture = picture;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("Image : " , image);
    }

//    public Bitmap GetPicture() {
//        return Picture;
//    }

    public void SetUserId(UUID userId) {
        UserId = userId;
    }

}
