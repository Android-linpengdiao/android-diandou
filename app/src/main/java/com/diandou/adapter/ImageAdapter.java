package com.diandou.adapter;

import android.content.Context;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.databinding.ItemImageLayoutBinding;
import com.diandou.view.OnClickListener;


public class ImageAdapter extends BaseRecyclerAdapter<String, ItemImageLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ImageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_image_layout;
    }

    @Override
    protected void onBindItem(final ItemImageLayoutBinding binding, final String str, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.delete.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
            if (position == 0) {
                GlideLoader.LoderDrawable(mContext, R.drawable.picture_upload, binding.image, 8);
            } else {
                GlideLoader.LoderLoadImage(mContext, str, binding.image, 8);
            }
            binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    notifyDataSetChanged();
                }
            });
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onClick(view, position);
                    }
                }
            });
        }

    }
}
