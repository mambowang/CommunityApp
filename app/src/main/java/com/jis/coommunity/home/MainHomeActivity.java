package com.jis.coommunity.home;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.jis.coommunity.R;
import com.jis.coommunity.adapter.EventAdapter;
import com.jis.coommunity.adapter.FAQAdapter;
import com.jis.coommunity.adapter.ForumAdapter;
import com.jis.coommunity.adapter.PhotoAdapter;
import com.jis.coommunity.adapter.RouteAdapter;
import com.jis.coommunity.adapter.SpotAdapter;
import com.jis.coommunity.addview.NewEventActivity;
import com.jis.coommunity.addview.NewForumActivity;
import com.jis.coommunity.addview.NewRouteActivity;
import com.jis.coommunity.addview.NewSpotActivity;
import com.jis.coommunity.components.TabButton;
import com.jis.coommunity.detail.CommentsActivity;
import com.jis.coommunity.user.LoginActivity;

import java.util.ArrayList;
import java.util.Random;


public class MainHomeActivity extends AppCompatActivity  {
    private static MainHomeActivity _activity=null;
    private TabHost mTabHost;
    private TabButton forum_icon;
    private TabButton spot_icon;
    private TabButton event_icon;
    private TabButton route_icon;
    private TabHost.TabSpec tab1;
    private TabHost.TabSpec tab2;
    private TabHost.TabSpec tab3;
    private TabHost.TabSpec tab4;

    private ViewPager user1_gallery;
    private ViewPager user2_gallery;
    private ViewPager spot_gallery;
    private ViewPager event_gallery;
    private ViewPager route_gallery;

    private ImageView plus_forum_btn;
    private RecyclerView forum_list;
    private RecyclerView spot_list;
    private RecyclerView event_list;
    private RecyclerView route_list;

