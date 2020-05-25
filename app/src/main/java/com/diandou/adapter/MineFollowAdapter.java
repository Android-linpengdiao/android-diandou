package com.diandou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.activity.UserHomeActivity;
import com.diandou.databinding.ItemFriendsLayoutBinding;
import com.diandou.model.FollowUserData;
import com.diandou.view.OnClickListener;


public class MineFollowAdapter extends BaseRecyclerAdapter<FollowUserData.DataBeanX.DataBean, ItemFriendsLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MineFollowAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_friends_layout;
    }

    @Override
    protected void onBindItem(final ItemFriendsLayoutBinding binding, final FollowUserData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvTitle.setText(dataBean.getName());
            binding.tvDesc.setText("粉丝：" + dataBean.getLiker());
            binding.tvFollowers.setText("已关注");
            binding.tvFollowers.setText(dataBean.getAttention() != -1 ? "已关注" : "关注");
            GlideLoader.LoderImage(mContext, dataBean.getAvatar(), binding.userIcon, 100);
            binding.tvFollowers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, dataBean);
                    }
                }
            });
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomeActivity.class);
                    intent.putExtra("uid", dataBean.getId());
                    intent.putExtra("isFollow", dataBean.getAttention() != -1 ? true : false);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
