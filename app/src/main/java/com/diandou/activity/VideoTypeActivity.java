package com.diandou.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.utils.ToastUtils;
import com.diandou.NavData;
import com.diandou.R;
import com.diandou.adapter.TabTypeAdapter;
import com.diandou.adapter.VideoTypeAdapter;
import com.diandou.databinding.ActivityReleaseBinding;
import com.diandou.databinding.ActivityVideoTypeBinding;
import com.diandou.view.OnClickListener;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;

public class VideoTypeActivity extends BaseActivity implements View.OnClickListener {

    private ActivityVideoTypeBinding binding;
    private VideoTypeAdapter adapter;
    private NavData.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_type);

        binding.back.setOnClickListener(this);
        binding.confirm.setOnClickListener(this);

        adapter = new VideoTypeAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof NavData.DataBean)
                    dataBean = (NavData.DataBean) object;
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        SendRequest.commonNav(new GenericsCallback<NavData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(NavData response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    for (int i = 0; i < response.getData().size(); i++) {
                        adapter.refreshData(response.getData());
                    }
                } else {
                    ToastUtils.showShort(VideoTypeActivity.this, response.getMsg());
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                if (dataBean != null && dataBean.getStatus() == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("nav", dataBean);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showShort(VideoTypeActivity.this, "请选择分类");
                }

                break;
        }
    }
}