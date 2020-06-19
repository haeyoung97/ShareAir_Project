package com.example.majorproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class DynamicSelectLayout extends LinearLayout {

    public DynamicSelectLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicSelectLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.dynamic_select_frame, this, true);
    }
}
