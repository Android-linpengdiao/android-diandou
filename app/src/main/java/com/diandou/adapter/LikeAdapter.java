package com.diandou.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.activity.UserHomeActivity;
import com.diandou.databinding.ItemMessageLayoutBinding;
import com.diandou.model.LikeData;
import com.diandou.model.MessageData;
import com.diandou.view.OnClickListener;


public class LikeAdapter extends BaseRecyclerAdapter<LikeData.DataBean, ItemMessageLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public LikeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_message_layout;
    }

    @Override
    protected void onBindItem(final ItemMessageLayoutBinding binding, final LikeData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvTitle.setText(dataBean.getTourist().getName());
            binding.tvDesc.setText("赞了你视频");
            binding.tvTime.setText(dataBean.getUpdated_at());
            GlideLoader.LoderImage(mContext, dataBean.getTourist().getAvatar(), binding.userIcon, 100);
            GlideLoader.LoderImage(mContext, dataBean.getContent().getImg(), binding.cover, 2);
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
                    intent.putExtra("uid", dataBean.getTourist().getId());
                    intent.putExtra("isFollow", false);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
