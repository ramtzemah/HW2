package com.example.hw2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MyDB {
    private ArrayList <Record> bestScore;
    private int score;
    private String lot;
    private String lat;

    public MyDB (){
        bestScore = new ArrayList(10);
    }

    public MyDB(ArrayList<Record> bestScore, int score, String lot, String lat) {
        this.score = score;
        this.lot = lot;
        this.lat = lat;
    }

    public boolean getIn(int score, String lot, String lat){
        if (bestScore.size()<10) {
            bestScore.add(new Record(score,lot,lat));
            sort();
            return true;
        }
        sort();
        Record lastScore = bestScore.get(9);
        if (score >lastScore.getScore()){
            bestScore.remove(9);
            Record record = new Record(score,lot,lat);
            bestScore.add(record);
            bestScore = sort();
            return true;
        }
            return false;
    }

    public ArrayList<Record> getBestScore() {
        return bestScore;
    }

    public ArrayList<Record> sort() {
        Collections.sort(bestScore, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getScore() > o2.getScore() ? -1 : (o1.getScore() < o2.getScore()) ? 1 : 0;
            }
        });
        return bestScore;
    }
}