package com.diandou.adapter;

import android.content.Context;
import android.view.View;

import com.diandou.R;
import com.diandou.databinding.ItemSearchHistoryLayoutBinding;
import com.diandou.view.OnClickListener;


public class SearchHistoryAdapter extends BaseRecyclerAdapter<String, ItemSearchHistoryLayoutBinding> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SearchHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) { //应该在此根viewType 选择不同布局的,但设计图上给的差距不大.就整合成一个布局了
        return R.layout.item_search_history_layout;
    }

    @Override
    protected void onBindItem(ItemSearchHistoryLayoutBinding binding, final String str, final int position) {
        if (mList != null && mList.size() > 0) {
            binding.content.setText(str);
            binding.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onClick(v,mList.get(position));
                    }
                }
            });
        }

    }
}
