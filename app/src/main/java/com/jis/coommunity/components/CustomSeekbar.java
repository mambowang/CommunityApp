package com.jis.coommunity.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jis.coommunity.R;


public class CustomSeekbar extends LinearLayout {
    private Context _con;
    private String unit="";
    private SeekBar _seekbar;
    private TextView label_up;
    private LinearLayout label_up_Layout;
    private TextView label_down;
    private LinearLayout label_down_Layout;
    public CustomSeekbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _con = context;
        init(context);
        setLabelPosition(0);
    }
    public void setUnit(String value)
    {
        unit = value;
        setProgress(_seekbar.getProgress());
    }
    public void setLabelPosition(int pos)
    {
        if(pos==0)
        {
            label_down_Layout.setVisibility(INVISIBLE);
            label_up_Layout.setVisibility(VISIBLE);
        }
        else
        {
            label_down_Layout.setVisibility(VISIBLE);
            label_up_Layout.setVisibility(INVISIBLE);
        }
    }
    public void setProgress(int value)
    {
        _seekbar.setProgress(value);
        label_up.setText(_seekbar.getProgress()+unit);
        label_down.setText(_seekbar.getProgress()+unit);
        label_up.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        label_down.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        label_down_Layout.setPadding(_seekbar.getThumb().getBounds().centerX()-label_down.getMeasuredWidth()/2, 0, 0, 0);
        label_up_Layout.setPadding(_seekbar.getThumb().getBounds().centerX()-label_up.getMeasuredWidth()/2, 0, 0, 0);
    }
    public int getProgress()
    {
        return _seekbar.getProgress();
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_custom_seekbar,this);
        _seekbar = (SeekBar)findViewById(R.id.custom_seekbar);
        label_up = (TextView)findViewById(R.id.custom_up_label);
        label_up.setText(_seekbar.getProgress()+unit);
        label_up_Layout = (LinearLayout)findViewById(R.id.label_up_layout);
        label_down = (TextView)findViewById(R.id.custom_down_label);
        label_down.setText(_seekbar.getProgress()+unit);
        label_down_Layout = (LinearLayout)findViewById(R.id.label_down_layout);
        _seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onStartTrackingTouch(SeekBar bar) {

            }
            public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
                if (!fromuser) {
                    return;
                }
                label_up.setText(progress+unit);
                label_up.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                label_down.setText(progress+unit);
                label_down.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                label_down_Layout.setPadding(_seekbar.getPaddingLeft()+_seekbar.getThumb().getBounds().centerX()-label_down.getMeasuredWidth()/2, 0, 0, 0);
                label_up_Layout.setPadding(_seekbar.getPaddingLeft()+_seekbar.getThumb().getBounds().centerX()-label_up.getMeasuredWidth()/2, 0, 0, 0);
                label_up.setGravity(Gravity.LEFT);
                label_down.setGravity(Gravity.LEFT);
            }
            public void onStopTrackingTouch(SeekBar bar) {

            }
        });
    }

}
