package com.androidproject.androidproject.Entities;


import com.androidproject.androidproject.Common.ImageCategory;

public class RandomImage extends Image {

    private ImageCategory Category;

    private String Translation;

    public RandomImage() {
        Category = null;
        Translation = null;
    }

    public RandomImage(ImageCategory category, String translation) {
        Category = category;
        Translation = translation;
    }

}
