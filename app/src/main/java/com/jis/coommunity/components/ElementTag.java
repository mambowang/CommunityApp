package com.jis.coommunity.components;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jis.coommunity.R;
import com.jis.coommunity.detail.RemoveClickListener;
import com.jis.coommunity.home.MainHomeActivity;


public class ElementTag extends LinearLayout {
    private Context _con;
    private int tag_index=0;
    private  TextView content;
    private  LinearLayout main_panel;
    private RemoveClickListener mListner;
    public ElementTag(Context context, @Nullable AttributeSet attrs) {
        super(context, null);
        _con = context;
        mListner = (RemoveClickListener)context;
        init(context);
    }
    public void setString(int index,String str)
    {
        tag_index = index;
        content.setText(str);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.element_tag,this);
        content = (TextView)findViewById(R.id.content);
        ImageView btn_del = (ImageView)findViewById(R.id.btn_delete);
        main_panel = (LinearLayout)findViewById(R.id.main_panel);
        btn_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.OnRemoveClick(tag_index);
            }
        });
    }
    public void setbackground(int color)
    {
        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setShape(GradientDrawable.RECTANGLE);
        shapeDrawable.setCornerRadius(50);
        shapeDrawable.setStroke(0, Color.BLACK);
        shapeDrawable.setColor(color);
        main_panel.setBackgroundDrawable(shapeDrawable);
    }
    public void setContentColor(int color)
    {
        content.setTextColor(color);
    }
}
