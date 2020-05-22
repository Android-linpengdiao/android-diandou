package com.diandou.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.databinding.ItemMessageLayoutBinding;
import com.diandou.databinding.ItemNoticeLayoutBinding;
import com.diandou.view.OnClickListener;


public class NoticeAdapter extends BaseRecyclerAdapter<String, ItemNoticeLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public NoticeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_notice_layout;
    }

    @Override
    protected void onBindItem(final ItemNoticeLayoutBinding binding, final String str, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, position);
                    }
                }
            });
        }

    }
}