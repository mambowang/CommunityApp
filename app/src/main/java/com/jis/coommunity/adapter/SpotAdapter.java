package com.jis.coommunity.adapter;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.jis.coommunity.R;
import com.jis.coommunity.home.MainHomeActivity;


public class SpotAdapter extends RecyclerView.Adapter<SpotAdapter.ViewHolder> {
    private  Activity _activity;
    private int length;
    public SpotAdapter(Activity a,int len) {
        _activity = a;
        length = len;
    }
    @Override
    public SpotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comp_spot, null);
        itemLayoutView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.google_map.onCreate(_activity.getIntent().getExtras());
        viewHolder.google_map.getMapAsync(new ViewHolder.googleMap());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       public MapView google_map;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
           google_map = (MapView)itemLayoutView.findViewById(R.id.mapView);
        }
        private static class googleMap implements OnMapReadyCallback {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.getUiSettings().setZoomGesturesEnabled(false);
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));
                LatLng pos = new LatLng(51.503186, -0.126446);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(pos);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            }
        }
    }
    @Override
    public int getItemCount() {
        return length;
    }
}