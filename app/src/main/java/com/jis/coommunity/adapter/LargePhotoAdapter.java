package com.jis.coommunity.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.jis.coommunity.R;



public class LargePhotoAdapter extends PagerAdapter {

    private Activity activity;
    private int length;
    private int index;
    private int photoId[] = {R.drawable.large_gallery};
    private static LayoutInflater inflater=null;

    public LargePhotoAdapter(Activity a) {
        activity = a;
        this.length = 5;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(ViewGroup parent,int position,Object obj)
    {
        ViewPager vp = (ViewPager)parent;
        View vi = (View)obj;
        vp.removeView(vi);
    }
    @Override
    public Object instantiateItem(ViewGroup parent,int position)
    {
        View vi = inflater.inflate(R.layout.element_large_gallery, null);
        vi.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onDoubleClick() {
                activity.onBackPressed();
            }
        });
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.g_photo); // thumb image
        thumb_image.setImageResource(this.photoId[0]);
        thumb_image.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewPager vp = (ViewPager)parent;
        vp.addView(vi,0);
        return vi;
    }
    public abstract class DoubleClickListener implements View.OnClickListener {

        // The time in which the second tap should be done in order to qualify as
        // a double click
        private static final long DEFAULT_QUALIFICATION_SPAN = 200;
        private long doubleClickQualificationSpanInMillis;
        private long timestampLastClick;

        public DoubleClickListener() {
            doubleClickQualificationSpanInMillis = DEFAULT_QUALIFICATION_SPAN;
            timestampLastClick = 0;
        }

        public DoubleClickListener(long doubleClickQualificationSpanInMillis) {
            this.doubleClickQualificationSpanInMillis = doubleClickQualificationSpanInMillis;
            timestampLastClick = 0;
        }

        @Override
        public void onClick(View v) {
            if((SystemClock.elapsedRealtime() - timestampLastClick) < doubleClickQualificationSpanInMillis) {
                onDoubleClick();
            }
            timestampLastClick = SystemClock.elapsedRealtime();
        }

        public abstract void onDoubleClick();

    }
}