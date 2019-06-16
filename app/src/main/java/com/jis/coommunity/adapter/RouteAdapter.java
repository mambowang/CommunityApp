package com.jis.coommunity.adapter;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.jis.coommunity.R;
import com.jis.coommunity.detail.CommentsActivity;
import com.jis.coommunity.home.MainHomeActivity;

import java.util.ArrayList;


public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private Activity _activity;
    private int length;
    public RouteAdapter(Activity a,int len)
    {
        _activity = a;
        length = len;
    }
    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comp_route, null);
        itemLayoutView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.google_map.onCreate(_activity.getIntent().getExtras());
        viewHolder.google_map.getMapAsync(new ViewHolder.googleMap());
        viewHolder.view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.startActivity(new Intent(_activity, CommentsActivity.class));
            }
        });
        String strFront = "Nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";
        String strChange = "<font color=\""+_activity.getResources().getColor(R.color.more_color)+"\">"+_activity.getResources().getString(R.string.label_more)+"</font>";
        viewHolder.content.setText(Html.fromHtml(strFront + strChange));
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MapView google_map;
        public TextView view_all;
        public TextView content;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            google_map = (MapView)itemLayoutView.findViewById(R.id.mapView);
            view_all = (TextView)itemLayoutView.findViewById(R.id.view_all);
            content = (TextView)itemLayoutView.findViewById(R.id.content);
        }
        private static class googleMap implements OnMapReadyCallback {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.getUiSettings().setZoomGesturesEnabled(false);
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                ArrayList<LatLng> arrayPoints = new ArrayList<LatLng>();
                LatLng POS1 = new LatLng(54.16, 43.97);
                MarkerOptions point1 = new MarkerOptions();
                point1.position(POS1);
                googleMap.addMarker(point1);
                arrayPoints.add(POS1);

                LatLng POS2 = new LatLng(50.56, 36.97);
                MarkerOptions point2 = new MarkerOptions();
                point2.position(POS2);
                googleMap.addMarker(point2);
                arrayPoints.add(POS2);

                LatLng POS3 = new LatLng(45.96, 31.97);
                MarkerOptions point3 = new MarkerOptions();
                point3.position(POS3);
                googleMap.addMarker(point3);
                arrayPoints.add(POS3);

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.parseColor("#00d3f1"));
                polylineOptions.width(15);
                polylineOptions.addAll(arrayPoints);
                googleMap.addPolyline(polylineOptions);

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(POS2));
            }
        }
    }
    @Override
    public int getItemCount() {
        return length;
    }
}