package com.ocnyang.revealanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;

public class RevealEffectActivity extends AppCompatActivity implements View.OnClickListener {

    private View mPuppet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_effect);
        mPuppet = findViewById(R.id.view_puppet);
        findViewById(R.id.fab).setOnClickListener(this);
        initToolbar();
    }

    @Override
    public void onClick(View v) {
        int[] vLocation = new int[2];
        v.getLocationInWindow(vLocation);
        int centerX = vLocation[0] + v.getMeasuredWidth() / 2;
        int centerY = vLocation[1] + v.getMeasuredHeight() / 2;

        int height = mPuppet.getHeight();
        int width = mPuppet.getWidth();
        int maxRradius = (int) Math.hypot(height, width);

        final boolean b = mPuppet.getVisibility() == View.VISIBLE;

        if (b) {
            Animator animator = ViewAnimationUtils.createCircularReveal(mPuppet, centerX, centerY, maxRradius, 0);
            animator.setDuration(1000);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mPuppet.setVisibility(View.GONE);
                }
            });
            animator.start();
        } else {
            Animator animator = ViewAnimationUtils.createCircularReveal(mPuppet, centerX, centerY, 0, maxRradius);
            animator.setDuration(1000);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            mPuppet.setVisibility(View.VISIBLE);
            animator.start();
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
