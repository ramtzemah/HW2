package com.example.hw2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaActionSound;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TheGameActivity extends AppCompatActivity {
    public static final String GAME_TYPE = "GAME_TYPE";
    final int SIZEOFROUTE = 5;
    final int SIZEOFROWS = 6;
    private int DELAY = 750;
    private TextView panel_textView;
    private int score = 0;
    private ImageView[][] imgArray;
    private int[][] vals;
    private int[] rocketRoute;
    private ImageView panel_IMG_speed;
    private ImageView panel_IMG_heart1;
    private ImageView panel_IMG_heart2;
    private ImageView panel_IMG_heart3;
    private ImageView panel_IMG_lView;
    private ImageView panel_IMG_rView;
    private ImageView[] rocketArray;
    private ImageView panel_IMG_rArrow;
    private ImageView panel_IMG_lArrow;
    private int counter = 0;
    private int rocksSpace = 0;
    TimerTask ts;
    private SensorManager sensorManager;
    private Sensor accSensor;
    private String game_type = "";
    private static MyDB myDB;
    private String strlot = "";
    private String strlat = "";
    private float x = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_game_activity);

       initSensor();
       findViews();



        Bundle extras = getIntent().getBundleExtra("Bundle");
        if (extras != null) {
            game_type = extras.getString("GAME_TYPE");
        }

        setRocksInvisible(imgArray);

        if (game_type.equals("0")) {
            easyGame();
        } else {
            hardGame();
        }



