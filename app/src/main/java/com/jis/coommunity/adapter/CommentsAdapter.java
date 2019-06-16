package com.jis.coommunity.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jis.coommunity.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private int photo[] = {R.drawable.user_photo,R.drawable.follower2};
    private String name[] = {"Angela Walker","lia Shieton","Justine Henderson","Waylon Dalton"};
    public CommentsAdapter() {

    }
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_comments, null);
        itemLayoutView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txtViewTitle.setText(name[position%4]);
        viewHolder.imgViewIcon.setImageResource(photo[position%2]);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public CircleImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.comment_name);
            imgViewIcon = (CircleImageView) itemLayoutView.findViewById(R.id.photo);
        }
    }
    @Override
    public int getItemCount() {
        return 10;
    }
}