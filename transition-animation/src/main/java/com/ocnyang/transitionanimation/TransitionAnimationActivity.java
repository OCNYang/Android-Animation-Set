package com.ocnyang.transitionanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ocnyang.transitionanimation.helper.GridSpacingItemDecoration;

/*
 * TransitionAnimationActivity
 *
 *******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/7/24 15:14.
 *    *     *   *         *   * *       EMail: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public class TransitionAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_animation);
        setupWindowAnimations();
        initToolbar();

        initTransitionsView();

        initRecyclerView();
    }

    private void initTransitionsView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.viewgroup_transition);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            viewGroup.getChildAt(i).setOnClickListener(this);
        }
    }


    /**
     * TransitionsViewGroup's item onClick Event
     *
     * @param v v's position in v's parent(TransitionsViewGroup):
     *          position =
     *          0: Explode by code;
     *          1: Explode by xml;
     *          2: Slide by code;
     *          3: Slide by xml;
     *          4: Exit;
     *          5: Exit by Overriding return transition.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onClick(View v) {
        for (int i = 0; i < ((ViewGroup) v.getParent()).getChildCount(); i++) {
            if (v == (((ViewGroup) v.getParent()).getChildAt(i))) {
                switch (i) {
                    case 0:
                        Intent explodeByCodeIntent = new Intent(TransitionAnimationActivity.this, TransitionAnimationByExplodeActivity.class);
                        explodeByCodeIntent.putExtra(TransitionAnimationByExplodeActivity.EXPLODE_TYPE, TransitionAnimationByExplodeActivity.EXPLODE_CODE);
                        startActivity(explodeByCodeIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
                        break;
                    case 1:
                        Intent explodeByXmlIntent = new Intent(TransitionAnimationActivity.this, TransitionAnimationByExplodeActivity.class);
                        explodeByXmlIntent.putExtra(TransitionAnimationByExplodeActivity.EXPLODE_TYPE, TransitionAnimationByExplodeActivity.EXPLODE_XML);
                        startActivity(explodeByXmlIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
                        break;
                    case 2:
                        Intent slideByCodeIntent = new Intent(TransitionAnimationActivity.this, TransitionAnimationBySlideActivity.class);
                        slideByCodeIntent.putExtra(TransitionAnimationBySlideActivity.SLIDE_TYPE, TransitionAnimationBySlideActivity.SLIDE_CODE);
                        startActivity(slideByCodeIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
                        break;
                    case 3:
                        Intent slideByXmlIntent = new Intent(TransitionAnimationActivity.this, TransitionAnimationBySlideActivity.class);
                        slideByXmlIntent.putExtra(TransitionAnimationBySlideActivity.SLIDE_TYPE, TransitionAnimationBySlideActivity.SLIDE_XML);
                        startActivity(slideByXmlIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
                        break;
                    case 4:
                        finishAfterTransition();
                        break;
                    case 5:
                        Visibility returnTransition = buildReturnTransition();
                        getWindow().setReturnTransition(returnTransition);
                        finishAfterTransition();
                        break;
                    default:
                        break;
                }
                break;
            }
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_share_elements);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(this));
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
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(500);
        return enterTransition;
    }
}
