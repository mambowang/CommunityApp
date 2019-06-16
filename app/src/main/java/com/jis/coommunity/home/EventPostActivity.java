package com.jis.coommunity.home;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jis.coommunity.R;
import com.jis.coommunity.adapter.EventAdapter;
import com.jis.coommunity.adapter.PhotoAdapter;
import com.jis.coommunity.detail.CommentsActivity;


public class EventPostActivity extends AppCompatActivity {
    private static EventPostActivity _activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_post);
        RecyclerView event = (RecyclerView)findViewById(R.id.event_recycler);
        EventAdapter event_adapter = new EventAdapter(this,1);
        event.setAdapter(event_adapter);
        event.setItemAnimator(new DefaultItemAnimator());
    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }
}
