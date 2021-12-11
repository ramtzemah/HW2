package com.example.hw2;

import java.util.Timer;
import java.util.TimerTask;

public class TimeInSec {
private TimerTask ts;

    public TimeInSec() {
    }
    
    public void timer(int time){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(ts = new TimerTask() {
            @Override
            public void run() {
//              |] });
            }
        }, 0, time);
    }
}
