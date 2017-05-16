package com.androidproject.androidproject.Common;

public class Translation {

    private Coordinate coordinate;

    private String translation;

    public Translation(Coordinate coordinate, String translation) {
        this.coordinate = coordinate;
        this.translation = translation;
    }

    public Coordinate GetCoordinate() {
        return coordinate;
    }

    public String GetTranslation() {
        return translation;
    }

    public void SetCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void SetTranslation(String translation) {
        this.translation = translation;
    }
}
