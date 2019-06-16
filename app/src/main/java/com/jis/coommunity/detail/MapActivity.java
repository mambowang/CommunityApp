package com.jis.coommunity.detail;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.jis.coommunity.R;
import com.jis.coommunity.components.MapTabIcon;




public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TabHost mTabHost;
    private MapTabIcon post_icon;
    private MapTabIcon fav_icon;
    private MapTabIcon been_icon;
    private MapTabIcon want_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymap);
        mTabHost = (TabHost)findViewById(R.id.home_tab);
        mTabHost.setup();

        post_icon = new MapTabIcon(this,null);
        post_icon.setIcon(R.drawable.post_icon_on);
        post_icon.setTitle(getResources().getString(R.string.button_post));
        TabHost.TabSpec tab1 = mTabHost.newTabSpec("post").setContent(R.id.tab1).setIndicator(post_icon);

        fav_icon = new MapTabIcon(this,null);
        fav_icon.setIcon(R.drawable.view_fav_off);
        fav_icon.setTitle(getResources().getString(R.string.btn_favorite_label));
        fav_icon.setTint(getResources().getColor(R.color.largemap_tabback_color));
        TabHost.TabSpec tab2 = mTabHost.newTabSpec("fav").setContent(R.id.tab2).setIndicator(fav_icon);

        been_icon = new MapTabIcon(this,null);
        been_icon.setIcon(R.drawable.view_hbeen_off);
        been_icon.setTitle(getResources().getString(R.string.btn_hbeen_label));
        been_icon.setTint(getResources().getColor(R.color.largemap_tabback_color));
        TabHost.TabSpec tab3 = mTabHost.newTabSpec("been").setContent(R.id.tab3).setIndicator(been_icon);

        want_icon = new MapTabIcon(this,null);
        want_icon.setIcon(R.drawable.view_want_off);
        want_icon.setTitle(getResources().getString(R.string.btn_want_label));
        want_icon.setTint(getResources().getColor(R.color.largemap_tabback_color));
        TabHost.TabSpec tab4 = mTabHost.newTabSpec("want").setContent(R.id.tab4).setIndicator(want_icon);

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
                    case "post":post_icon.setIcon(R.drawable.post_icon_on);post_icon.setTint(getResources().getColor(R.color.selecttabtext_color));break;
                    case "fav":fav_icon.setIcon(R.drawable.view_fav_on);fav_icon.setTint(getResources().getColor(R.color.selecttabtext_color));break;
                    case "been":been_icon.setIcon(R.drawable.view_hbeen_on);been_icon.setTint(getResources().getColor(R.color.selecttabtext_color));break;
                    case "want":want_icon.setIcon(R.drawable.view_want_on);want_icon.setTint(getResources().getColor(R.color.selecttabtext_color));break;
                }
            }});

        FragmentManager fragmentManager_large = getFragmentManager();
        MapFragment tab1_large = (MapFragment)fragmentManager_large.findFragmentById(R.id.large_map1);
        tab1_large.getMapAsync(this);

        MapFragment tab2_large = (MapFragment)fragmentManager_large.findFragmentById(R.id.large_map2);
        tab2_large.getMapAsync(this);

        MapFragment tab3_large = (MapFragment)fragmentManager_large.findFragmentById(R.id.large_map3);
        tab3_large.getMapAsync(this);

        MapFragment tab4_large = (MapFragment)fragmentManager_large.findFragmentById(R.id.large_map4);
        tab4_large.getMapAsync(this);
    }
    private void format()
    {
        post_icon.setIcon(R.drawable.post_icon_off);
        post_icon.setTint(getResources().getColor(R.color.largemap_tabback_color));
        fav_icon.setIcon(R.drawable.view_fav_off);
        fav_icon.setTint(getResources().getColor(R.color.largemap_tabback_color));
        been_icon.setIcon(R.drawable.view_hbeen_off);
        been_icon.setTint(getResources().getColor(R.color.largemap_tabback_color));
        want_icon.setIcon(R.drawable.view_want_off);
        want_icon.setTint(getResources().getColor(R.color.largemap_tabback_color));
    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
