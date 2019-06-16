package com.jis.coommunity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jis.coommunity.R;
import com.jis.coommunity.detail.PrivacyActivity;
import com.jis.coommunity.detail.TermsActivity;
import com.jis.coommunity.home.MainHomeActivity;

public class SignupActivity extends AppCompatActivity {
    private  InputMethodManager imm;
    private EditText email;
    private EditText pwd;
    private EditText name;
    private LinearLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        email = (EditText)findViewById(R.id.email);
        pwd = (EditText)findViewById(R.id.password);
        name = (EditText)findViewById(R.id.name);
        main_layout = (LinearLayout)findViewById(R.id.signup_layout);
        main_layout.setOnClickListener(myClickListener);
    }
    View.OnClickListener myClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            hideKeyboard();
            switch (v.getId())
            {
                case R.id.login_layout :
                    break;
            }
        }
    };
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
        email.clearFocus();
        imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
        name.clearFocus();
        imm.hideSoftInputFromWindow(pwd.getWindowToken(), 0);
        pwd.clearFocus();
    }
    public void onLoginActivity(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void onTermsActivity(View view) {
        startActivity(new Intent(this, TermsActivity.class));
    }
    public void onPrivacyActivity(View view) {
        startActivity(new Intent(this, PrivacyActivity.class));
    }

}
