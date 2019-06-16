package com.jis.coommunity.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jis.coommunity.R;


public class ChangePasswordActivity extends AppCompatActivity {
    private LinearLayout main_layout;
    private InputMethodManager imm;
    private EditText cur_pwd;
    private EditText new_pwd;
    private EditText confirm_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        cur_pwd = (EditText)findViewById(R.id.cur_pwd);
        new_pwd = (EditText)findViewById(R.id.cur_pwd);
        confirm_pwd = (EditText)findViewById(R.id.confirm_pwd);
        main_layout = (LinearLayout)findViewById(R.id.main_layout);
        main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
    }
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(cur_pwd.getWindowToken(), 0);
        cur_pwd.clearFocus();
        imm.hideSoftInputFromWindow(new_pwd.getWindowToken(), 0);
        new_pwd.clearFocus();
        imm.hideSoftInputFromWindow(confirm_pwd.getWindowToken(), 0);
        confirm_pwd.clearFocus();
    }
    public void onBackActivity(View view) {
        super.onBackPressed();
    }
}
