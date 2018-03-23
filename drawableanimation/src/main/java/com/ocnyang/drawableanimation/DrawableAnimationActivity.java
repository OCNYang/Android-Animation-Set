package com.ocnyang.drawableanimation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class DrawableAnimationActivity extends AppCompatActivity {

    private ImageView mPuppet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_animation);
        initToolbar();
        mPuppet = findViewById(R.id.img_puppet);
    }

    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_off, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
        } else if (i == R.id.action_do) {
            doAnimation(getAnimationDrawable(false), true);
        } else if (i == R.id.action_stop) {
            doAnimation(getAnimationDrawable(true), false);
        }
        return super.onOptionsItemSelected(item);
    }

    private void doAnimation(AnimationDrawable animationDrawable, boolean doIt) {
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }

        //When you want to restart the animation, stop the animation first.
        if (doIt) {
            animationDrawable.start();
        }
    }

    private AnimationDrawable getAnimationDrawable(boolean fromXml) {
        if (fromXml) {
            //mPuppet.setImageDrawable(getResources().getDrawable(R.drawable.run));
            AnimationDrawable animationDrawable = (AnimationDrawable) mPuppet.getDrawable();
            //animationDrawable.setOneShot(false);
            return animationDrawable;
        } else {
            AnimationDrawable animationDrawable = new AnimationDrawable();
            for (int i = 1; i < 8; i++) {
                int id = getResources().getIdentifier("run" + i, "drawable", getPackageName());
                animationDrawable.addFrame(getResources().getDrawable(id), 100);
            }
            mPuppet.setImageDrawable(animationDrawable);
            return animationDrawable;
        }
    }
}
