package com.diandou.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.databinding.ItemFansLayoutBinding;
import com.diandou.databinding.ItemMessageLayoutBinding;
import com.diandou.model.FansData;
import com.diandou.model.MessageData;
import com.diandou.view.OnClickListener;


public class FansAdapter extends BaseRecyclerAdapter<FansData.DataBeanX.DataBean, ItemFansLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public FansAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_fans_layout;
    }

    @Override
    protected void onBindItem(final ItemFansLayoutBinding binding, final FansData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvTitle.setText(dataBean.getName());
            binding.tvDesc.setText("关注了你");
            binding.tvTime.setText(dataBean.getUpdated_at());
            GlideLoader.LoderImage(mContext, dataBean.getAvatar(), binding.userIcon, 100);
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
