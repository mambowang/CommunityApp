package com.jis.coommunity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import com.jis.coommunity.home.MainHomeActivity;

import java.util.Timer;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainHomeActivity.class);
        startActivity(intent);
        finish();
    }


}
