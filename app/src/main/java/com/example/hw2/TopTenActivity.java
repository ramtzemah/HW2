package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class TopTenActivity extends AppCompatActivity {
    public static final String GAME_TYPE = "GAME_TYPE";
    public MyDB myDB;
    private ImageView returnbutton;
    private ImageView reGame;
    private Fragment_List fragmentList;
    private Fragment_Map fragmentMap;
    private ArrayList<Record> records;
    private String gameType = "";
    Adapter_Record adapter_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_ten);

        fragmentList = new Fragment_List();
        fragmentList.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentList).commit();

        fragmentMap = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.frame2, fragmentMap).commit();

        findViews();
        onStart();

        Bundle extras = getIntent().getBundleExtra("Bundle");
        if (extras != null) {
            gameType = extras.getString("GAME_TYPE");
        }

//  <ImageView
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:layout_centerInParent="true"
//        android:scaleType="centerCrop"
//        android:src="@drawable/background2"/>
        String js = MSPV.getMe().getString("MY_DB", "");
        MyDB myDB = new Gson().fromJson(js, MyDB.class);

        ArrayList<Record> records = myDB.getBestScore();

        adapter_record = new Adapter_Record(this, records);


        fragmentList.getMain_LST_records().setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        fragmentList.getMain_LST_records().setHasFixedSize(true);
        fragmentList.getMain_LST_records().setItemAnimator(new DefaultItemAnimator());
        fragmentList.getMain_LST_records().setAdapter(adapter_record);

        returnbutton.setOnClickListener(v -> finish());
        reGame.setOnClickListener(v -> onClick());


        adapter_record.setRecordItemClickListener(new Adapter_Record.RecordItemClickListener() {
            @Override
            public void recordItemClicked(Record item, String lat, String lot) {
                    Log.d("pttt", "positioncccc");
                    fragmentMap.setOnMap(lat,lot);

            }
        });
    }

    private void onClick(){
        if(gameType.equals("")){
            return;
        }
        Intent myIntent = new Intent(this, TheGameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TheGameActivity.GAME_TYPE,gameType);
        myIntent.putExtra("Bundle", bundle);
        startActivity(myIntent);
        finish();
    }

    private void findViews() {
        reGame = findViewById(R.id.reGame);
        returnbutton = findViewById(R.id.returnbutton);
        myDB = new MyDB();
    }





    @Override
    protected void onStart() {
        super.onStart();
        fragmentList.getActivity();
        fragmentMap.getActivity();
        int x = 9;
    }
}
