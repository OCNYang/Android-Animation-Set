package com.ocnyang.propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

public class PropertyAnimationActivity extends AppCompatActivity {

    private View mPuppet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        mPuppet = findViewById(R.id.view_puppet);
        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_property, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
        } else if (i == R.id.action_do_byxml) {
            doAnimation(getAnimationDrawable(false));
        } else if (i == R.id.action_stop_bycode) {
            doAnimation(getAnimationDrawable(true));
        }
        return super.onOptionsItemSelected(item);
    }

    private Animator getAnimationDrawable(boolean formXml) {
        return formXml ? getAnimationByXml() : getAnimatorSet();
    }

    /**
     * Animator usage
     *
     * @param animator
     */
    private void doAnimation(Animator animator) {
        animator.start();
        //animator.cancel();
        //animator.end();
        //animator.isPaused();
        //animator.isRunning();
        //animator.isStarted();
    }

    /**
     * use property animation by xml;
     * @return
     */
    private Animator getAnimationByXml() {
        final int height = mPuppet.getLayoutParams().height;
        final int width = mPuppet.getLayoutParams().width;
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animatorset);

        //ValueAnimator usage:add AnimatorUpdateListener;
        ArrayList<Animator> childAnimations = animatorSet.getChildAnimations();
        ((ValueAnimator) childAnimations.get(childAnimations.size() - 1))
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedValue = (float) valueAnimator.getAnimatedValue();
                        mPuppet.getLayoutParams().height = (int) (height * animatedValue);
                        mPuppet.getLayoutParams().width = (int) (width * animatedValue);
                        mPuppet.requestLayout();
                    }
                });

        animatorSet.setTarget(mPuppet);
        return animatorSet;
    }

    /**
     * ObjectAnimator usage
     *
     * @param b
     * @return
     */
    public ObjectAnimator getObjectAnimator(boolean b) {
        if (b) {
            ObjectAnimator bgColorAnimator = ObjectAnimator.ofArgb(mPuppet,
                    "backgroundColor",
                    0xff009688, 0xff795548);
            bgColorAnimator.setRepeatCount(1);
            bgColorAnimator.setDuration(3000);
            bgColorAnimator.setRepeatMode(ValueAnimator.REVERSE);
            bgColorAnimator.setStartDelay(0);
            return bgColorAnimator;
        } else {
            ObjectAnimator rotationXAnimator = ObjectAnimator.ofFloat(mPuppet,
                    "rotationX",
                    0f, 360f);
            rotationXAnimator.setRepeatCount(1);
            rotationXAnimator.setDuration(3000);
            rotationXAnimator.setRepeatMode(ValueAnimator.REVERSE);
            return rotationXAnimator;
        }
    }

    /**
     * ObjectAnimator: You can also create multiple animations by PropertyValuesHolder.
     * <p>
     * ValueAnimator has the same method, but we don't use it that way. #ValueAnimator.ofPropertyValuesHolder()#
     *
     * @return
     */
    public Animator getObjectAnimatorByPropertyValuesHolder() {
        PropertyValuesHolder bgColorAnimator = PropertyValuesHolder.ofObject("backgroundColor",
                new ArgbEvaluator(),
                0xff009688, 0xff795548);
        PropertyValuesHolder rotationXAnimator = PropertyValuesHolder.ofFloat("rotationX",
                0f, 360f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mPuppet, bgColorAnimator, rotationXAnimator);
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        return objectAnimator;
    }

    /**
     * ValueAnimator usage
     *
     * @return
     */

    public ValueAnimator getValueAnimator() {
        final int height = mPuppet.getLayoutParams().height;
        final int width = mPuppet.getLayoutParams().width;

        ValueAnimator sizeValueAnimator = ValueAnimator.ofFloat(1f, 3f);
        sizeValueAnimator.setDuration(3000);
        sizeValueAnimator.setRepeatCount(1);
        sizeValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        sizeValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mPuppet.getLayoutParams().height = (int) (height * animatedValue);
                mPuppet.getLayoutParams().width = (int) (width * animatedValue);
                mPuppet.requestLayout();
            }
        });
        return sizeValueAnimator;
    }

    /**
     * AnimatorSet usage
     *
     * @return
     */
    public AnimatorSet getAnimatorSet() {
        AnimatorSet animatorSet = new AnimatorSet();

        // play together
        {
//            animatorSet.play(getObjectAnimator(true))
//                    .with(getObjectAnimator(false))
//                    .with(getValueAnimator());
        }

        //play sequentially
        {
//            animatorSet.play(getObjectAnimator(true))
//                    .after(getObjectAnimator(false))
//                    .before(getValueAnimator());
        }

        animatorSet.playTogether(getObjectAnimatorByPropertyValuesHolder(), getValueAnimator());

        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        return animatorSet;
    }
}
