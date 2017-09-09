package com.example.administrator.you;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.hanks.htextview.scale.ScaleTextView;

import tyrantgit.explosionfield.ExplosionField;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {

    private final String[] sWelcome = new String[]{

            "有这么一个人，她可爱善良，貌美如花，温婉贤淑",
            "美丽动人,温柔大方,楚楚动人,闭月羞花,倾国倾城",
            "我们的这位可爱的小姐姐是谁呢？",
            "小姐姐的学名呢，就叫做Teacher Gao!",
            "在这里祝福她教师节快乐！快乐！快乐~~",
            "Happy Teachers' Day!"
    };

    private ConstraintLayout clMain;
    private ConstraintSet csMain = new ConstraintSet();

    private int index = 0;

    private ImageView ivMagic;

    private ScaleTextView hTextView;

    private boolean isFirstOnResume = true;

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
        setContentView(R.layout.activity_launch);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        animObjectMagicIv();
    }

    @Override
    public void onBackPressed() {

        Snackbar.make(clMain, "你确定不再看看了么？小姐姐", 2000).setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        }).show();
    }

    private void doMessage(Message msg) {

        if (msg.what == 0) {

            int indexCurrent = index % sWelcome.length;
            hTextView.animateText(sWelcome[indexCurrent]);
            index++;
            if (index < sWelcome.length)
                mHandler.sendEmptyMessageDelayed(0, 3000);
            else
                animFabJumpVisible();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {

        clMain = (ConstraintLayout) findViewById(R.id.cl_main_launcher);
        csMain.clone(clMain);

        ivMagic = (ImageView) findViewById(R.id.iv_magic_launcher);

        hTextView = (ScaleTextView) findViewById(R.id.htv_welcome);

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
        animationSet.setStartDelay(0);
        animationSet.setInterpolator(new BounceInterpolator());
        animationSet.start();
        ivMagic.setVisibility(View.VISIBLE);

        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if (isFirstOnResume) {

                    isFirstOnResume = false;
                    animAvatarCenterHorizontal();
                } else {

                    animFabJumpVisible();
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
                }, 1000);

                break;
        }
    }

    private void bingBoomView(View view) {

        ImageView iv = (ImageView) findViewById(R.id.iv_bing_launcher);
        ExplosionField explosionField = ExplosionField.attach2Window(this);
        explosionField.explode(view);
        explosionField.explode(iv);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void animFabJumpVisible() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                TransitionManager.beginDelayedTransition(clMain);
                csMain.setVisibility(R.id.fab_jump_launcher, ConstraintSet.VISIBLE);
                csMain.applyTo(clMain);
            }
        }, 2000);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void animAvatarCenterHorizontal() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                TransitionManager.beginDelayedTransition(clMain);
                csMain.setVisibility(R.id.iv_avatar_launcher, ConstraintSet.VISIBLE);
                csMain.setMargin(R.id.iv_avatar_launcher, ConstraintSet.START, 0);
                csMain.setMargin(R.id.iv_avatar_launcher, ConstraintSet.END, 0);
                csMain.centerHorizontally(R.id.iv_avatar_launcher, R.id.cl_main_launcher);
                csMain.applyTo(clMain);

                animNameFlowAvatar();
            }
        }, 800);

    }

    private void animNameFlowAvatar() {

        mHandler.postDelayed(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

                TransitionManager.beginDelayedTransition(clMain);
                csMain.setVisibility(R.id.tv_name_launcher, ConstraintSet.VISIBLE);
                csMain.connect(R.id.iv_avatar_launcher, ConstraintSet.END, R.id.tv_name_launcher, ConstraintSet.START);
                csMain.connect(R.id.tv_name_launcher, ConstraintSet.END, R.id.cl_main_launcher, ConstraintSet.END);
                csMain.applyTo(clMain);

                mHandler.sendEmptyMessageDelayed(0, 800);
            }
        }, 1200);
    }

}
