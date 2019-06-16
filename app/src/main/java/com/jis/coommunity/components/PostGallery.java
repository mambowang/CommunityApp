package com.jis.coommunity.components;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.jis.coommunity.MainActivity;
import com.jis.coommunity.R;
import com.jis.coommunity.adapter.PhotoAdapter;
import com.jis.coommunity.home.MainHomeActivity;
import com.jis.coommunity.user.OtherProfileActivity;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class PostGallery extends LinearLayout {
    private Activity _activity;
    private ImageView[] ivArrayDotsPager;
    private PhotoAdapter gallery_adapter;
    private LinearLayout pager_indicator;
    private ViewPager image_slider;
    private int mCurrentPosition;
    private int mScrollState;
    private int currentPage=0;
    private int photoId[] = {R.drawable.gallery1,R.drawable.gallery_2};
    public PostGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_post_gallery,this);
        image_slider = (ViewPager)findViewById(R.id.image_slider);
        _activity = (Activity)context;
        gallery_adapter = new PhotoAdapter(_activity,photoId);
        image_slider.setAdapter(gallery_adapter);
        pager_indicator = (LinearLayout)findViewById(R.id.indicator_layout);
        setupIndicator();
        image_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.gallery_mark_1);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.gallery_mark_2);
                mCurrentPosition = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    setNextItemIfNeeded();
                }
                mScrollState = state;
            }
        });
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == image_slider.getAdapter().getCount()) {
                            currentPage = 0;
                        }
                        image_slider.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, 500, 5000);
    }
    public void setAdapter(PagerAdapter adapter)
    {
        image_slider.setAdapter(adapter);
        setupIndicator();
    }
    private void setNextItemIfNeeded() {
        if (!isScrollStateSettling()) {
            handleSetNextItem();
        }
    }
    private boolean isScrollStateSettling() {
        return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
    }
    private void handleSetNextItem() {
        final int lastPosition = image_slider.getAdapter().getCount() - 1;
        if (mCurrentPosition == 0) {
            image_slider.setCurrentItem(lastPosition,false);
        } else if (mCurrentPosition == lastPosition) {
            image_slider.setCurrentItem(0,false);
        }
    }
    private void  setupIndicator(){
        ivArrayDotsPager = new ImageView[image_slider.getAdapter().getCount()];
        pager_indicator.removeAllViews();
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(_activity);
            ivArrayDotsPager[i].setId(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
            params.setMargins(10, 0, 10, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.gallery_mark_1);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    ImageView select = (ImageView)view;
                    select.setImageResource(R.drawable.gallery_mark_2);
                    image_slider.setCurrentItem(select.getId(),false);
                }
            });
            pager_indicator.addView(ivArrayDotsPager[i]);
            pager_indicator.bringToFront();
        }
        ivArrayDotsPager[0].setImageResource(R.drawable.gallery_mark_2);
    }
}
