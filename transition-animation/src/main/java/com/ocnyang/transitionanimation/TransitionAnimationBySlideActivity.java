package com.ocnyang.transitionanimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.MenuItem;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/7/25 9:10.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public class TransitionAnimationBySlideActivity extends AppCompatActivity {

    public static final String SLIDE_TYPE = "Slide type";
    public static final String SLIDE_CODE = "Slide code";
    public static final String SLIDE_XML = "Slide xml";

    private String mSlideType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_elements);

        mSlideType = getIntent().getExtras().getString(SLIDE_TYPE);

        setupWindowAnimations();

        initToolbar();
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
            //If you use 'finish();' you will not get the animation effect,
            //you can use the following methods instead.
            supportFinishAfterTransition();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupWindowAnimations() {
        Transition transition = null;

        switch (mSlideType) {
            case SLIDE_CODE:
                transition = buildEnterTransitionByCode();
                break;
            case SLIDE_XML:
                transition = buildEnterTransitionByXml();
                break;
            default:
                break;
        }
        getWindow().setEnterTransition(transition);
    }

    private Transition buildEnterTransitionByCode() {
        Slide enterTransition = new Slide();
        enterTransition.setDuration(500);
        enterTransition.setSlideEdge(Gravity.RIGHT);
        return enterTransition;
    }

    private Transition buildEnterTransitionByXml() {
        return TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
    }
}
