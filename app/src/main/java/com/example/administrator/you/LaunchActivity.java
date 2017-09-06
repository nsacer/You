package com.example.administrator.you;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class LaunchActivity extends AppCompatActivity {

    private ConstraintLayout clMain;
    private ConstraintSet csMain = new ConstraintSet();

    private ImageView ivMagic;

    private int ivWidth = 0, ivHeight=0;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
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

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                animMagicIv();
//            }
//        }, 400);

        animObjectMagicIv();
    }

    private void initView() {

        clMain = (ConstraintLayout) findViewById(R.id.cl_main_launcher);
        csMain.clone(clMain);

        ivMagic = (ImageView) findViewById(R.id.iv_magic_launcher);
        ivWidth = ivMagic.getLayoutParams().width;
        ivHeight = ivMagic.getLayoutParams().height;
//        ivMagic.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void animMagicIv() {

        TransitionManager.beginDelayedTransition(clMain);
        csMain.constrainWidth(R.id.iv_magic_launcher, ivWidth);
        csMain.constrainHeight(R.id.iv_magic_launcher, ivHeight);
        csMain.applyTo(clMain);
    }

    private void animObjectMagicIv() {

        AnimatorSet animationSet = new AnimatorSet();

        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(ivMagic, "scaleX", 0, 1);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(ivMagic, "scaleY", 0, 1);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(ivMagic, "alpha", 0, 1);

        animationSet.playTogether(animatorScaleX, animatorScaleY, animatorAlpha);
        animationSet.setDuration(400);
        animationSet.setStartDelay(400);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.start();
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                ivMagic.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
