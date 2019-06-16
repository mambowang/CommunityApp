package com.jis.coommunity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jis.coommunity.R;
import com.jis.coommunity.components.TabButton;
import com.jis.coommunity.detail.FollowerActivity;
import com.jis.coommunity.detail.MapActivity;
import com.jis.coommunity.home.MainHomeActivity;

public class OtherProfileActivity extends AppCompatActivity{
    private TabHost mTabHost;
    private OtherProfileActivity _activity;
    private TextView btn_follow;
    private ImageView banner;
    private Boolean login_status_flag;
    private TabButton following_icon;
    private TabButton post_icon;
    private TabButton map_icon;
    private TabHost.TabSpec tab1;
    private TabHost.TabSpec tab2;
    private TabHost.TabSpec tab3;
    private ScrollView inner_scroll;
    private LinearLayout header_layout;
    private LinearLayout tabcontent_layout;
    private LinearLayout main_layout;
    private int header_layout_height=0;
    private int tab_content_height=0;
    private int tab_layout_height =0;
    private int start_header_point=0;
    private int start_tab_point=0;
    private  int start_y=0,end_y=0;
    private boolean header_scroll_flag=true;
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity = this;
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_profile_other);
        btn_follow = (TextView)findViewById(R.id.btn_editprofile);
        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( btn_follow.getText().equals(getResources().getString(R.string.label_follow)))
                {
                    btn_follow.setText(getResources().getString(R.string.label_following));
                    btn_follow.setBackgroundResource(R.drawable.profile_button_border);
                    btn_follow.setTextColor(Color.WHITE);
                }
                else {
                    btn_follow.setText(getResources().getString(R.string.label_follow));
                    btn_follow.setBackgroundResource(R.drawable.profile_follow_border);
                    btn_follow.setTextColor(getResources().getColor(R.color.normal_black));
                }
            }
        });
        banner = (ImageView)findViewById(R.id.banner_back);
        mTabHost = (TabHost)findViewById(R.id.profile_tab);
        mTabHost.setup();
        following_icon = new TabButton(this,null);
        following_icon.setBackColor(Color.WHITE);
        following_icon.setUnderLineVisible(true);
        following_icon.setTitleHeight(90);
        following_icon.setTitle(getResources().getString(R.string.label_following));
        following_icon.setTint(getResources().getColor(R.color.selecttabtext_color));
        tab1 = mTabHost.newTabSpec(getResources().getString(R.string.label_following)).setContent(R.id.tab1).setIndicator(following_icon);

        post_icon = new TabButton(this,null);
        post_icon.setBackColor(Color.WHITE);
        post_icon.setUnderLineVisible(true);
        post_icon.setTitleHeight(90);
        post_icon.setTitle(getResources().getString(R.string.label_myposts));
        post_icon.setTint(Color.WHITE);
        post_icon.setTitleColor(getResources().getColor(R.color.normal_black));
        tab2 = mTabHost.newTabSpec("My Posts").setContent(R.id.tab2).setIndicator(post_icon);

        map_icon = new TabButton(this,null);
        map_icon.setBackColor(Color.WHITE);
        map_icon.setUnderLineVisible(true);
        map_icon.setTitleHeight(90);
        map_icon.setTitle(getResources().getString(R.string.label_mymap));
        map_icon.setTint(Color.WHITE);
        map_icon.setTitleColor(getResources().getColor(R.color.normal_black));
        tab3 = mTabHost.newTabSpec("My Map").setContent(R.id.tab3).setIndicator(map_icon);

        mTabHost.addTab(tab1);
        mTabHost.addTab(tab2);
        mTabHost.addTab(tab3);
        //mTabHost.setCurrentTab(1);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                tabStyle_format();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                lp.setMargins(0,0,0,0);
                // Toast.makeText(_activity,"tab_after"+height,(int)200).show();
                tabcontent_layout.setLayoutParams(lp);
                switch(tabId)
                {
                    case "My Map":map_icon.setTint(getResources().getColor(R.color.selecttabtext_color));
                        tabcontent_layout = (LinearLayout)findViewById(R.id.tab_mymap);
                        tabcontent_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        tab_content_height =tabcontent_layout.getMeasuredHeight();
                        break;
                    case "My Posts":post_icon.setTint(getResources().getColor(R.color.selecttabtext_color));
                        tabcontent_layout = (LinearLayout)findViewById(R.id.tab_mypost);
                        tabcontent_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        tab_content_height =tabcontent_layout.getMeasuredHeight();
                        break;
                    case "Following":following_icon.setTint(getResources().getColor(R.color.selecttabtext_color));
                        tabcontent_layout = (LinearLayout)findViewById(R.id.tab_following);
                        tabcontent_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        tab_content_height =tabcontent_layout.getMeasuredHeight();
                        break;
                }
                tab_layout_height = tab_content_height-(main_layout.getHeight()-90)+20;
                tab_layout_height = (tab_layout_height<0)?0:tab_layout_height;
                start_tab_point=0;
            }});
        header_layout = (LinearLayout)findViewById(R.id.header_layout);
        header_layout_height = header_layout.getHeight();
        tabcontent_layout = (LinearLayout)findViewById(R.id.tab_following);
        tabcontent_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        tab_content_height =tabcontent_layout.getMeasuredHeight();
        main_layout = (LinearLayout)findViewById(R.id.main_layout);
       main_layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override public boolean onTouch(View v, MotionEvent event)
            {
                if(header_layout_height==0)
                {
                    header_layout_height = header_layout.getHeight();
                    tab_layout_height = tab_content_height-(main_layout.getHeight()-90)+20;
                    //Toast.makeText(_activity,"tab"+tab_layout_height+":"+post_layout.getHeight()+":"+main_layout.getHeight(),(int)500).show();
                    tab_layout_height = (tab_layout_height<0)?0:tab_layout_height;
                    start_header_point=0;
                    start_tab_point=0;
                }
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN)
                {
                    start_y = (int)event.getY();
                }
                if(event.getActionMasked() == MotionEvent.ACTION_MOVE)
                {
                    end_y = (int)event.getY();
                    if(header_scroll_flag)
                    {
                        int height =start_header_point-(start_y-end_y);
                        if(height>0)height = 0;
                        else if(height<0-header_layout_height) height = 0-header_layout_height;
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        lp.setMargins(0,height,0,0);
                        header_layout.setLayoutParams(lp);
                    }
                    else
                    {
                        int height =start_tab_point-(start_y-end_y);
                        //Toast.makeText(_activity,"tab"+height,(int)200).show();
                        if(height>0)height = 0;
                        else if(height<0-tab_layout_height) height = 0-tab_layout_height;
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        lp.setMargins(0,height,0,0);
                       // Toast.makeText(_activity,"tab_after"+height,(int)200).show();
                        tabcontent_layout.setLayoutParams(lp);
                    }
                }
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    end_y = (int) event.getY();
                    if(header_scroll_flag)
                    {
                        start_header_point = start_header_point - (start_y - end_y);
                        if (start_header_point > 0)start_header_point = 0;
                        else if (start_header_point < 0 - header_layout_height)
                        {
                            start_header_point = 0 - header_layout_height;
                            if(tab_layout_height>0) header_scroll_flag = false;
                        }
                    }
                    else
                    {
                        start_tab_point = start_tab_point - (start_y - end_y);
                        if (start_tab_point > 0)
                        {
                            start_tab_point = 0;
                            header_scroll_flag = true;
                        }
                        else if (start_tab_point < 0 - tab_layout_height) start_tab_point = 0 - tab_layout_height;
                    }
                }
                return  true;
            }});
    }

    private void tabStyle_format()
    {
        following_icon.setTint(Color.WHITE);
        post_icon.setTint(Color.WHITE);
        map_icon.setTint(Color.WHITE);

        following_icon.setTitleColor(getResources().getColor(R.color.normal_black));
        post_icon.setTitleColor(getResources().getColor(R.color.normal_black));
        map_icon.setTitleColor(getResources().getColor(R.color.normal_black));
    }
    public void onFollowersActivity(View view) {
        Intent intent = new Intent(this, FollowerActivity.class);
        intent.putExtra("title",getResources().getString(R.string.title_followers));
        this.startActivity(intent);

    }
    public void onFollowingActivity(View view) {
        Intent intent = new Intent(this, FollowerActivity.class);
        intent.putExtra("title",getResources().getString(R.string.title_following));
        this.startActivity(intent);

    }
    public void onBackActivity(View view) {

        startActivity(new Intent(this, MainHomeActivity.class));
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, MainHomeActivity.class));
    }
    public void onSettingActivity(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }
    public void onLargeMapActivity(View view)
    {
        startActivity(new Intent(this, MapActivity.class));
    }


}
