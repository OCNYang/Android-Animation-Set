package com.ocnyang.transitionanimation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ocnyang.transitionanimation.helper.TransitionHelper;

import de.hdodenhof.circleimageview.CircleImageView;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2018/3/23 14:06.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    Context mContext;

    public MyRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_recycler_share_elements, parent, false));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat activityOptionsCompat;
                if (holder.getAdapterPosition() % 2 == 0) {
                    Pair<View, String> pair1 = new Pair<View, String>(holder.mImageView, mContext.getString(R.string.share_element_imageview));
                    Pair<View, String> pair2 = new Pair<View, String>(holder.mHeader, mContext.getString(R.string.share_element_header));
                    Pair<View, String> pair3 = new Pair<View, String>(holder.mTextView, mContext.getString(R.string.share_element_tv_info));
                    activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) mContext), pair1, pair2, pair3);
                } else {
                    final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(
                            ((Activity) mContext), true,
                            new Pair<>(holder.mImageView, mContext.getString(R.string.share_element_imageview)),
                            new Pair<>(holder.mHeader, mContext.getString(R.string.share_element_header)),
                            new Pair<>(holder.mTextView, mContext.getString(R.string.share_element_tv_info)));
                    activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) mContext), pairs);
                }

                Intent intent = new Intent(mContext, ShareElementsActivity.class);
                mContext.startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView mImageView;
        CircleImageView mHeader;
        TextView mTextView;

        ViewHolder(View view) {
            super(view);
            itemView = view;
            mImageView = view.findViewById(R.id.imageview);
            mHeader = view.findViewById(R.id.header);
            mTextView = view.findViewById(R.id.tv_info);
        }
    }
}
