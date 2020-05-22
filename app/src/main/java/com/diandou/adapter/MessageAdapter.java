package com.diandou.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.databinding.ItemMessageLayoutBinding;
import com.diandou.view.OnClickListener;


public class MessageAdapter extends BaseRecyclerAdapter<String, ItemMessageLayoutBinding> {

    private OnClickListener onClickListener;

    private int type = 0;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MessageAdapter(Context context) {
        super(context);
    }

    public MessageAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_message_layout;
    }

    @Override
    protected void onBindItem(final ItemMessageLayoutBinding binding, final String str, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.cover.setVisibility(type == 0 ? View.VISIBLE : View.GONE);
            GlideLoader.LoderImage(mContext, CommonUtil.getImageListString().get(position), binding.userIcon, 100);
            GlideLoader.LoderImage(mContext, CommonUtil.getImageListString().get(position), binding.cover, 2);
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
