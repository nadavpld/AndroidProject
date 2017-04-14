package com.androidproject.androidproject.Entities;

import android.graphics.Bitmap;

import java.util.UUID;

public abstract class Image {

    public Image() {
        Id = UUID.randomUUID();
        Picture = null;
    }

    protected UUID Id;

    protected Bitmap Picture;

    public void SetPicture(Bitmap picture) {
        Picture = picture;
    }

    public Bitmap GetPicture() {
        return Picture;
    }

}
