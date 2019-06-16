package com.jis.coommunity.adapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jis.coommunity.R;
import com.jis.coommunity.user.OtherProfileActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolder> {
    private boolean status_flag[] = {true,false,false,true,true,false,false,true,true,true};
    private int photo[] = {R.drawable.follower2};
    private String name[] = {"Angela Walker","lia Shieton","Justine Henderson","Waylon Dalton"};
    private Activity activity;
    public FollowerAdapter(Activity a) {
        activity = a;
    }
    @Override
    public FollowerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_profile_follower, null);
        itemLayoutView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(name[position%4]);
        viewHolder.photo.setImageResource(photo[0]);
        if(this.status_flag[position])
        {
            viewHolder.status.setBackgroundResource(R.drawable.profile_button_border);
            viewHolder.status.setTextColor(Color.WHITE);
            viewHolder.status.setText(activity.getResources().getString(R.string.label_following));
        }
        else
        {
            viewHolder.status.setBackgroundResource(R.drawable.profile_follow_border);
            viewHolder.status.setTextColor(Color.BLACK);
            viewHolder.status.setText(activity.getResources().getString(R.string.label_follow));
        }
        viewHolder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, OtherProfileActivity.class));
            }
        });
        viewHolder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView temp = (TextView)v;
                if(temp.getText().equals("Follow"))
                {
                    temp.setBackgroundResource(R.drawable.profile_button_border);
                    temp.setTextColor(Color.WHITE);
                    temp.setText("Following");
                }
                else
                {
                    temp.setBackgroundResource(R.drawable.profile_follow_border);
                    temp.setTextColor(Color.BLACK);
                    temp.setText("Follow");
                }
            }
        });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CircleImageView photo;
        public TextView status;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView.findViewById(R.id.comment_name);
            status = (TextView) itemLayoutView.findViewById(R.id.status);
            photo = (CircleImageView)itemLayoutView.findViewById(R.id.photo);
        }
    }
    @Override
    public int getItemCount() {
        return 10;
    }
}