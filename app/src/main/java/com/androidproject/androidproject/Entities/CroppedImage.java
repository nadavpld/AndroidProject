package com.androidproject.androidproject.Entities;

import android.util.Pair;

import com.androidproject.androidproject.Common.Coordinate;
import com.androidproject.androidproject.Common.Translation;

import java.util.ArrayList;
import java.util.List;

public class CroppedImage extends Image {

    public ArrayList<Translation> Translations;

    public CroppedImage() {
        Translations = new ArrayList<>();
    }

    public ArrayList<Translation> GetTranslations() {
        return Translations;
    }

}
