package com.example.hw2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private MaterialButton easyButton;
    private MaterialButton hardButtun;
    private MaterialButton topTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}