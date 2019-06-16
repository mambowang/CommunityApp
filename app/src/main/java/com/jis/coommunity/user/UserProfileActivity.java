package com.jis.coommunity.user;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.jis.coommunity.R;
import com.jis.coommunity.addview.NewSpotActivity;
import com.jis.coommunity.components.GetImageActivity;
import com.jis.coommunity.components.TabButton;
import com.jis.coommunity.detail.FollowerActivity;
import com.jis.coommunity.detail.MapActivity;
import com.jis.coommunity.home.MainHomeActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    private TabHost mTabHost;
    private static UserProfileActivity _activity;
    private TextView btn_edit;
    private static ImageView banner;

    private TabButton following_icon;
    private TabButton post_icon;
    private TabButton map_icon;
    private TabHost.TabSpec tab1;
    private TabHost.TabSpec tab2;
    private TabHost.TabSpec tab3;
    private LinearLayout header_layout;
    private ImageView user_photo;
    private ImageView camera_icon;
    private LinearLayout tabcontent_layout;
    private LinearLayout main_layout;
    private int header_layout_height=0;
    private int tab_content_height=0;
    private int tab_layout_height =0;
    private int start_header_point=0;
    private int start_tab_point=0;
    private  int start_y=0,end_y=0;
    private boolean header_scroll_flag=true;
    private static Bitmap []image_change_view = new Bitmap[2];
    private static int select_image;
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    public static UserProfileActivity sharedActivity(){
        return _activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setStatusBarTranslucent(true);
        Intent _intent = getIntent();
        String bitmap_uri = _intent.getStringExtra("image");
        if(bitmap_uri!=null) {
            try {
                image_change_view[ select_image] = MediaStore.Images.Media.getBitmap(this.getContentResolver(),  Uri.parse(bitmap_uri));
                //image_change_view[ select_image]  = rotateImage(  image_change_view[ select_image], 90);
                image_change_view[ select_image]= rotateImageIfRequired(image_change_view[ select_image],  Uri.parse(bitmap_uri));
                image_change_view[ select_image]  = getResizedBitmap(  image_change_view[ select_image] , 500);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////tab setting
        mTabHost = (TabHost)findViewById(R.id.profile_tab);
        mTabHost.setup();

        following_icon = new TabButton(this,null);
        following_icon.setBackColor(Color.WHITE);
        following_icon.setUnderLineVisible(true);
        following_icon.setTitleHeight(90);
        following_icon.setTitle(getResources().getString(R.string.label_following));
        following_icon.setTint(getResources().getColor(R.color.selecttabtext_color));
        tab1 = mTabHost.newTabSpec("Following").setContent(R.id.tab1).setIndicator(following_icon);

        post_icon = new TabButton(this,null);
        post_icon.setBackColor(Color.WHITE);
        post_icon.setUnderLineVisible(true);
        post_icon.setTitleHeight(90);
        post_icon.setTitle(getResources().getString(R.string.label_myposts));
        post_icon.setTint(Color.WHITE);
        post_icon.setTitleColor(getResources().getColor(R.color.normal_black));
        tab2 = mTabHost.newTabSpec("My Posts").setContent(R.id.tab2).setIndicator(post_icon);

        map_icon = new TabButton(this,null);
        map_icon.setBackColor(Color.WHITE);
        map_icon.setUnderLineVisible(true);
        map_icon.setTitleHeight(90);
        map_icon.setTitle(getResources().getString(R.string.label_mymap));
        map_icon.setTint(Color.WHITE);
        map_icon.setTitleColor(getResources().getColor(R.color.normal_black));
        tab3 = mTabHost.newTabSpec("My Map").setContent(R.id.tab3).setIndicator(map_icon);

        mTabHost.addTab(tab1);
        mTabHost.addTab(tab2);
        mTabHost.addTab(tab3);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                tabStyle_format();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                lp.setMargins(0,0,0,0);
                // Toast.makeText(_activity,"tab_after"+height,(int)200).show();
                tabcontent_layout.setLayoutParams(lp);
                switch(tabId)
                {
                    case "My Map":map_icon.setTint(getResources().getColor(R.color.selecttabtext_color));
                        tabcontent_layout = (LinearLayout) findViewById(R.id.tab_mymap);
                        tabcontent_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        tab_content_height =tabcontent_layout.getMeasuredHeight();
                        break;
                    case "My Posts":post_icon.setTint(getResources().getColor(R.color.selecttabtext_color));
                        tabcontent_layout = (LinearLayout) findViewById(R.id.tab_mypost);
                        tabcontent_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        tab_content_height =tabcontent_layout.getMeasuredHeight();
                        break;
                    case "Following":following_icon.setTint(getResources().getColor(R.color.selecttabtext_color));
                        tabcontent_layout = (LinearLayout) findViewById(R.id.tab_following);
                        tabcontent_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                        tab_content_height =tabcontent_layout.getMeasuredHeight();
                        break;

                }
                tab_layout_height = tab_content_height-(main_layout.getHeight()-90)+20;
                tab_layout_height = (tab_layout_height<0)?0:tab_layout_height;
                start_tab_point=0;
            }});
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Camera icon setting
        camera_icon = (ImageView)findViewById(R.id.camera_icon);
        camera_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_image = 1;
                pop_selectImage();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Edit profile button setting
        btn_edit = (TextView)findViewById(R.id.btn_editprofile);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_image = 0;
                pop_selectImage();
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////banner_image
        banner = (ImageView)findViewById(R.id.banner_back);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////user_photo image setting
        user_photo = (ImageView)findViewById(R.id.user_photo);
        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_image = 0;
                pop_selectImage();
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////tab panel setting
        tabcontent_layout = (LinearLayout) findViewById(R.id.tab_following);
        tabcontent_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        tab_content_height =tabcontent_layout.getMeasuredHeight();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////header panel setting
        header_layout = (LinearLayout)findViewById(R.id.header_layout);
        header_layout_height = header_layout.getHeight();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////main :tab & header panel setting
        main_layout = (LinearLayout)findViewById(R.id.main_layout);
        main_layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override public boolean onTouch(View v, MotionEvent event)
            {
                if(header_layout_height==0)
                {
                    header_layout_height = header_layout.getHeight();
                    tab_layout_height = tab_content_height-(main_layout.getHeight()-90)+20;
                    tab_layout_height = (tab_layout_height<0)?0:tab_layout_height;
                    start_header_point=0;
                    start_tab_point=0;
                }
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN)
                {
                    start_y = (int)event.getY();
                }
                if(event.getActionMasked() == MotionEvent.ACTION_MOVE)
                {
                    end_y = (int)event.getY();
                    if(header_scroll_flag)
                    {
                        int height =start_header_point-(start_y-end_y);
                        if(height>0)height = 0;
                        else if(height<0-header_layout_height) height = 0-header_layout_height;
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        lp.setMargins(0,height,0,0);
                        header_layout.setLayoutParams(lp);
                    }
                    else
                    {
                        int height =start_tab_point-(start_y-end_y);
                        //Toast.makeText(_activity,"tab"+height,(int)200).show();
                        if(height>0)height = 0;
                        else if(height<0-tab_layout_height) height = 0-tab_layout_height;
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        lp.setMargins(0,0,0,0);
                        tabcontent_layout.setLayoutParams(lp);
                    }
                }
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    end_y = (int) event.getY();
                    if(header_scroll_flag)
                    {
                        start_header_point = start_header_point - (start_y - end_y);
                        if (start_header_point > 0)start_header_point = 0;
                        else if (start_header_point < 0 - header_layout_height)
                        {
                            start_header_point = 0 - header_layout_height;
                            if(tab_layout_height>0) header_scroll_flag = false;
                        }
                    }
                    else
                    {
                        start_tab_point = start_tab_point - (start_y - end_y);
                        if (start_tab_point > 0)
                        {
                            start_tab_point = 0;
                            header_scroll_flag = true;
                        }
                        else if (start_tab_point < 0 - tab_layout_height) start_tab_point = 0 - tab_layout_height;
                    }
                }
                return  true;
            }});
        drawImage();
    }
    private void drawImage()
    {
        if(image_change_view[0]!=null)
            user_photo.setImageBitmap(image_change_view[0]);
        if(image_change_view[1]!=null)
            banner.setImageBitmap(image_change_view[1]);
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
    private void pop_selectImage()///////////////////////////////////////////////////////////////////////////////////////////////////////////////camera & gallery  check  panel
    {
        Intent intent = new Intent(_activity,GetImageActivity.class) ;
        intent.putExtra("activity","user");
        _activity.startActivity(intent);
    }

    private void tabStyle_format()//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////tab select change
    {
        following_icon.setTint(Color.WHITE);
        post_icon.setTint(Color.WHITE);
        map_icon.setTint(Color.WHITE);

        following_icon.setTitleColor(getResources().getColor(R.color.normal_black));
        post_icon.setTitleColor(getResources().getColor(R.color.normal_black));
        map_icon.setTitleColor(getResources().getColor(R.color.normal_black));
    }

    public void onFollowersActivity(View view) {
        Intent intent = new Intent(this, FollowerActivity.class);
        intent.putExtra("title",getResources().getString(R.string.title_followers));
        this.startActivity(intent);
    }
    public void onFollowingActivity(View view) {
        Intent intent = new Intent(this, FollowerActivity.class);
        intent.putExtra("title",getResources().getString(R.string.title_following));
        this.startActivity(intent);
    }
    public void onBackActivity(View view) {
        startActivity(new Intent(this, MainHomeActivity.class));
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainHomeActivity.class));
    }
    public void onSettingActivity(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }
    public void onLargeMapActivity(View view)
    {
        startActivity(new Intent(this, MapActivity.class));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
