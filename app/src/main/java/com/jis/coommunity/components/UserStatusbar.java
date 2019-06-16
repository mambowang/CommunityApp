package com.jis.coommunity.components;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.jis.coommunity.MainActivity;
import com.jis.coommunity.R;
import com.jis.coommunity.home.MainHomeActivity;
import com.jis.coommunity.user.OtherProfileActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class UserStatusbar extends LinearLayout {
    private Context _con;
    public UserStatusbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _con = context;
        init(context);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.element_home_user,this);

        CircleImageView photo = (CircleImageView) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_con, OtherProfileActivity.class);
                intent.putExtra("login_status","true");
                _con.startActivity(intent);
            }
        });
        ImageView popup_icon = (ImageView)findViewById(R.id.drop_icon);
        popup_icon.setOnClickListener(new View.OnClickListener(){
            private  final  int PICK_CONTACT_REQUEST=1;
            @Override
            public void onClick(View v) {
                MainHomeActivity.UIupdater.obtainMessage().sendToTarget();
            }
        });
    }

}
