package com.jis.coommunity.detail;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jis.coommunity.R;
import com.jis.coommunity.adapter.CommentsAdapter;


public class CommentsActivity extends AppCompatActivity {
    private RecyclerView comments_list;
    private LinearLayout main_layout;
    private EditText edit_c;
    private  InputMethodManager imm;
    private int start_editc_height;
    private  int current_editc_height=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        edit_c = (EditText)findViewById(R.id.edit_comment);
        edit_c.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        start_editc_height = edit_c.getMeasuredHeight();
        edit_c.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    //Toast.makeText(CommentsActivity.this,""+current_editc_height,(int)0).show();
                    edit_c.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    if(current_editc_height!=0){
                    ViewGroup.LayoutParams params = edit_c.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    edit_c.setLayoutParams(params);}
                }
                else
                {
                    edit_c.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    current_editc_height = edit_c.getMeasuredHeight();
                    ViewGroup.LayoutParams params = edit_c.getLayoutParams();
                    params.height = start_editc_height;
                    edit_c.setLayoutParams(params);
                    edit_c.setBackgroundResource(R.drawable.black_rounded_border);
                }
            }
        });
        comments_list = (RecyclerView)findViewById(R.id.comments_list);
        CommentsAdapter adapter=new CommentsAdapter();
        comments_list.setAdapter(adapter);
        comments_list.setItemAnimator(new DefaultItemAnimator());
        comments_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_MOVE)
                {
                    imm.hideSoftInputFromWindow(edit_c.getWindowToken(), 0);
                    edit_c.clearFocus();
                }
                return false;
            }
        });
    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }
}
