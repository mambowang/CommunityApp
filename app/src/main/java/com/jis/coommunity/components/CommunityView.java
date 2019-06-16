package com.jis.coommunity.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jis.coommunity.R;
import com.jis.coommunity.adapter.SportListAdapter;


public class CommunityView extends LinearLayout implements View.OnClickListener{
    private Boolean[] select_flag={false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
    private TextView select_community;
    private ListView sport_list;
    private SportListAdapter adapter;
    private boolean sportlist_flag=false;
    private ImageView sport_drop_icon;
    private Context con;
    private static int C_AGORA_ID=1,C_ART_ID=2,C_WELCOME_ID=3,C_DESIGN_ID=4,C_FASHION_ID=5,C_FINANCE_ID=6,C_KITCHEN_ID=7,C_LITERATURE_ID=8,C_MUSIC_ID=9,C_NATURE_ID=10,C_HEALTH_ID=11,C_CINEMA_ID=12,
                    C_THEATER_ID=13,C_TRAVELS_ID=14,C_ARCHITECTURE_ID=15,C_SCIENCE_ID=16,C_SPORT_ID=17;
    private int[] community_color = {0,R.color.community_agora,R.color.community_art,R.color.community_welcome,R.color.community_design,R.color.community_fashion,R.color.community_finance,R.color.community_kitchen,
            R.color.community_literature,R.color.community_music,R.color.community_nature,R.color.community_health,R.color.community_cinema,R.color.community_theater,R.color.community_travel,R.color.community_architecture,R.color.community_science,R.color.community_sport};
    public CommunityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        con =  context;
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_communitys,this);
        TextView community_agora = (TextView)findViewById(R.id.t_agora);
        community_agora.setOnClickListener(this);
        community_agora.setId(C_AGORA_ID);

        TextView community_art = (TextView)findViewById(R.id.t_art);
        community_art.setOnClickListener(this);
        community_art.setId(C_ART_ID);

        TextView community_welcome = (TextView)findViewById(R.id.t_welcome);
        community_welcome.setOnClickListener(this);
        community_welcome.setId(C_WELCOME_ID);

        TextView community_design = (TextView)findViewById(R.id.t_design);
        community_design.setOnClickListener(this);
        community_design.setId(C_DESIGN_ID);

        TextView community_fashion = (TextView)findViewById(R.id.t_fashion);
        community_fashion.setOnClickListener(this);
        community_fashion.setId(C_FASHION_ID);

        TextView community_finance = (TextView)findViewById(R.id.t_finance);
        community_finance.setOnClickListener(this);
        community_finance.setId(C_FINANCE_ID);

        TextView community_kitchen = (TextView)findViewById(R.id.t_kitchen);
        community_kitchen.setOnClickListener(this);
        community_kitchen.setId(C_KITCHEN_ID);

        TextView community_literature = (TextView)findViewById(R.id.t_literature);
        community_literature.setOnClickListener(this);
        community_literature.setId(C_LITERATURE_ID);

        TextView community_music = (TextView)findViewById(R.id.t_music);
        community_music.setOnClickListener(this);
        community_music.setId(C_MUSIC_ID);

        TextView community_nature = (TextView)findViewById(R.id.t_nature);
        community_nature.setOnClickListener(this);
        community_nature.setId(C_NATURE_ID);

        TextView community_health = (TextView)findViewById(R.id.t_health);
        community_health.setOnClickListener(this);
        community_health.setId(C_HEALTH_ID);

        TextView community_cinema = (TextView)findViewById(R.id.t_cinema);
        community_cinema.setOnClickListener(this);
        community_cinema.setId(C_CINEMA_ID);

        TextView community_theater = (TextView)findViewById(R.id.t_theater);
        community_theater.setOnClickListener(this);
        community_theater.setId(C_THEATER_ID);

        TextView community_travels = (TextView)findViewById(R.id.t_travels);
        community_travels.setOnClickListener(this);
        community_travels.setId(C_TRAVELS_ID);

        TextView community_architecture = (TextView)findViewById(R.id.t_architecture);
        community_architecture.setOnClickListener(this);
        community_architecture.setId(C_ARCHITECTURE_ID);

        TextView community_science = (TextView)findViewById(R.id.t_science);
        community_science.setOnClickListener(this);
        community_science.setId(C_SCIENCE_ID);

        TextView community_sport = (TextView)findViewById(R.id.t_sport);
        community_sport.setOnClickListener(this);
        community_sport.setId(C_SPORT_ID);

        sport_list = (ListView)findViewById(R.id.sport_list2);
        Activity activity = (Activity) context;
        adapter=new SportListAdapter(activity);
        setListViewHeightBasedOnChildren(sport_list);
        sport_list.setOnItemClickListener(new MyListener(adapter,sport_list));
        sport_drop_icon = (ImageView)findViewById(R.id.sport_drop_icon);
        sport_drop_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sportlist_flag) {
                    sport_list.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(sport_list);
                    sport_drop_icon.setImageResource(R.drawable.drop_up_icon);
                    sportlist_flag = true;
                }
                else
                {
                    sport_list.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0));
                    sport_drop_icon.setImageResource(R.drawable.drop_down_icon);
                    sportlist_flag = false;
                }
            }
        });
    }
    public class MyListener implements AdapterView.OnItemClickListener {
        private ListView list;
        private SportListAdapter list_adapter;
        public MyListener(SportListAdapter m_adapter,ListView view){
            list_adapter = m_adapter;
            list = view;
        }
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            ListView list_view = (ListView) arg0;
            final int pos = arg2;
            list_adapter.check(pos);
            sport_list.setAdapter(list_adapter);
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    private void selectChange(int index,int select_color)
    {
        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setShape(GradientDrawable.RECTANGLE);
        shapeDrawable.setCornerRadius(50);
        shapeDrawable.setStroke(0, Color.BLACK);
        if(index!=17){
            if(!select_flag[index])
            {
                shapeDrawable.setColor(select_color);
                select_community.setBackgroundDrawable(shapeDrawable);
                select_flag[index] = true;
            }
            else {select_community.setBackgroundResource(R.drawable.black_rounded_border);select_community.setTextColor(getResources().getColor(R.color.normal_black));select_flag[index]=false;}}
        else
        {
            FrameLayout temp = (FrameLayout)findViewById(R.id.sport_layout);
            if(!select_flag[index]) {
                shapeDrawable.setColor(select_color);
                temp.setBackgroundDrawable(shapeDrawable);
                select_community.setTextColor(Color.WHITE);
                select_flag[index] = true;
            }
            else
            {
                temp.setBackgroundResource(R.drawable.black_rounded_border);
                select_community.setTextColor(Color.GRAY);
                select_flag[index] = false;
            }
        }
    }

    @Override
    public void onClick(View v) {
        select_community = (TextView)v;
        select_community.setTextColor(Color.WHITE);
        selectChange(select_community.getId(),getResources().getColor(community_color[select_community.getId()]));
    }
}
