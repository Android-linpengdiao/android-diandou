package com.diandou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.activity.VideoInfoActivity;
import com.diandou.databinding.ItemWorkLayoutBinding;
import com.diandou.model.WorkData;


public class WorkAdapter extends BaseRecyclerAdapter<WorkData.DataBeanX.DataBean, ItemWorkLayoutBinding> {

    public WorkAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_work_layout;
    }

    @Override
    protected void onBindItem(final ItemWorkLayoutBinding binding, final WorkData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.title.setText(dataBean.getName());
            GlideLoader.LoderImage(mContext, dataBean.getImg(), binding.cover, 10);
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, VideoInfoActivity.class));
                }
            });
        }

    }
}
