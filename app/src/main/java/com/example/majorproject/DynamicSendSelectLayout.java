package com.example.majorproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class DynamicSendSelectLayout extends LinearLayout {

    public DynamicSendSelectLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicSendSelectLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.dynamic_send_select_frame, this, true);
    }
}