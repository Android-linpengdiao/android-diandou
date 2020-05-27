package com.diandou.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.diandou.NavData;
import com.diandou.R;
import com.diandou.databinding.ItemTabTypeLayoutBinding;
import com.diandou.databinding.ItemVideoTypeLayoutBinding;
import com.diandou.view.OnClickListener;


public class VideoTypeAdapter extends BaseRecyclerAdapter<NavData.DataBean, ItemVideoTypeLayoutBinding> {
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public VideoTypeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_video_type_layout;
    }

    @Override
    protected void onBindItem(final ItemVideoTypeLayoutBinding binding, final NavData.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.name.setText(dataBean.getName());
            binding.name.setSelected(dataBean.getStatus() == 0 ? false : true);
            binding.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        dataBean.setStatus(dataBean.getStatus() == 0 ? 1 : 0);
                        binding.name.setSelected(dataBean.getStatus() == 0 ? false : true);
                        for (int i = 0; i < mList.size(); i++) {
                            if (dataBean != mList.get(i)) {
                                mList.get(i).setStatus(0);
                            }
                        }
                        onClickListener.onClick(v, dataBean);
                        notifyDataSetChanged();
                    }
                }
            });
        }

    }
}
