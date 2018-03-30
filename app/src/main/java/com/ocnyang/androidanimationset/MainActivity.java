package com.ocnyang.androidanimationset;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((SimpleDraweeView) findViewById(R.id.view_header))
                .setImageURI(Uri.parse("res://" + getPackageName() + "/" + R.drawable.header_bg));

        mRecyclerView = ((RecyclerView) findViewById(R.id.recycler));
        mRecyclerView.setFocusable(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setNestedScrollingEnabled(false);

        mAdapter = new MyRecyclerViewAdapter(this,
                new int[]{R.drawable.view_gif, R.drawable.drawable_gif, R.drawable.property_gif, R.drawable.ripple_gif,
                        R.drawable.reveal_effect_gif, R.drawable.transition_gif, R.drawable.view_state_gif, R.drawable.vector_gif},
                getResources().getStringArray(R.array.itemName));
        mRecyclerView.setAdapter(mAdapter);

    }

}
