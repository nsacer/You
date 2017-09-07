package com.example.administrator.you;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.king.view.flutteringlayout.FlutteringLayout;

import java.util.ArrayList;
import java.util.List;

import customview.FlyBanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FlutteringLayout flutteringLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        initFabLove();

        initFabLoveLeft();

        flutteringLayout = (FlutteringLayout) findViewById(R.id.flutter_layout);

        initFlyBanner();

    }

    private void initFabLove() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_love_main);
        fab.setOnClickListener(this);
    }

    private void initFabLoveLeft() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_love_left_main);
        fab.setOnClickListener(this);
    }

    private void initFlyBanner() {

        List<Integer> integers = new ArrayList<>();
        integers.add(R.mipmap.g1);
        integers.add(R.mipmap.g2);
        integers.add(R.mipmap.g3);
        integers.add(R.mipmap.g4);
        integers.add(R.mipmap.g5);
        integers.add(R.mipmap.g6);
        integers.add(R.mipmap.g7);
        integers.add(R.mipmap.g8);
        integers.add(R.mipmap.g9);
        integers.add(R.mipmap.g10);

        FlyBanner flyBanner = (FlyBanner) findViewById(R.id.fly_banner_main);
        flyBanner.setImages(integers);

        flyBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fab_love_main:

                clickLoveFab();
                break;

            case R.id.fab_love_left_main:

                clickFabLoveLeft();
                break;
        }
    }

    private void clickLoveFab() {

        int count = (int) (Math.random() * 20);
        for (int i = 0; i < count; i++) {

            flutteringLayout.addHeart();
        }
    }

    private void clickFabLoveLeft() {

    }
}
