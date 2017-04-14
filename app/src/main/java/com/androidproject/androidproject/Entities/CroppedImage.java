package com.androidproject.androidproject.Entities;

import android.util.Pair;

import com.androidproject.androidproject.Common.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class CroppedImage extends Image {

    private ArrayList<Pair<Coordinate, String>> Translations;

    public CroppedImage() {
        Translations = new ArrayList<Pair<Coordinate, String>>();
    }

    public ArrayList<Pair<Coordinate, String>> GetTranslations() {
        return Translations;
    }

    public void AddTranslation(Coordinate coordinate, String translation) {
        Pair<Coordinate, String> p = new Pair<>(coordinate, translation);
        Translations.add(p);
    }

    public void RemoveTranslation(Coordinate coordinate) {
        int index = -1;
        for(int i = 0 ; i < Translations.size() ; i++) {
            if(Translations.get(i).first.equals(coordinate)) {
                index = i;
                break;
            }
        }
        if(index != -1) {
            Translations.remove(index);
        }
    }
}
