package com.diandou.view;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by xiangcheng on 17/8/22.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public ItemDecoration(int space) {
        this.space = space;
    }

    private static final String TAG = "ItemDecoration";

    @Override
    public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
        Log.i(TAG, "getItemOffsets: ");
        super.getItemOffsets(outRect, itemPosition, parent);
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int marginRight = space;
        int marginBottom = space;
        // 如果是最后一行，则不需要绘制底部
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
            marginBottom = 0;
        }
        // 如果是最后一列，则不需要绘制右边
        if (isLastColum(parent, itemPosition, spanCount, childCount)) {
            marginRight = 0;
        }
        outRect.set(0, 0, marginRight, marginBottom);
    }

    //判断是最后一列
    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {
                return true;
            }
        }
        return false;
    }

    //判断是最后一行
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int ranger = childCount % spanCount;
            if (ranger == 0) {
                ranger = spanCount;
            }
            childCount = childCount - ranger;
            if (pos >= childCount) {
                return true;
            }
        }
        return false;
    }

    //列数
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}