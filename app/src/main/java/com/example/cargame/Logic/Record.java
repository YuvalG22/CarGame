package com.example.cargame.Logic;

import androidx.annotation.NonNull;

public class Record {
    private String name;
    private int score;
    private double lat;
    private double lon;

    public Record(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " - " + score + " coins";
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
