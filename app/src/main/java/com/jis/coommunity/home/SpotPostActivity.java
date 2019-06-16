package com.jis.coommunity.home;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jis.coommunity.R;
import com.jis.coommunity.adapter.PhotoAdapter;
import com.jis.coommunity.adapter.SpotAdapter;


public class SpotPostActivity extends AppCompatActivity {
    private ViewPager gallery;
    private SpotPostActivity _activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_post);
        RecyclerView spot_list = (RecyclerView)findViewById(R.id.spot_recycler);
        SpotAdapter spot_adapter = new SpotAdapter(this,1);
        spot_list.setAdapter(spot_adapter);
        spot_list.setItemAnimator(new DefaultItemAnimator());

    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }

}
