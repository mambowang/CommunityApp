package com.jis.coommunity.home;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jis.coommunity.R;
import com.jis.coommunity.adapter.PhotoAdapter;
import com.jis.coommunity.adapter.RouteAdapter;
import com.jis.coommunity.detail.CommentsActivity;

import java.util.ArrayList;


public class RoutePostActivity extends AppCompatActivity {
    private static RoutePostActivity _activity=null;
    private ArrayList<LatLng> arrayPoints;
    private PolylineOptions polylineOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_post);
        RecyclerView route_list = (RecyclerView)findViewById(R.id.route_recycler);
        RouteAdapter route_adapter = new RouteAdapter(this,1);
        route_list.setAdapter(route_adapter);
        route_list.setItemAnimator(new DefaultItemAnimator());
    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }

}
