package com.cjt2325.cameralibrary.view;

import android.view.View;
import android.widget.AdapterView;

import java.util.Calendar;

public abstract class CameraOnClickListener implements AdapterView.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1500;
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            OnClickListener(view);
        }
    }

    public abstract void OnClickListener(View view);
}

