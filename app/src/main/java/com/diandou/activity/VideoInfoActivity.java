package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.adapter.VideoListAdapter;
import com.diandou.databinding.ActivityVideoInfoBinding;
import com.diandou.view.CommentListPopupWindow;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.OnClickListener;

public class VideoInfoActivity extends BaseActivity implements View.OnClickListener {

    private ActivityVideoInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_info);

        binding.back.setOnClickListener(this);
        binding.tvLike.setOnClickListener(this);
        binding.tvAppreciate.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvShare.setOnClickListener(this);
        GlideLoader.LoderImage(this, CommonUtil.getImageListString().get(5), binding.thumbnails);

        VideoListAdapter adapter = new VideoListAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setNestedScrollingEnabled(false);
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 10));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);
        adapter.refreshData(CommonUtil.getImageListString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_like:
                binding.tvLike.setSelected(!binding.tvLike.isSelected());
                break;
            case R.id.tv_appreciate:
                binding.tvAppreciate.setSelected(!binding.tvAppreciate.isSelected());
                break;
            case R.id.tv_comment:
                CommentListPopupWindow commentListPopupWindow = new CommentListPopupWindow(VideoInfoActivity.this);
                commentListPopupWindow.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view, Object object) {

                    }

                    @Override
                    public void onLongClick(View view, Object object) {

                    }
                });
                commentListPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_share:

                break;
        }
    }
}
