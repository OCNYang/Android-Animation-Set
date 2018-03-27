package com.ocnyang.revealanimation;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;

public class RevealAnimationActivity extends AppCompatActivity {

    boolean flag;
    FloatingActionButton fab;
    private View mPuppet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_animation);
        initToolbar();
        mPuppet = findViewById(R.id.view_puppet);
        flag = mPuppet.getVisibility() == View.VISIBLE;

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRevealAnimation();
            }
        });
    }

    private void launchRevealAnimation() {
        Animation animation = mPuppet.getAnimation();
        if (animation != null) {
            animation.cancel();
        }

        int[] vLocation = new int[2];
        fab.getLocationInWindow(vLocation);
        int centerX = vLocation[0] + fab.getWidth() / 2;
        int centerY = vLocation[1] + fab.getHeight() / 2;

        View view = findViewById(R.id.toolbar_layout);
        int hypotenuse = (int) Math.hypot(view.getWidth(), view.getHeight());

        if (flag) {
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(mPuppet, centerX, centerY, hypotenuse, 0);
            circularReveal.setDuration(2000);
            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mPuppet.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            circularReveal.start();
            flag = false;
        } else {
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(mPuppet, centerX, centerY, 0, hypotenuse);
            circularReveal.setDuration(2000);
            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mPuppet.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            circularReveal.start();
            flag = true;
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
