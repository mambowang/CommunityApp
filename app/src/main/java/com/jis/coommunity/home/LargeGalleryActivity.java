package com.jis.coommunity.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.jis.coommunity.R;

import com.jis.coommunity.adapter.LargePhotoAdapter;
import com.jis.coommunity.components.PostGallery;




public class LargeGalleryActivity extends AppCompatActivity {
    private PostGallery gallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_large_gallery);
        gallery = (PostGallery)findViewById(R.id.gallery);
        FrameLayout main_panel = (FrameLayout)gallery.findViewById(R.id.main_panel);
        main_panel.setBackgroundColor(Color.BLACK);
        main_panel.setPadding(0,0,0,50);
        ViewPager image_slide = (ViewPager)gallery.findViewById(R.id.image_slider);
        LargePhotoAdapter  adapter= new LargePhotoAdapter(this);
        gallery.setAdapter(adapter);
        image_slide.setPadding(0,100,0,10);
    }

}
