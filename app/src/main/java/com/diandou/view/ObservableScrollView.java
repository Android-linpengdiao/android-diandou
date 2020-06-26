package com.diandou.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

    //标识是否滑动到顶部
    private boolean isScrollToStart = false;
    //标识是否滑动到底部
    private boolean isScrollToEnd = false;

    public interface ScrollViewListener {

        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

        void onScrollToEnd();
    }

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);

            View contentView = getChildAt(0);
            if (contentView != null && contentView.getMeasuredHeight() == (getScrollY() + getHeight())) {
                //滚动到底部，ScrollView存在回弹效果效应
                //优化，只过滤第一次
                if (!isScrollToEnd) {
                    isScrollToEnd = true;
                    Log.e("CustomScrollView", "toEnd,scrollY:" + getScrollY());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollViewListener.onScrollToEnd();
                            isScrollToEnd = false;
                        }
                    },200);
                }
            }
        }
    }

}
