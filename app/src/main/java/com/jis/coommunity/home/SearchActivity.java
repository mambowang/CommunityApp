package com.jis.coommunity.home;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jis.coommunity.R;
import com.jis.coommunity.adapter.SportListAdapter;
import com.jis.coommunity.components.CustomSeekbar;
import com.jis.coommunity.components.ElementTag;
import com.jis.coommunity.detail.RemoveClickListener;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity implements RemoveClickListener {
    private EditText search_edit;
    private LinearLayout search_tagLayout;
    private static SearchActivity _activity;
    private static int input_tagnum=0;
    private ArrayList<Integer> myTagList = new ArrayList<>();
    private InputMethodManager imm;
    private int searchTagLayoutMaxWidth;
    private HorizontalScrollView tag_scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        search_edit = (EditText)findViewById(R.id.edittext_search);
        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        tag_scroll = (HorizontalScrollView)findViewById(R.id.tag_scroll);
        searchTagLayoutMaxWidth = (int)metrics.density*180;
        Toast.makeText(this,""+searchTagLayoutMaxWidth,(int)0).show();
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT  || actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    String str = search_edit.getText().toString();
                    if(str.length()!=0) {
                        ElementTag temp = new ElementTag(_activity,null);
                        temp.setString(input_tagnum,str);
                        input_tagnum++;
                        temp.setbackground(Color.WHITE);
                        temp.setContentColor(getResources().getColor(R.color.normal_black));
                        temp.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        temp.setPadding(15,0,15,0);
                        search_tagLayout.addView(temp);
                        tag_scroll.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        if(tag_scroll.getMeasuredWidth()>searchTagLayoutMaxWidth) tag_scroll.setLayoutParams(new LinearLayout.LayoutParams(searchTagLayoutMaxWidth, ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                    search_edit.setText("");
                    return true;
                }
                return false;
            }
        });

        search_tagLayout = (LinearLayout) findViewById(R.id.search_tag_layout);
        CustomSeekbar seekbar = (CustomSeekbar)findViewById(R.id.km_seekBar);
        seekbar.setProgress(50);
        seekbar.setUnit("km");
        seekbar.setLabelPosition(1);
        ScrollView main_panel = (ScrollView) findViewById(R.id.main_panel);
        main_panel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_MOVE)
                {
                    hideKeyboard();
                }
                return false;
            }
        });
    }
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(search_edit.getWindowToken(), 0);search_edit.clearFocus();
    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }

    @Override
    public void OnRemoveClick(int index) {
        int select_index = index;
        myTagList.add(index);
        for(int i=0;i<myTagList.size();i++)
        {
            if(index>myTagList.get(i))select_index--;
        }
        search_tagLayout.removeViewAt(select_index);
        tag_scroll.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        tag_scroll.setLayoutParams(new LinearLayout.LayoutParams((tag_scroll.getMeasuredWidth()>searchTagLayoutMaxWidth)?searchTagLayoutMaxWidth:tag_scroll.getMeasuredWidth(), ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
