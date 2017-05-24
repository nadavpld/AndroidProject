package com.androidproject.androidproject.Entities;

import android.graphics.Bitmap;

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

    private byte[] ByteArray;

    public void SetPicture(Bitmap picture) {

        //Picture = picture;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        ByteArray = stream.toByteArray();
    }

//    public Bitmap GetPicture() {
//        return Picture;
//    }

    public void SetUserId(UUID userId) {
        UserId = userId;
    }

}
