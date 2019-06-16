package com.jis.coommunity.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.jis.coommunity.R;
import com.jis.coommunity.adapter.FAQAdapter;


public class FAQActivity extends AppCompatActivity {
    private RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        list = (RecyclerView) findViewById(R.id.faq_list);
        FAQAdapter adapter = new FAQAdapter();
        list.setAdapter(adapter);
        list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), LinearLayoutManager.VERTICAL);
        list.addItemDecoration(dividerItemDecoration);
    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }
}
