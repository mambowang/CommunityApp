package com.jis.coommunity.addview;


import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.jis.coommunity.R;
import com.jis.coommunity.components.ElementTag;
import com.jis.coommunity.components.GetImageActivity;
import com.jis.coommunity.detail.RemoveClickListener;
import com.jis.coommunity.home.LargeMapActivity;
import com.jis.coommunity.home.MainHomeActivity;
import com.wefika.flowlayout.FlowLayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class NewRouteActivity extends AppCompatActivity implements OnMapReadyCallback ,RemoveClickListener{
    private static NewRouteActivity _activity=null;
    private LinearLayout img_layout;
    private static Bitmap []imgIds=new Bitmap[100];
    private static int number=0;
    private FrameLayout add_image;
    private final int PICK_IMAGE_GALLERY = 2,PICK_IMAGE_CAMERA=1;
    private EditText tag_edit;
    private InputMethodManager imm;
    private EditText edit_placeName;
    private EditText edit_info;
    private EditText edit_placeAddress;
    private ScrollView main_scroll;
    private ArrayList<Integer> myTagList = new ArrayList<>();
    private FlowLayout tags_layout;
    private static int input_tagnum=0;
    public static NewRouteActivity sharedActivity(){
        return _activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity =this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_new);
        Intent _intent = getIntent();
        String bitmap_uri = _intent.getStringExtra("image");
        if(bitmap_uri!=null) {
            try {
                imgIds[number] = MediaStore.Images.Media.getBitmap(this.getContentResolver(),  Uri.parse(bitmap_uri));
                imgIds[number]= rotateImageIfRequired(imgIds[number],  Uri.parse(bitmap_uri));
                imgIds[number]  = getResizedBitmap(imgIds[number] , 500);
            } catch (IOException e) {
                e.printStackTrace();
            }
            number++;
        }
        add_image = (FrameLayout)findViewById(R.id.route_img).findViewById(R.id.btn_add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_activity,GetImageActivity.class) ;
                intent.putExtra("activity","route");
                _activity.startActivity(intent);
                finish();
            }
        });
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        FragmentManager fragmentManager_small = getFragmentManager();
        MapFragment mapFragment_small = (MapFragment)fragmentManager_small.findFragmentById(R.id.small_map);
        mapFragment_small.getMapAsync(this);
        img_layout = (LinearLayout)findViewById(R.id.route_img).findViewById(R.id.image_list);
        setImageDraw();
        tags_layout = (FlowLayout)findViewById(R.id.tags_flowlayout);
        tag_edit = (EditText)findViewById(R.id.tag_edit);
        tag_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_NEXT  || actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    String str = tag_edit.getText().toString();
                    if(str.length()!=0) {
                        ElementTag temp = new ElementTag(_activity,null);
                        temp.setString(input_tagnum,str);
                        input_tagnum++;
                        temp.setLayoutParams(new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        temp.setPadding(15,0,15,10);
                        tags_layout.addView(temp);
                    }
                    tag_edit.setText("");
                    return true;
                }
                return false;
            }
        });
        edit_placeName = (EditText)findViewById(R.id.edit_placename);
        edit_info = (EditText)findViewById(R.id.edit_information);
        edit_placeAddress = (EditText)findViewById(R.id.edit_placeaddress);
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
    private void setImageDraw()
    {
        if(number>0)
        {
            LinearLayout main_panel = (LinearLayout)findViewById(R.id.route_img).findViewById(R.id.main_panel);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,main_panel.getLayoutParams().height ,Gravity.LEFT | Gravity.CENTER_VERTICAL);
            main_panel.setLayoutParams(params);
            img_layout.removeAllViews();
            for(int i=0;i<number;i++)
            {
                ImageView temp = new ImageView(this);
                temp.setImageBitmap(imgIds[i]);
                temp.setScaleType(ImageView.ScaleType.FIT_XY);
                temp.setLayoutParams(new LinearLayout.LayoutParams(img_layout.getLayoutParams().height, ViewGroup.LayoutParams.MATCH_PARENT));
                temp.setPadding(0,0,10,0);
                img_layout.addView(temp);
            }
        }
    }
    @Override
    public void OnRemoveClick(int index) {
        int select_index = index;
        myTagList.add(index);
        for(int i=0;i<myTagList.size();i++)
        {
            if(index>myTagList.get(i))select_index--;
        }
        tags_layout.removeViewAt(select_index);
    }

    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(edit_info.getWindowToken(), 0);
        edit_info.clearFocus();
        imm.hideSoftInputFromWindow(tag_edit.getWindowToken(), 0);
        tag_edit.clearFocus();
        imm.hideSoftInputFromWindow(edit_placeAddress.getWindowToken(), 0);
        edit_placeAddress.clearFocus();
        imm.hideSoftInputFromWindow(edit_placeName.getWindowToken(), 0);
        edit_placeName.clearFocus();
    }
    public void onBackActivity(View view)
    {
        super.onBackPressed();
    }

    public void onLargeMapActivity(View view)
    {
        startActivity(new Intent(this, LargeMapActivity.class));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
