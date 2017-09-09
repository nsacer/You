package com.example.administrator.you;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.king.view.flutteringlayout.FlutteringLayout;

import java.util.ArrayList;
import java.util.List;

import customview.BarrageView;
import customview.FlyBanner;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout clMain;
    private ConstraintSet csMain = new ConstraintSet();
    private FlutteringLayout flutteringLayout;
    private GifImageView gifImageView;
    private BarrageView barrageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        barrageView.onWindowFocusChanged(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        barrageView.onWindowFocusChanged(false);
    }

    @Override
    public void onBackPressed() {

        if (gifImageView.getVisibility() == View.VISIBLE)
            animGIVHide();
        else
            super.onBackPressed();
    }

    private void initView() {

        clMain = (ConstraintLayout) findViewById(R.id.cl_main_main);
        csMain.clone(clMain);

        barrageView = (BarrageView) findViewById(R.id.barrage_view);

        gifImageView = (GifImageView) findViewById(R.id.giv_main);

        initFabLove();

        initFabLoveLeft();

        flutteringLayout = (FlutteringLayout) findViewById(R.id.flutter_layout);

        initFlyBanner();

        FloatingActionButton fabPoker = (FloatingActionButton) findViewById(R.id.fab_love_card_main);
        fabPoker.setOnClickListener(this);


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

                if (gifImageView.getVisibility() == View.VISIBLE)
                    animGIVHide();
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

            case R.id.fab_love_card_main:

                if (gifImageView.getVisibility() == View.VISIBLE)
                    animGIVHide();
                startActivity(new Intent(this, CardActivity.class));
                break;
        }
    }

    private void clickLoveFab() {

        if (gifImageView.getVisibility() == View.VISIBLE)
            animGIVHide();

        int count = (int) (Math.random() * 20);
        for (int i = 0; i < count; i++) {

            flutteringLayout.addHeart();
        }
    }

    private void clickFabLoveLeft() {

        if (gifImageView.getVisibility() == View.VISIBLE)
            animGIVHide();
        else
            animGIVShow();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void animGIVShow() {

        TransitionManager.beginDelayedTransition(clMain);
        csMain.setVisibility(R.id.giv_main, ConstraintSet.VISIBLE);
        csMain.applyTo(clMain);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void animGIVHide() {

        TransitionManager.beginDelayedTransition(clMain);
        csMain.setVisibility(R.id.giv_main, ConstraintSet.INVISIBLE);
        csMain.applyTo(clMain);
    }
}
