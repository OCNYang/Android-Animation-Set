package com.ocnyang.androidanimationset;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ocnyang.drawableanimation.DrawableAnimationActivity;
import com.ocnyang.propertyanimation.PropertyAnimationActivity;
import com.ocnyang.rippleanimation.TouchFeedbackActivity;
import com.ocnyang.viewanimation.ViewAnimationActivity;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2018/3/23 14:06.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final int[] mIntArray;
    private final String[] mStringArray;
    Context mContext;

    public MyRecyclerViewAdapter(Context context, int[] intArray, String[] stringArray) {
        mContext = context;
        mIntArray = intArray;
        mStringArray = stringArray;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setBackgroundColor(mIntArray[position]);
        holder.itemNameTv.setText(mStringArray[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "fuck", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        mContext.startActivity(new Intent(mContext, ViewAnimationActivity.class));
                        break;
                    case 1:
                        mContext.startActivity(new Intent(mContext, DrawableAnimationActivity.class));
                        break;
                    case 2:
                        mContext.startActivity(new Intent(mContext, PropertyAnimationActivity.class));
                        break;
                    case 3:
                        mContext.startActivity(new Intent(mContext, TouchFeedbackActivity.class));
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStringArray != null ? mStringArray.length : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView itemNameTv;
        ImageView itemImg;

        ViewHolder(View view) {
            super(view);
            itemView = view.findViewById(R.id.view_content);
            itemImg = view.findViewById(R.id.img_item);
            itemNameTv = view.findViewById(R.id.tv_item);
        }
    }
}
