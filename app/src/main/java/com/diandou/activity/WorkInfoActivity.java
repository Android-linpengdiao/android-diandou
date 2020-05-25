package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.WorkAdapter;
import com.diandou.databinding.ActivityWorkInfoBinding;
import com.diandou.model.WorkData;
import com.diandou.model.WorkDetail;
import com.diandou.view.CommentListPopupWindow;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.OnClickListener;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;

public class WorkInfoActivity extends BaseActivity implements View.OnClickListener {

    private ActivityWorkInfoBinding binding;
    private WorkAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_info);

        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", 0);
        } else {
            finish();
        }

        binding.back.setOnClickListener(this);
        binding.tvLike.setOnClickListener(this);
        binding.tvAppreciate.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvShare.setOnClickListener(this);
        GlideLoader.LoderImage(this, CommonUtil.getImageListString().get(5), binding.thumbnails);

        adapter = new WorkAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setNestedScrollingEnabled(false);
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 10));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);

        SendRequest.workDetail(id, new GenericsCallback<WorkDetail>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(WorkDetail response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    initView(response.getData());
                } else {
                    ToastUtils.showShort(WorkInfoActivity.this, response.getMsg());
                }
            }

        });


    }

    private void initView(WorkDetail.DataBean data) {
        binding.tvDesc.setText(data.getDesc());
        binding.tvAddr.setText(data.getAddr());
        binding.tvTime.setText(data.getUpdated_at());
        binding.tvAppreciate.setText(data.getAssist() + "");
        GlideLoader.LoderImage(WorkInfoActivity.this, data.getImg(), binding.thumbnails);

//        getVideos(data);
        showContentComment(data);
    }

    private void getVideos(WorkDetail.DataBean data) {
        SendRequest.searchWorkType(data.getNav_id(), 10, 1, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(WorkData response, int id) {
                if (response.getCode() == 200) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(WorkInfoActivity.this, response.getMsg());
                }
            }

        });

    }

    private static final String TAG = "WorkInfoActivity";
    private void showContentComment(WorkDetail.DataBean data) {
        Log.i(TAG, "showContentComment: "+data.getId());
        SendRequest.showContentComment(data.getId(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
            }
        });

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
                CommentListPopupWindow commentListPopupWindow = new CommentListPopupWindow(WorkInfoActivity.this);
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
