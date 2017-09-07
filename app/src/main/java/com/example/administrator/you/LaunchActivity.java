package com.example.administrator.you;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.hanks.htextview.HTextView;

import tyrantgit.explosionfield.ExplosionField;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {

    private final String[] sWelcome = new String[]{

            "曾经沧海难为水",
            "除却巫山不是云",
            "何当共剪西窗烛",
            "却话巴山夜雨时",
            "轻罗小扇扑流萤"
    };
    private int index = 0;

    private ImageView ivMagic;

    private HTextView hTextView;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        animObjectMagicIv();
        mHandler.sendEmptyMessage(0);
    }

    private void doMessage(Message msg) {

        if (msg.what == 0) {

            int indexCurrent = index % sWelcome.length;
            hTextView.animateText(sWelcome[indexCurrent]);
            index++;
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    private void initView() {

        ivMagic = (ImageView) findViewById(R.id.iv_magic_launcher);
        ivMagic.setAlpha(0f);

        hTextView = (HTextView) findViewById(R.id.htv_welcome);

        FloatingActionButton fabJump = (FloatingActionButton) findViewById(R.id.fab_jump_launcher);
        fabJump.setOnClickListener(this);

    }

    private void animObjectMagicIv() {

        AnimatorSet animationSet = new AnimatorSet();

        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(ivMagic, "scaleX", 0, 1);

        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(ivMagic, "scaleY", 0, 1);

        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(ivMagic, "alpha", 0, 1);

        animationSet.playTogether(animatorScaleX, animatorScaleY, animatorAlpha);
        animationSet.setDuration(2000);
        animationSet.setStartDelay(400);
        animationSet.setInterpolator(new BounceInterpolator());
        animationSet.start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fab_jump_launcher:

                bingBoomView(v);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                    }
                }, 1600);

                break;
        }
    }

    private void bingBoomView(View view) {

        ImageView iv = (ImageView) findViewById(R.id.iv_bing_launcher);
        ExplosionField explosionField = ExplosionField.attach2Window(this);
        explosionField.explode(view);
        explosionField.explode(iv);
    }
}
