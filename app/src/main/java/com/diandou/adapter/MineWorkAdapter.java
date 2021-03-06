package com.diandou.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.activity.WorkInfoActivity;
import com.diandou.databinding.ItemMineWorkLayoutBinding;
import com.diandou.model.MineWorkData;
import com.diandou.view.OnClickListener;

public class MineWorkAdapter extends BaseRecyclerAdapter<MineWorkData.DataBeanX.DataBean, ItemMineWorkLayoutBinding> {

    private static final String TAG = "MineWorkAdapter";
    private boolean isSelection = false;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelection(boolean selection) {
        isSelection = selection;
        notifyDataSetChanged();
    }

    public MineWorkAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_mine_work_layout;
    }

    @Override
    protected void onBindItem(final ItemMineWorkLayoutBinding binding, final MineWorkData.DataBeanX.DataBean dataBean, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.tvAssist.setText("" + dataBean.getAssist());
            GlideLoader.LoderImage(mContext, dataBean.getImg(), binding.cover);
            binding.selection.setSelected(dataBean.isSelection());
            binding.selection.setVisibility(isSelection ? View.VISIBLE : View.GONE);
            binding.selection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBean.setSelection(!dataBean.isSelection());
                    binding.selection.setSelected(dataBean.isSelection());
                    notifyItemChanged(position);
                }
            });
            binding.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WorkInfoActivity.class);
                    intent.putExtra("id", dataBean.getId());
                    mContext.startActivity(intent);
                }
            });
            binding.viewLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onClickListener != null) {
                        isSelection = true;
                        onClickListener.onLongClick(view, true);
                        notifyDataSetChanged();
                    }
                    return true;
                }
            });
        }

    }
}
