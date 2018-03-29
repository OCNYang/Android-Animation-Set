package com.ocnyang.vectoranimation;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class VectorAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_animation);
        initToolbar();
        findViewById(R.id.iv_puppet1).setOnClickListener(this);
        initPuppet2();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initPuppet3();
        }
    }

    private void initPuppet2() {
        ImageView imageView = (ImageView) findViewById(R.id.iv_puppet2);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.drawable_animated_vector));
        imageView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initPuppet3() {
        ImageView imageView = (ImageView) findViewById(R.id.iv_puppet3);
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AnimatedVectorDrawable) {
            ((AnimatedVectorDrawable) drawable).registerAnimationCallback(new Animatable2.AnimationCallback() {
                @Override
                public void onAnimationStart(Drawable drawable) {
                    super.onAnimationStart(drawable);
                }

                @Override
                public void onAnimationEnd(Drawable drawable) {
                    super.onAnimationEnd(drawable);
                    ((AnimatedVectorDrawable) drawable).unregisterAnimationCallback(this);
                }
            });
            imageView.setOnClickListener(this);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
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

    @Override
    public void onClick(View v) {
        if (v instanceof ImageView) {
            Drawable drawable = ((ImageView) v).getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        }
    }
}
