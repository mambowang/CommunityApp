package com.jis.coommunity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.jis.coommunity.R;
import com.jis.coommunity.detail.ChangePasswordActivity;
import com.jis.coommunity.detail.FAQActivity;
import com.jis.coommunity.detail.PrivacyActivity;
import com.jis.coommunity.detail.TermsActivity;


public class SettingActivity extends AppCompatActivity {
    private ScrollView main_scroll;
    private InputMethodManager imm;
    private EditText bio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        bio = (EditText)findViewById(R.id.bio_edit);
        main_scroll = (ScrollView)findViewById(R.id.main_scroll);
        main_scroll.setOnTouchListener(new View.OnTouchListener() {
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
        imm.hideSoftInputFromWindow(bio.getWindowToken(), 0);
        bio.clearFocus();
    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }
    public void onChangePasswordActivity(View view){startActivity(new Intent(this,ChangePasswordActivity.class));}
    public void onFAQActivity(View view){startActivity(new Intent(this,FAQActivity.class));}
    public void onLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("logout","true");
        this.startActivity(intent);
    }
    public void onTermsActivity(View view) {
        startActivity(new Intent(this, TermsActivity.class));
    }
    public void onPrivacyActivity(View view) {
        startActivity(new Intent(this, PrivacyActivity.class));
    }
}
