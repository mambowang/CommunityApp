package com.jis.coommunity.components;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jis.coommunity.R;
import com.jis.coommunity.detail.CommentsActivity;

public class ExComments extends LinearLayout {
    private Context _con;
    public ExComments(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _con = context;
        init(context);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_ex_comments,this);
        TextView view_all = (TextView)findViewById(R.id.btn_viewallcomment);
        view_all.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                _con.startActivity(new Intent(_con, CommentsActivity.class));
            }
        });
        TextView content = (TextView)findViewById(R.id.content);
        String strFront = "Nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum ";
        String strChange = "<font color=\""+getResources().getColor(R.color.more_color)+"\">"+getResources().getString(R.string.label_more)+"</font>";
        content.setText(Html.fromHtml(strFront + strChange));
    }
}
