package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.MineFansAdapter;
import com.diandou.databinding.ActivityMineFansBinding;
import com.diandou.model.FansUserData;
import com.diandou.view.OnClickListener;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;

import org.json.JSONObject;

import okhttp3.Call;

public class MineFansActivity extends BaseActivity implements View.OnClickListener {
    private ActivityMineFansBinding binding;
    private MineFansAdapter adapter;
    private FansUserData fansUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mine_fans);
        binding.back.setOnClickListener(this);

        adapter = new MineFansAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                FansUserData.DataBeanX.DataBean dataBean = (FansUserData.DataBeanX.DataBean) object;
                setFollow(dataBean);
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.swipeRefreshLayout.setRefreshing(true);
        initData();
    }

    private void initData() {
        SendRequest.centerAttention(getUserInfo().getData().getId(), 10, 1, new GenericsCallback<FansUserData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(FansUserData response, int id) {
                fansUserData = response;
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(MineFansActivity.this, response.getMsg());
                }
            }

        });
    }

    private void setFollow(final FansUserData.DataBeanX.DataBean dataBean) {
        String url = dataBean.isAttention() ? APIUrls.url_centerUnFollow : APIUrls.url_centerFollow;
        SendRequest.centerFollow(getUserInfo().getData().getId(), dataBean.getId(), url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            dataBean.setAttention(!dataBean.isAttention());
                            if (dataBean.isAttention()) {
                                ToastUtils.showShort(MineFansActivity.this, "已关注");
                            }
                            adapter.notifyItemChanged(fansUserData.getData().getData().indexOf(dataBean));
                        } else {
                            ToastUtils.showShort(MineFansActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(MineFansActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(MineFansActivity.this, "请求失败");
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
