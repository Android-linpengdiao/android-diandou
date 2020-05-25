package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baselibrary.Constants;
import com.baselibrary.manager.DialogManager;
import com.baselibrary.utils.MsgCache;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.NoticeAdapter;
import com.diandou.databinding.ActivityNoticeListBinding;
import com.diandou.model.NoticeData;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;

public class NoticeListActivity extends BaseActivity implements View.OnClickListener {

    private ActivityNoticeListBinding binding;
    private NoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_list);
        binding.back.setOnClickListener(this);
        adapter = new NoticeAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        binding.swipeRefreshLayout.setRefreshing(true);
        initData();

    }

    private void initData() {
        SendRequest.commonMessage(new GenericsCallback<NoticeData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(NoticeData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null) {
                    adapter.refreshData(response.getData());
                } else {
                    ToastUtils.showShort(NoticeListActivity.this, response.getMsg());
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
        }
    }
}
