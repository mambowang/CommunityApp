package com.jis.coommunity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jis.coommunity.R;


public class LargeMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_largemap);

    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }
}
