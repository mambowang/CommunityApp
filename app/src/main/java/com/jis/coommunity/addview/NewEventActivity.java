package com.jis.coommunity.addview;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baoyz.actionsheet.ActionSheet;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.jis.coommunity.R;
import com.jis.coommunity.components.ElementTag;
import com.jis.coommunity.components.GetImageActivity;
import com.jis.coommunity.detail.RemoveClickListener;
import com.jis.coommunity.home.LargeMapActivity;
import com.jis.coommunity.home.MainHomeActivity;
import com.wefika.flowlayout.FlowLayout;


public class NewEventActivity extends AppCompatActivity implements OnMapReadyCallback,RemoveClickListener {
    private LinearLayout img_layout;
    private static NewEventActivity _activity;
    private static Bitmap []imgIds=new Bitmap[100];
    private static int number=0;
    private final int PICK_IMAGE_GALLERY = 2,PICK_IMAGE_CAMERA=1;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private TextView date_from;
    private TextView date_to;
    private TextView time_from;
    private TextView time_to;
    private TextView select_view;
    private EditText tag_edit;
    private LinearLayout tag_layout;
    final Calendar cal = Calendar.getInstance();
    private InputMethodManager imm;
    private EditText edit_placeName;
    private EditText edit_info;
    private EditText edit_placeAddress;
    private EditText edit_ticketnum;
    private EditText edit_phoneHeader;
    private EditText edit_phoneNumber;
    private EditText edit_netCost;
    private EditText edit_commission;
    private EditText edit_finalPrice;
    private ScrollView main_scroll;
    private FrameLayout add_image;
    private ArrayList<Integer> myTagList = new ArrayList<>();
    private FlowLayout tags_layout;
    private static int input_tagnum=0;
    public static NewEventActivity sharedActivity(){
        return _activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _activity =this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_new);
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
        add_image = (FrameLayout)findViewById(R.id.event_img).findViewById(R.id.btn_add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_activity,GetImageActivity.class) ;
                intent.putExtra("activity","event");
                _activity.startActivity(intent);
                finish();
            }
        });
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        FragmentManager fragmentManager_small = getFragmentManager();
        MapFragment mapFragment_small = (MapFragment)fragmentManager_small.findFragmentById(R.id.small_map);
        mapFragment_small.getMapAsync(this);
        img_layout = (LinearLayout)findViewById(R.id.event_img).findViewById(R.id.image_list);
        date_from = (TextView)findViewById(R.id.edit_date_from);
        date_from.setOnClickListener(new datePicker());
        date_to = (TextView)findViewById(R.id.edit_date_to);
        date_to.setOnClickListener(new datePicker());

        time_from = (TextView)findViewById(R.id.edit_time_from);
        time_from.setOnClickListener(new timePicker());
        time_to = (TextView)findViewById(R.id.edit_time_to);
        time_to.setOnClickListener(new timePicker());
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
        edit_ticketnum = (EditText)findViewById(R.id.edit_ticket_num);
        edit_phoneHeader = (EditText)findViewById(R.id.edit_phone_header);
        edit_phoneNumber = (EditText)findViewById(R.id.edit_phone_number);
        edit_netCost = (EditText)findViewById(R.id.edit_netcost);
        edit_commission = (EditText)findViewById(R.id.edit_commission);
        edit_finalPrice = (EditText)findViewById(R.id.edit_price);
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

    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(edit_info.getWindowToken(), 0);
        edit_info.clearFocus();
        imm.hideSoftInputFromWindow(edit_commission.getWindowToken(), 0);
        edit_commission.clearFocus();
        imm.hideSoftInputFromWindow(tag_edit.getWindowToken(), 0);
        tag_edit.clearFocus();
        imm.hideSoftInputFromWindow(edit_placeAddress.getWindowToken(), 0);
        edit_placeAddress.clearFocus();
        imm.hideSoftInputFromWindow(edit_placeName.getWindowToken(), 0);
        edit_placeName.clearFocus();
        imm.hideSoftInputFromWindow(edit_phoneNumber.getWindowToken(), 0);
        edit_phoneNumber.clearFocus();
        imm.hideSoftInputFromWindow(edit_phoneHeader.getWindowToken(), 0);
        edit_phoneHeader.clearFocus();
        imm.hideSoftInputFromWindow(edit_ticketnum.getWindowToken(), 0);
        edit_ticketnum.clearFocus();
        imm.hideSoftInputFromWindow(edit_netCost.getWindowToken(), 0);
        edit_netCost.clearFocus();
        imm.hideSoftInputFromWindow(edit_finalPrice.getWindowToken(), 0);
        edit_finalPrice.clearFocus();
    }
    public void onBackActivity(View view) {
       super.onBackPressed();
    }

    public void onLargeMapActivity(View view)
    {
        startActivity(new Intent(this, LargeMapActivity.class));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

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

    private class datePicker implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            select_view =(TextView)v;
            final DatePickerDialog dialog = new DatePickerDialog(NewEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                    String msg = String.format("%d/%d/%d", year, month+1, date);
                    select_view.setText(msg);
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
            dialog.setOnShowListener( new DialogInterface.OnShowListener()
            {
                @Override public void onShow(DialogInterface arg0)
                {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                }
            });
            dialog.getDatePicker().setMaxDate(new Date().getTime());
            dialog.show();
        }
    }
    private class timePicker implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            select_view =(TextView)v;
            final TimePickerDialog dialog = new TimePickerDialog(NewEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int min) {

                    String msg = String.format("%d:%d", hour, min);
                    select_view.setText(msg);
                }
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
            dialog.setOnShowListener( new DialogInterface.OnShowListener()
            {
                @Override public void onShow(DialogInterface arg0)
                {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                }
            });
            dialog.show();
        }
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
    private void setImageDraw()
    {
        if(number>0)
        {
            LinearLayout main_panel = (LinearLayout)findViewById(R.id.event_img).findViewById(R.id.main_panel);
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
}
