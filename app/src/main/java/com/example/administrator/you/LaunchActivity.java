package com.example.administrator.you;

import android.animation.Animator;
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
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionManager;
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

            "有这么一个人，她可爱善良",
            "美丽动人，温柔大方",
            "这位可爱小姐姐的",
            "学名就叫做Teacher Gao!",
            "祝福她教师节快乐！",
            "Happy Teachers' Day!"
    };

    private ConstraintLayout clMain;
    private ConstraintSet csMain = new ConstraintSet();

    private int index = 0;

    private ImageView ivMagic;

    private HTextView hTextView;

    private boolean isFirstOnResume = true;
    private FloatingActionButton fabJump;

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

        clMain = (ConstraintLayout) findViewById(R.id.cl_main_launcher);
        csMain.clone(clMain);

        ivMagic = (ImageView) findViewById(R.id.iv_magic_launcher);
        ivMagic.setAlpha(0f);

        hTextView = (HTextView) findViewById(R.id.htv_welcome);

        fabJump = (FloatingActionButton) findViewById(R.id.fab_jump_launcher);
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

        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if (isFirstOnResume && fabJump.getVisibility() != View.VISIBLE) {

                    animFabJumpVisible();
                    isFirstOnResume = false;
                    mHandler.sendEmptyMessage(0);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
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
                }, 1200);

                break;
        }
    }

    private void bingBoomView(View view) {

        ImageView iv = (ImageView) findViewById(R.id.iv_bing_launcher);
        ExplosionField explosionField = ExplosionField.attach2Window(this);
        explosionField.explode(view);
        explosionField.explode(iv);
    }

    private void animFabJumpVisible() {

        TransitionManager.beginDelayedTransition(clMain);
        csMain.setVisibility(R.id.fab_jump_launcher, ConstraintSet.VISIBLE);
        csMain.applyTo(clMain);
    }
}
