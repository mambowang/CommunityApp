package com.jis.coommunity.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jis.coommunity.R;


public class MapTabIcon extends LinearLayout {
    private ListView image_list;
    private Context _con;
    private ImageView icon;
    private TextView title;
    private ImageView under_line;
    public MapTabIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _con = context;
        init(context);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_mymaplarge_tabicon,this);
        icon = (ImageView)findViewById(R.id.icon);
        title = (TextView)findViewById(R.id.title);
        under_line = (ImageView)findViewById(R.id.under_line);
    }
    public void setIcon(int id)
    {
        icon.setImageResource(id);
    }
    public void setTitle(String str)
    {
        title.setText(str);
    }
    public void setTint(int line_color)
    {
        under_line.setBackgroundColor(line_color);
    }
}
