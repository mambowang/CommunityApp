package com.jis.coommunity.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jis.coommunity.R;

/**
 * Created by lucky on 2018.04.11.
 */

public class TabButton extends LinearLayout {
    private ListView image_list;
    private Context _con;
    private ImageView tint_line;
    private TextView title;
    private ImageView under_line;
    public TabButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _con = context;
        init(context);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.element_normal_tabbutton,this);
        title = (TextView)findViewById(R.id.title);
        under_line = (ImageView)findViewById(R.id.under_line);
        tint_line = (ImageView)findViewById(R.id.tint_line);
    }
    public void setTitleColor(int color){title.setTextColor(color);}
    public void setTitle(String str)
    {
        title.setText(str);
    }
    public void setBackColor(int color)
    {
        title.setBackgroundColor(color);
    }
    public void setTitleHeight(int height)
    {
        title.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,height));
    }
    public void setTint(int str_color)
    {
        tint_line.setBackgroundColor(str_color);
        title.setTextColor(str_color);
    }
    public void setUnderLine(int line_color)
    {
        under_line.setBackgroundColor(line_color);
    }
    public void setUnderLineVisible(boolean bool)
    {
        if(bool)under_line.setVisibility(VISIBLE);
        else {under_line.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,0));}
    }
}