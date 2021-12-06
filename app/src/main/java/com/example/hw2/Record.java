package com.example.hw2;

public class Record {
    private int score;
    private String lot;
    private String lat;

    public Record() {
    }

    public Record(int score, String log, String lag) {
        this.score = score;
        this.lot = log;
        this.lat = lag;
    }

    public int getScore() {
        return score;
    }

    public String getLot() {
        return lot;
    }

    public String getLat() {
        return lat;
    }
}
