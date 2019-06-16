package com.jis.coommunity.adapter;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jis.coommunity.R;
import com.jis.coommunity.components.PostGallery;




public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
    private Activity _activity;
    private int mCurrentPosition;
    private int mScrollState;

    public ForumAdapter(Activity a) {
        _activity = a;
    }
    @Override
    public ForumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comp_forum, null);
        itemLayoutView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {



    }
    private void setNextItemIfNeeded(ViewPager temp) {
        if (!isScrollStateSettling()) {
            handleSetNextItem(temp);
        }
    }
    private boolean isScrollStateSettling() {
        return mScrollState == ViewPager.SCROLL_STATE_SETTLING; //indicated page is settling to it's final position
    }
    private void handleSetNextItem(ViewPager pager) {
       // Toast.makeText(_activity,"end",0).show();
        final int lastPosition = pager.getAdapter().getCount() - 1;
        if (mCurrentPosition == 0) {
            pager.setCurrentItem(lastPosition,false);
          //  Toast.makeText(_activity,"first",0).show();
        } else if (mCurrentPosition == lastPosition) {
            pager.setCurrentItem(0,false);
            //Toast.makeText(_activity,"last",0).show();
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public PostGallery gallery;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            gallery = (PostGallery) itemLayoutView.findViewById(R.id.gallery);
        }
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}