package com.diandou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diandou.R;

public class TabLayout extends android.support.design.widget.TabLayout implements android.support.design.widget.TabLayout.OnTabSelectedListener {
    private Context mContext;

    public TabLayout(Context context) {
        super(context);
        this.mContext = context;
        setOnTabSelectedListener(this);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOnTabSelectedListener(this);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(Tab tab) {
        TextView title = (TextView)(((LinearLayout) ((LinearLayout) getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
        title.setTextAppearance(mContext, R.style.TabLayoutSelectedTextStyle);
    }

    @Override
    public void onTabUnselected(Tab tab) {
        TextView title = (TextView)(((LinearLayout) ((LinearLayout) getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
        title.setTextAppearance(mContext, R.style.TabLayoutUnselectedTextStyle);
    }

    @Override
    public void onTabReselected(Tab tab) {

    }

    public void setTabSelected(int position){
        TextView title = (TextView)(((LinearLayout) ((LinearLayout) getChildAt(0)).getChildAt(position)).getChildAt(1));
        title.setTextAppearance(mContext, R.style.TabLayoutSelectedTextStyle);
    }
}
