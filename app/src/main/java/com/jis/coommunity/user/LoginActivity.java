package com.jis.coommunity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jis.coommunity.R;
import com.jis.coommunity.home.MainHomeActivity;

public class LoginActivity extends AppCompatActivity {
    private static boolean status=false;
    private  InputMethodManager imm;
    private EditText email;
    private EditText pwd;
    private LinearLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String data = intent.getStringExtra("logout");
            if (!status || data!=null) {
                setContentView(R.layout.activity_user_login);
                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                email = (EditText) findViewById(R.id.email);
                pwd = (EditText) findViewById(R.id.password);
                main_layout = (LinearLayout) findViewById(R.id.login_layout);
                main_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard();
                        switch (v.getId()) {
                            case R.id.login_layout:
                                break;
                        }
                    }
                });
                status = false;
            } else {
                startActivity(new Intent(this, UserProfileActivity.class));
            }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainHomeActivity.class));finish();
    }
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
        email.clearFocus();
        imm.hideSoftInputFromWindow(pwd.getWindowToken(), 0);
        pwd.clearFocus();
    }
    public void onSignupActivity(View view) {
        hideKeyboard();
        startActivity(new Intent(this, SignupActivity.class));
    }
    public void onProfileActivity(View view) {
        hideKeyboard();
        status = true;
        startActivity(new Intent(this, UserProfileActivity.class));
    }
}
