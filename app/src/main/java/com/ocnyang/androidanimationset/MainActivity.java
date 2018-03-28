package com.ocnyang.androidanimationset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ocnyang.viewanimation.ViewAnimationActivity;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.view_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewAnimationActivity.class));
            }
        });
        mRecyclerView = ((RecyclerView) findViewById(R.id.recycler));
        mRecyclerView.setFocusable(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new MyRecyclerViewAdapter(this,
                getResources().getIntArray(R.array.itemColor),
                getResources().getStringArray(R.array.itemName));
        mRecyclerView.setAdapter(mAdapter);

    }

}
