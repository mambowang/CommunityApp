package com.jis.coommunity.components;

        import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.drawable.ColorDrawable;
        import android.support.annotation.Nullable;
        import android.util.AttributeSet;
        import android.view.Display;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.Window;
        import android.widget.FrameLayout;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.jis.coommunity.R;
        import com.jis.coommunity.home.MainHomeActivity;


public class AddImageView extends LinearLayout {
    private ListView image_list;
    private Context _con;
    public AddImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _con = context;
        init(context);
    }
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.comp_add_image,this);
        FrameLayout btn_add_image = (FrameLayout)findViewById(R.id.btn_add_image);
    }
}