//        SensorClass.CallBack_Move callBack_move = new SensorClass.CallBack_Move() {
//            @Override
//            public void dataReady(float t) {
//                Log.d("pttt", "Moooooodel: "  + " Thread: " + Thread.currentThread().getName());
//                x=t;
//                move(x);
//            }
//        };
//        SensorClass sensorClass =  new SensorClass(this);
//        sensorClass.move(callBack_move);


        LocationInCoordinate.CallBack_Loc callBack_loc = new LocationInCoordinate.CallBack_Loc() {
            @Override
            public void dataReady(String lag, String log) {
                strlat = lag;
                strlot = log;
            }
        };
        LocationInCoordinate locationInCoordinate =  new LocationInCoordinate(this);
        locationInCoordinate.getLastLocation(callBack_loc);

        panel_IMG_speed.setOnClickListener(v -> {

            counter++;
            ImageView im = panel_IMG_speed;
            if (counter == 1) {
                ts.cancel();
                setDelay(250);
                im.setImageResource(R.drawable.turtle);
            } else {
                ts.cancel();
                setDelay(750);
                im.setImageResource(R.drawable.bunny);
                panel_IMG_speed = im;
                counter = 0;
            }
        });


    }

    private void hardGame() {
        panel_IMG_lArrow.setVisibility(View.INVISIBLE);
        panel_IMG_lView.setVisibility(View.INVISIBLE);
        panel_IMG_rArrow.setVisibility(View.INVISIBLE);
        panel_IMG_rView.setVisibility(View.INVISIBLE);
        initSensor();
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

//    private void move(float x){
//        for (int i = 0; i < SIZEOFROUTE; i++) {
//                    rocketRoute[i] = 0;
//                    rocketArray[i].setVisibility(View.INVISIBLE);
//                }
//                if (x >= 6) {
//                    rocketRoute[0] = 1;
//                } else if (x >= 2 && x < 6) {
//                    rocketRoute[1] = 1;
//                } else if (x <= 2 && x >= -2) {
//                    rocketRoute[2] = 1;
//                } else if (x > -6 && x < -2) {
//                    rocketRoute[3] = 1;
//                } else {
//                    rocketRoute[4] = 1;
//                }
//                for (int i = 0; i < SIZEOFROUTE; i++) {
//                    if (rocketRoute[i] == 1) {
//                        rocketArray[i].setVisibility(View.VISIBLE);
//                    }
//                }
//    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (game_type.equals("1")) {
                DecimalFormat df = new DecimalFormat("##");
                float x = event.values[0];
                x = Float.parseFloat(df.format(x));
                for (int i = 0; i < SIZEOFROUTE; i++) {
                    rocketRoute[i] = 0;
                    rocketArray[i].setVisibility(View.INVISIBLE);
                }
                if (x >= 6) {
                    rocketRoute[0] = 1;
                } else if (x >= 2 && x < 6) {
                    rocketRoute[1] = 1;
                } else if (x <= 2 && x >= -2) {
                    rocketRoute[2] = 1;
                } else if (x > -6 && x < -2) {
                    rocketRoute[3] = 1;
                } else {
                    rocketRoute[4] = 1;
                }
                for (int i = 0; i < SIZEOFROUTE; i++) {
                    if (rocketRoute[i] == 1) {
                        rocketArray[i].setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public boolean isSensorExist(int sensorType) {
        return (sensorManager.getDefaultSensor(sensorType) != null);
    }

    private void easyGame() {
        panel_IMG_lArrow.setOnClickListener(v -> next(true));
        panel_IMG_lView.setOnClickListener(v -> next(true));
        panel_IMG_rArrow.setOnClickListener(v -> next(false));
        panel_IMG_rView.setOnClickListener(v -> next(false));
    }

    private void updateUI() {
        startTicker();
    }

    public void setDelay(int DELAY) {
        this.DELAY = DELAY;
        startTicker();
    }

    private void startTicker() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(ts = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    checkHit();
                    rocksMaker();
                });
            }
        }, 0, DELAY);
    }

    private void rocksMaker() {
        Random rand = new Random();
        int randomNum = rand.nextInt(SIZEOFROUTE);
        int blockKind = 1 + rand.nextInt(5);
        for (int i = vals.length - 1; i >= 1; i--) {
            for (int j = 0; j < vals[i].length; j++) {
                if (vals[i - 1][j] >= 1) {
                    vals[i][j] = vals[i - 1][j];
                    vals[i - 1][j] = 0;
                    break;
                }
            }
        }

        if (rocksSpace == 0) {
            vals[0][randomNum] = blockKind; //1
            rocksSpace++;
        } else {
            vals[0][randomNum] = 0;
            rocksSpace--;
        }

        for (int i = 0; i < vals.length; i++) {
            for (int j = 0; j < vals[i].length; j++) {

                if (vals[i][j] == 1) {
                    ImageView im2 = imgArray[i][j];
                    im2.setImageResource(R.drawable.rock);
                    imgArray[i][j].setVisibility(View.VISIBLE);
                } else if (vals[i][j] == 2) {
                    ImageView im2 = imgArray[i][j];
                    imgArray[i][j].setVisibility(View.VISIBLE);
                    im2.setImageResource(R.drawable.roadblock);
                } else if (vals[i][j] == 3) {
                    ImageView im2 = imgArray[i][j];
                    imgArray[i][j].setVisibility(View.VISIBLE);
                    im2.setImageResource(R.drawable.black_hole);
                } else if (vals[i][j] == 4) {
                    ImageView im2 = imgArray[i][j];
                    imgArray[i][j].setVisibility(View.VISIBLE);
                    im2.setImageResource(R.drawable.coin);
                } else if (vals[i][j] == 5) {
                    ImageView im2 = imgArray[i][j];
                    imgArray[i][j].setVisibility(View.VISIBLE);
                    im2.setImageResource(R.drawable.coins);
                } else {
                    imgArray[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void checkHit() {
        for (int i = 0; i < imgArray[4].length; i++) {
            if (vals[SIZEOFROWS - 1][i] > 3 && rocketRoute[i] == 1) {
                addPoint(i);
                vals[SIZEOFROWS - 1][i] = 0;
            } else if (vals[SIZEOFROWS - 1][i] >= 1 && rocketRoute[i] == 1) {
                vals[SIZEOFROWS - 1][i] = 0;
                heartCount();
            } else if (vals[SIZEOFROWS - 1][i] >= 1 && rocketRoute[i] == 0) {
                vals[SIZEOFROWS - 1][i] = 0;
            }
        }
    }

    private void addPoint(int i) {
        //  sound();
        if (vals[SIZEOFROWS - 1][i] == 4) {
            score += 100;
        } else
            score += 200;
        panel_textView.setText("score: " + score);
    }

    private void sound() {
        MediaActionSound sound = new MediaActionSound();
        sound.play(MediaActionSound.STOP_VIDEO_RECORDING);
    }

    private void heartCount() {
        //vibrate();
        if (panel_IMG_heart1.getVisibility() == View.VISIBLE) {
            panel_IMG_heart1.setVisibility(View.INVISIBLE);
        } else if (panel_IMG_heart2.getVisibility() == View.VISIBLE) {
            panel_IMG_heart2.setVisibility(View.INVISIBLE);
        } else {
            panel_IMG_heart3.setVisibility(View.INVISIBLE);
            stopTicker();
            endMatch();
            finish();
        }
    }

    private void endMatch() {

        String js = MSPV.getMe().getString("MY_DB", "");
        if (!js.equals("")) {
            myDB = new Gson().fromJson(js, MyDB.class);
        }else{
            myDB =new MyDB();
        }

        if (myDB.getIn(score, strlot, strlat)) {
            Intent myIntent = new Intent(this, TopTenActivity.class);
            String json = new Gson().toJson(myDB);
            MSPV.getMe().putString("MY_DB", json);
            Bundle bundle = new Bundle();
            bundle.putString(TopTenActivity.GAME_TYPE, game_type);
            myIntent.putExtra("Bundle", bundle);
            startActivity(myIntent);
        }

    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }


    private void setRocksInvisible(ImageView[][] imgArray) {
        for (int i = 0; i < SIZEOFROUTE; i++) {
            if (rocketRoute[i] == 1) {
                rocketArray[i].setVisibility(View.VISIBLE);
            } else {
                rocketArray[i].setVisibility(View.INVISIBLE);
            }
        }
        for (ImageView[] imageViews : imgArray) {
            for (ImageView imageView : imageViews) {
                imageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void next(boolean left) {
        if (left) {
            for (int i = 0; i < SIZEOFROUTE; i++) {
                if (rocketRoute[i] == 1) {
                    rocketRoute[i] = 0;
                    rocketRoute[i - 1] = 1;
                    rocketArray[i].setVisibility(View.INVISIBLE);
                    rocketArray[i - 1].setVisibility(View.VISIBLE);
                }
            }
        } else {
            for (int i = SIZEOFROUTE - 1; i >= 0; i--) {
                if (rocketRoute[i] == 1) {
                    rocketRoute[i] = 0;
                    rocketRoute[i + 1] = 1;
                    rocketArray[i].setVisibility(View.INVISIBLE);
                    rocketArray[i + 1].setVisibility(View.VISIBLE);
                }
            }

        }
        if (!game_type.equals("1")) {
            if (rocketRoute[SIZEOFROUTE - 1] == 1) {
                panel_IMG_rArrow.setVisibility(View.INVISIBLE);
            } else {
                panel_IMG_rArrow.setVisibility(View.VISIBLE);
            }
            if (rocketRoute[0] == 1) {
                panel_IMG_lArrow.setVisibility(View.INVISIBLE);
            } else {
                panel_IMG_lArrow.setVisibility(View.VISIBLE);
            }
        }

    }

    @SuppressLint("WrongViewCast")
    private void findViews() {
        imgArray = new ImageView[][]{
                {findViewById(R.id.panel_IMG_rockPlace1), findViewById(R.id.panel_IMG_rockPlace2), findViewById(R.id.panel_IMG_rockPlace3), findViewById(R.id.panel_IMG_rockPlace4), findViewById(R.id.panel_IMG_rockPlace5)},
                {findViewById(R.id.panel_IMG_rockPlace6), findViewById(R.id.panel_IMG_rockPlace7), findViewById(R.id.panel_IMG_rockPlace8), findViewById(R.id.panel_IMG_rockPlace9), findViewById(R.id.panel_IMG_rockPlace10)},
                {findViewById(R.id.panel_IMG_rockPlace11), findViewById(R.id.panel_IMG_rockPlace12), findViewById(R.id.panel_IMG_rockPlace13), findViewById(R.id.panel_IMG_rockPlace14), findViewById(R.id.panel_IMG_rockPlace15)},
                {findViewById(R.id.panel_IMG_rockPlace16), findViewById(R.id.panel_IMG_rockPlace17), findViewById(R.id.panel_IMG_rockPlace18), findViewById(R.id.panel_IMG_rockPlace19), findViewById(R.id.panel_IMG_rockPlace20)},
                {findViewById(R.id.panel_IMG_rockPlace21), findViewById(R.id.panel_IMG_rockPlace22), findViewById(R.id.panel_IMG_rockPlace23), findViewById(R.id.panel_IMG_rockPlace24), findViewById(R.id.panel_IMG_rockPlace25)},
                {findViewById(R.id.panel_IMG_rockPlace26), findViewById(R.id.panel_IMG_rockPlace27), findViewById(R.id.panel_IMG_rockPlace28), findViewById(R.id.panel_IMG_rockPlace29), findViewById(R.id.panel_IMG_rockPlace30)}
        };
        rocketArray = new ImageView[]
                {findViewById(R.id.panel_IMG_Rocket1), findViewById(R.id.panel_IMG_Rocket2), findViewById(R.id.panel_IMG_Rocket3), findViewById(R.id.panel_IMG_Rocket4), findViewById(R.id.panel_IMG_Rocket5)};
        vals = new int[SIZEOFROWS][SIZEOFROUTE];
        rocketRoute = new int[SIZEOFROUTE];
        rocketRoute[((SIZEOFROUTE / 2))] = 1;
        panel_IMG_lView = findViewById(R.id.panel_IMG_lView);
        panel_IMG_rView = findViewById(R.id.panel_IMG_rView);
        panel_textView = findViewById(R.id.panel_textView);
        panel_textView.setText("score: " + score);
        panel_IMG_speed = findViewById(R.id.panel_IMG_speed);
        panel_IMG_heart1 = findViewById(R.id.panel_IMG_heart1);
        panel_IMG_heart2 = findViewById(R.id.panel_IMG_heart2);
        panel_IMG_heart3 = findViewById(R.id.panel_IMG_heart3);
        panel_IMG_rArrow = findViewById(R.id.panel_IMG_rArrow);
        panel_IMG_lArrow = findViewById(R.id.panel_IMG_lArrow);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();
    }

    private void stopTicker() {
        ts.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accSensorEventListener);
    }
}



