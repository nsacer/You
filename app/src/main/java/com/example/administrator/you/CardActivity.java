package com.example.administrator.you;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import adapter.CardAdapter;
import customview.SpeedRecyclerView;
import helper.CardScaleHelper;

public class CardActivity extends AppCompatActivity {

    private ConstraintLayout clMain;
    private ConstraintSet csMain = new ConstraintSet();
    private TextView tvTalk;
    private ConstraintLayout clTalk;

    private String[] talks = new String[]{

            "我总想\n射点什么...",
            "官人想看我\n长舞一曲吗？",
            "你想见识一下\n本宫的厉害吗？",
            "奴家可还\n没有准备好呢",
            "喂！看什么看！\n还不快从了本宫",
            "大人，小女子\n为您吹奏一曲可好",
            "我的大刀\n早已经饥渴难耐",
            "小女子的表演\n您可还满意？",
            "赶紧洗洗\n去睡吧你"
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            doMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_card);

        initView();
    }

    private void initView() {

        clMain = (ConstraintLayout) findViewById(R.id.cl_main_card);
        csMain.clone(clMain);

        clTalk = (ConstraintLayout) findViewById(R.id.cl_talk_card);
        clTalk.setVisibility(View.INVISIBLE);
        tvTalk = (TextView) findViewById(R.id.tv_talk);

        initRecyclerView();

    }

    private void initRecyclerView() {

        SpeedRecyclerView recyclerView = (SpeedRecyclerView) findViewById(R.id.srv_card);
        final LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);

        CardAdapter adapter = new CardAdapter(this);
        recyclerView.setAdapter(adapter);

        CardScaleHelper helper = new CardScaleHelper();
        helper.attachToRecyclerView(recyclerView);


        adapter.setOnCardItemClickListener(new CardAdapter.OnCardItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, ViewSwitcher vs) {

                animShowTalk(position);
                vs.setDisplayedChild(1);
            }
        });
    }

    private void doMessage(Message msg) {


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void animShowTalk(int position) {

        if (clTalk.getVisibility() == View.VISIBLE)
            return;
        tvTalk.setText(talks[position]);
        TransitionManager.beginDelayedTransition(clMain);
        csMain.setVisibility(R.id.cl_talk_card, ConstraintSet.VISIBLE);
        csMain.applyTo(clMain);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                animHideTalk();
            }
        }, 2000);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void animHideTalk() {

        TransitionManager.beginDelayedTransition(clMain);
        csMain.setVisibility(R.id.cl_talk_card, ConstraintSet.INVISIBLE);
        csMain.applyTo(clMain);
    }

}
