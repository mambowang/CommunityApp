package com.jis.coommunity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jis.coommunity.R;
import com.jis.coommunity.adapter.FollowerAdapter;

public class FollowerActivity extends AppCompatActivity {
    private TabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if(!title.equals("")) {
            TextView text_title = (TextView) findViewById(R.id.title);
            text_title.setText(title);
        }
        RecyclerView follower_list = (RecyclerView)findViewById(R.id.followers_listview);
        FollowerAdapter adapter=new FollowerAdapter(this);
        follower_list.setAdapter(adapter);

    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }
}