    private ImageView view_mode_icon;
    public static int tab_selected=0;
    private GoogleMap gmap;
    private ClusterManager<Point> mClusterManager;
    private ArrayList<LatLng> arrayPoints;
    private PolylineOptions polylineOptions;
    private Polyline route_line=null;
    private Random mRandom = new Random(1984);
    private static  int value=0;
    private static String pop_menu_follow="",pop_menu_share="",pop_menu_report="";
    public static Handler UIupdater = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int numOfBytesReceived = msg.arg1;
            String buffer = (String)msg.obj;
            show_pop();
        }
    };
    public static MainHomeActivity sharedActivity(){
        return _activity;
    }
    public static void show_pop()
    {
        ActionSheet.createBuilder(_activity, _activity.getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(pop_menu_follow, pop_menu_share, pop_menu_report)
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        Toast.makeText(_activity, "click item index = " + index,
                                (int)100).show();
                    }
                }).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        pop_menu_follow=getResources().getString(R.string.label_follow);
        pop_menu_share=getResources().getString(R.string.label_share);
        pop_menu_report=getResources().getString(R.string.label_report);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = _activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.statusbar_color));
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////forum list create
        forum_list = (RecyclerView)findViewById(R.id.forum_list);
        ForumAdapter forum_adapter = new ForumAdapter(this);
        forum_list.setAdapter(forum_adapter);
        forum_list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration forum_divider = new DividerItemDecoration(forum_list.getContext(), LinearLayoutManager.VERTICAL);
        forum_list.addItemDecoration(forum_divider);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////spot list create
        spot_list = (RecyclerView)findViewById(R.id.spot_list);
        SpotAdapter spot_adapter = new SpotAdapter(this,2);
        spot_list.setAdapter(spot_adapter);
        spot_list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration spot_divider = new DividerItemDecoration(spot_list.getContext(), LinearLayoutManager.VERTICAL);
        spot_list.addItemDecoration(spot_divider);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////event list create
        event_list = (RecyclerView)findViewById(R.id.event_list);
        EventAdapter event_adapter = new EventAdapter(this,2);
        event_list.setAdapter(event_adapter);
        event_list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration event_divider = new DividerItemDecoration(event_list.getContext(), LinearLayoutManager.VERTICAL);
        event_list.addItemDecoration(event_divider);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////event list create
        route_list = (RecyclerView)findViewById(R.id.route_list);
        RouteAdapter route_adapter = new RouteAdapter(this,2);
        route_list.setAdapter(route_adapter);
        route_list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration route_divider = new DividerItemDecoration(route_list.getContext(), LinearLayoutManager.VERTICAL);
        route_list.addItemDecoration(route_divider);

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mTabHost = (TabHost)findViewById(R.id.home_tab);
        mTabHost.setup();
        forum_icon = new TabButton(this,null);
        forum_icon.setBackColor(getResources().getColor(R.color.home_tabback_color));
        forum_icon.setTint(getResources().getColor(R.color.home_tabtint_color));
        forum_icon.setTitleColor(getResources().getColor(R.color.selecttabtext_color));
        forum_icon.setUnderLineVisible(false);
        forum_icon.setTitleHeight((int)(38*metrics.density));
        forum_icon.setTitle("Forums");
        tab1 = mTabHost.newTabSpec("forums").setContent(R.id.tab1).setIndicator(forum_icon);

        spot_icon = new TabButton(this,null);
        spot_icon.setBackColor(getResources().getColor(R.color.home_tabback_color));
        spot_icon.setTint(getResources().getColor(R.color.home_tabback_color));
        spot_icon.setTitleColor(getResources().getColor(R.color.home_normaltabtext_color));
        spot_icon.setUnderLineVisible(false);
        spot_icon.setTitle("Spots");
        spot_icon.setTitleHeight((int)(38*metrics.density));
        tab2 = mTabHost.newTabSpec("spots").setContent(R.id.tab2).setIndicator(spot_icon);

        event_icon = new TabButton(this,null);
        event_icon.setBackColor(getResources().getColor(R.color.home_tabback_color));
        event_icon.setTint(getResources().getColor(R.color.home_tabback_color));
        event_icon.setTitleColor(getResources().getColor(R.color.home_normaltabtext_color));
        event_icon.setUnderLineVisible(false);
        event_icon.setTitle("Events");
        event_icon.setTitleHeight((int)(38*metrics.density));
        tab3 = mTabHost.newTabSpec("events").setContent(R.id.tab3).setIndicator(event_icon);

        route_icon = new TabButton(this,null);
        route_icon.setBackColor(getResources().getColor(R.color.home_tabback_color));
        route_icon.setTint(getResources().getColor(R.color.home_tabback_color));
        route_icon.setTitleColor(getResources().getColor(R.color.home_normaltabtext_color));
        route_icon.setUnderLineVisible(false);
        route_icon.setTitle("Routes");
        route_icon.setTitleHeight((int)(38*metrics.density));
        tab4 = mTabHost.newTabSpec("routes").setContent(R.id.tab4).setIndicator(route_icon);
        mTabHost.addTab(tab1);
        mTabHost.addTab(tab2);
        mTabHost.addTab(tab3);
        mTabHost.addTab(tab4);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                format();
                switch (tabId)
                {
                    case "forums": forum_icon.setTint(getResources().getColor(R.color.home_tabtint_color));forum_icon.setTitleColor(getResources().getColor(R.color.selecttabtext_color));
                       tab_selected=0;view_mode_icon.setVisibility(View.INVISIBLE);break;
                    case "spots": spot_icon.setTint(getResources().getColor(R.color.home_tabtint_color));spot_icon.setTitleColor(getResources().getColor(R.color.selecttabtext_color));
                       tab_selected=1;view_mode_icon.setVisibility(View.VISIBLE);break;
                    case "events": event_icon.setTint(getResources().getColor(R.color.home_tabtint_color));event_icon.setTitleColor(getResources().getColor(R.color.selecttabtext_color));
                       tab_selected=2;view_mode_icon.setVisibility(View.VISIBLE);break;
                    case "routes":route_icon.setTint(getResources().getColor(R.color.home_tabtint_color));route_icon.setTitleColor(getResources().getColor(R.color.selecttabtext_color));
                        tab_selected=3;view_mode_icon.setVisibility(View.VISIBLE);break;
                }
            }});

        plus_forum_btn = (ImageView)findViewById(R.id.btn_plus);
        plus_forum_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(_activity, NewForumActivity.class));
            }
        });

        view_mode_icon = (ImageView)findViewById(R.id.viewMode_icon);
        view_mode_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tab_selected)
                {
                    case 1:if(spot_list.getVisibility()==View.INVISIBLE)
                    {
                        spot_list.setVisibility(View.VISIBLE);
                        view_mode_icon.setImageResource(R.drawable.mapview_icon);
                    }
                    else
                    {
                        spot_list.setVisibility(View.INVISIBLE);
                        view_mode_icon.setImageResource(R.drawable.list_icon);
                    }break;
                    case 2:if(event_list.getVisibility()==View.INVISIBLE)
                    {
                        event_list.setVisibility(View.VISIBLE);
                        view_mode_icon.setImageResource(R.drawable.mapview_icon);
                    }
                    else
                    {
                        event_list.setVisibility(View.INVISIBLE);
                        view_mode_icon.setImageResource(R.drawable.list_icon);
                    }break;
                    case 3:if(route_list.getVisibility()==View.INVISIBLE)
                    {
                        route_list.setVisibility(View.VISIBLE);
                        view_mode_icon.setImageResource(R.drawable.mapview_icon);
                    }
                    else
                    {
                        route_list.setVisibility(View.INVISIBLE);
                        view_mode_icon.setImageResource(R.drawable.list_icon);
                    }break;
                    default:break;
                }
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment spot_map_large = (MapFragment)fragmentManager.findFragmentById(R.id.spot_large_map);
        spot_map_large.getMapAsync(new spot_LargeMap());

        MapFragment event_map_large = (MapFragment)fragmentManager.findFragmentById(R.id.event_large_map);
        event_map_large.getMapAsync(new event_LargeMap());

        MapFragment route_map_large = (MapFragment)fragmentManager.findFragmentById(R.id.route_large_map);
        route_map_large.getMapAsync(new route_LargeMap());
        mTabHost.setCurrentTab(tab_selected);

    }
    private void format()
    {
        forum_icon.setTint(getResources().getColor(R.color.home_tabback_color));
        spot_icon.setTint(getResources().getColor(R.color.home_tabback_color));
        event_icon.setTint(getResources().getColor(R.color.home_tabback_color));
        route_icon.setTint(getResources().getColor(R.color.home_tabback_color));
        forum_icon.setTitleColor(getResources().getColor(R.color.home_normaltabtext_color));
        spot_icon.setTitleColor(getResources().getColor(R.color.home_normaltabtext_color));
        event_icon.setTitleColor(getResources().getColor(R.color.home_normaltabtext_color));
        route_icon.setTitleColor(getResources().getColor(R.color.home_normaltabtext_color));

       view_mode_icon.setImageResource(R.drawable.list_icon);
        route_list.setVisibility(View.INVISIBLE);
       spot_list.setVisibility(View.INVISIBLE);
        event_list.setVisibility(View.INVISIBLE);
    }
    public void onNewSpotActivity(View view) { startActivity(new Intent(this, NewSpotActivity.class));}
    public void onCurSpotActivity(View view) { startActivity(new Intent(this, SpotPostActivity.class));}

    public void onNewEventActivity(View view) { startActivity(new Intent(this, NewEventActivity.class));}
    public void onCurrentEventActivity(View view) { startActivity(new Intent(this, EventPostActivity.class));}

    public void onNewRouteActivity(View view) { startActivity(new Intent(this, NewRouteActivity.class));}
    public void onCurrentRouteActivity(View view) {startActivity(new Intent(this, RoutePostActivity.class));}

    public void onLoginActivity(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void onSearchActivity(View view) { startActivity(new Intent(this,SearchActivity.class));}
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage(getResources().getString(R.string.string_question));
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.button_yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                       //Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        //homeIntent.addCategory(Intent.CATEGORY_HOME);
                        //homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        //startActivity(homeIntent);

                    }
                });
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.button_no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener()
        {
            @Override public void onShow(DialogInterface arg0)
            {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
            }
        });
        alertDialog.show();
    }
    private LatLng position() {
        LatLng temp = new LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683));
        arrayPoints.add(temp);
        return temp;
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }
    private void addItems() {
        Random rnd = new Random();
        arrayPoints = new ArrayList<LatLng>();
        for(int i=0;i<6;i++) {
            mClusterManager.addItem(new Point(position(), Color.argb(255, rnd.nextInt(256), rnd.nextInt(180), rnd.nextInt(180)),String.valueOf(i+1)));
        }
    }
    private class spot_LargeMap implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gmap = googleMap;
            //gmap.getUiSettings().setZoomControlsEnabled(true);
            mClusterManager = new ClusterManager<Point>(_activity, gmap);
            gmap.setOnCameraChangeListener(mClusterManager);
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));
            mClusterManager.setRenderer(new PointRenderer());
            addItems();
            mClusterManager.cluster();
            gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    startActivity(new Intent(_activity, SpotPostActivity.class));
                    return true;
                }
            });
        }

    }
    private class event_LargeMap implements OnMapReadyCallback {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            //googleMap.getUiSettings().setZoomControlsEnabled(true);
            gmap = googleMap;
            mClusterManager = new ClusterManager<Point>(_activity, gmap);
            gmap.setOnCameraChangeListener(mClusterManager);
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));
            mClusterManager.setRenderer(new PointRenderer());
            addItems();
            mClusterManager.cluster();
            gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    startActivity(new Intent(_activity, EventPostActivity.class));
                    return true;
                }
            });
        }
    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
    private class onCamera extends ClusterManager<Point>
    {
        public onCamera(Context context, GoogleMap map) {
            super(context, map);
        }
    }
    private class route_LargeMap implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //googleMap.getUiSettings().setZoomControlsEnabled(true);
            gmap = googleMap;
            mClusterManager = new ClusterManager<Point>(_activity, gmap);
            gmap.setOnCameraChangeListener(mClusterManager);
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));
            mClusterManager.setRenderer(new PointRenderer());
            addItems();
            mClusterManager.cluster();
            polylineOptions = new PolylineOptions();
            polylineOptions.color(getResources().getColor(R.color.home_normaltabtext_color));
            polylineOptions.width(15);
            polylineOptions.addAll(arrayPoints);
            gmap.addPolyline(polylineOptions);

            gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
            {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    startActivity(new Intent(_activity, RoutePostActivity.class));
                    return true;
                }
            });
        }
    }
    public class Point implements ClusterItem {
        private final LatLng mPosition;
        private final String point_text;
        private final GradientDrawable backShapeDrawable;
        @SuppressLint("WrongConstant")
        public Point(LatLng position, int color, String text) {
            mPosition = position;
            backShapeDrawable = new GradientDrawable();
            backShapeDrawable.setShape(GradientDrawable.RADIAL_GRADIENT);
          //  backShapeDrawable.setCornerRadius(30);
            backShapeDrawable.setStroke(5, getResources().getColor(R.color.map_marker_border_color));
            backShapeDrawable.setColor(color);
            point_text = text;
        }
        @Override
        public LatLng getPosition() {
            return mPosition;
        }
        public String getTitle() {
            return null;
        }
        public String getSnippet() {
            return null;
        }
    }
    private class PointRenderer extends DefaultClusterRenderer<Point> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private final View mItemView;
        private final View mClusterView;
        private final TextView mItemText;
        private final TextView mClusterText;
        private final LinearLayout mItemLayout;
        private final LinearLayout mClusterLayout;
        private final GoogleMap route_map;
        private final Polyline routeline;

        public PointRenderer() {
            super(getApplicationContext(), gmap, mClusterManager);
            route_map= gmap;
            routeline = route_line;
            mClusterView =getLayoutInflater().inflate(R.layout.element_googlemap_marker, null);
            mClusterIconGenerator.setContentView(mClusterView);
            mClusterLayout = (LinearLayout)mClusterView.findViewById(R.id.marker);
            mClusterText = (TextView)mClusterView.findViewById(R.id.value);

            mItemView = getLayoutInflater().inflate(R.layout.element_googlemap_marker, null);
            mItemLayout = (LinearLayout)mItemView.findViewById(R.id.marker);
            mIconGenerator.setContentView(mItemView);
            mItemText = (TextView)mItemView.findViewById(R.id.value);
        }
        @Override
        public void onBeforeClusterItemRendered(Point point, MarkerOptions markerOptions) {
            mIconGenerator.setBackground(null);
            mItemLayout.setBackgroundDrawable(point.backShapeDrawable);
            mItemText.setText(point.point_text);
            Bitmap icon = mIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }
        @Override
        public void onBeforeClusterRendered(Cluster<Point> cluster, MarkerOptions markerOptions) {
            arrayPoints = new ArrayList<LatLng>();
            polylineOptions = new PolylineOptions();
            for (Point p : cluster.getItems()) {
                mClusterLayout.setBackgroundDrawable(p.backShapeDrawable);
                mClusterText.setText(p.point_text);
                arrayPoints.add(p.getPosition());
            }
            polylineOptions.addAll(arrayPoints);
            if(routeline!=null) routeline.remove();
            // route_map.addPolyline(polylineOptions);
            mClusterIconGenerator.setBackground(null);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
            //Toast.makeText(getApplicationContext(),String.valueOf(value),(int)500).show();
            value++;
        }
        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            return true;
        }

    }
}
