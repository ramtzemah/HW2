package com.example.hw2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private MaterialButton easyButton;
    private MaterialButton hardButtun;
    private MaterialButton topTen;
//    private static final int CAMERA_PERMISSION_CODE = 100;
//    private static final int ACCESS_FINE_LOCATION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // checkPermissions();
        findViews();
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("0");
            }
        });
        hardButtun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("1");
            }
        });

        topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topTen();
            }
        });
    }

    private void topTen() {
        Intent myIntent = new Intent(this, TopTenActivity.class);
        startActivity(myIntent);
    }

    private void startGame(String gameType) {
        Intent myIntent = new Intent(this, TheGameActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(TheGameActivity.GAME_TYPE,gameType);
        myIntent.putExtra("Bundle", bundle);
        startActivity(myIntent);
    }

    private void findViews() {
        easyButton = findViewById(R.id.easyButtun);
        hardButtun = findViewById(R.id.hardButtun);
        topTen = findViewById(R.id.topTen);

    }
    private void checkPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1001  );
            }else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1001  );
            }
        }
    }

}
