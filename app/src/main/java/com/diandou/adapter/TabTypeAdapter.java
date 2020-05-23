package com.diandou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.diandou.NavData;
import com.diandou.R;
import com.diandou.databinding.ItemTabTypeLayoutBinding;
import com.diandou.view.OnClickListener;


public class TabTypeAdapter extends BaseRecyclerAdapter<NavData.DataBean, ItemTabTypeLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public TabTypeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_tab_type_layout;
    }

    @Override
    protected void onBindItem(final ItemTabTypeLayoutBinding binding, final NavData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.name.setText(dataBean.getName());
            GlideLoader.LoderImage(mContext, CommonUtil.getImageListString().get(position), binding.icon, 100);
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
