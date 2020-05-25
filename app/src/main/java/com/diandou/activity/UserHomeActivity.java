package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.MineWorkAdapter;
import com.diandou.databinding.ActivityUserHomeBinding;
import com.diandou.model.MineWorkData;
import com.diandou.view.GridItemDecoration;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;

import org.json.JSONObject;

import okhttp3.Call;

public class UserHomeActivity extends BaseActivity implements View.OnClickListener {
    private ActivityUserHomeBinding binding;
    private MineWorkAdapter adapter;
    private int uid;
    private boolean isFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_home);

        uid = getIntent().getIntExtra("uid", 0);
        isFollow = getIntent().getBooleanExtra("isFollow", false);
        binding.headLoginLayout.back.setOnClickListener(this);
        binding.headLoginLayout.back.setVisibility(View.VISIBLE);
        binding.headLoginLayout.tvIsFollow.setVisibility(View.VISIBLE);
        binding.headLoginLayout.tvEditor.setVisibility(View.GONE);
        binding.headLoginLayout.tvSetting.setVisibility(View.GONE);

        adapter = new MineWorkAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 2));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
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

        SendRequest.baseInfo(uid, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(UserInfo response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    initView(response);
                }
            }

        });

        SendRequest.isFollow(getUserInfo().getData().getId(), uid, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {

                        } else {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        SendRequest.centerSelfWork(uid, new GenericsCallback<MineWorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(MineWorkData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(UserHomeActivity.this, response.getMsg());
                }
            }

        });
    }

    private void initView(UserInfo userInfo) {

        binding.headLoginLayout.tvIsFollow.setOnClickListener(this);

        binding.headLoginLayout.userName.setText(userInfo.getData().getName());
        binding.headLoginLayout.touristId.setText("点逗号：" + userInfo.getData().getTourist_id());
        GlideLoader.LoderClipImage(UserHomeActivity.this, userInfo.getData().getAvatar(), binding.headLoginLayout.userIcon);
        binding.headLoginLayout.tvFollowers.setText(getUserInfo().getData().getFollowers() + "");
        binding.headLoginLayout.tvLiker.setText(getUserInfo().getData().getLiker() + "");

        binding.headLoginLayout.tvIsFollow.setSelected(isFollow);
        binding.headLoginLayout.tvIsFollow.setText(isFollow ? "已关注" : "关注");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_is_follow:
                String url = binding.headLoginLayout.tvIsFollow.isSelected() ? APIUrls.url_centerUnFollow : APIUrls.url_centerFollow;
                SendRequest.centerFollow(getUserInfo().getData().getId(), uid, url, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            if (!CommonUtil.isBlank(response)) {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 200) {
                                    binding.headLoginLayout.tvIsFollow.setSelected(!binding.headLoginLayout.tvIsFollow.isSelected());
                                    binding.headLoginLayout.tvIsFollow.setText(binding.headLoginLayout.tvIsFollow.isSelected() ? "已关注" : "关注");
                                    if (binding.headLoginLayout.tvIsFollow.isSelected()) {
                                        ToastUtils.showShort(UserHomeActivity.this, "已关注");
                                    }
                                } else {
                                    ToastUtils.showShort(UserHomeActivity.this, jsonObject.optString("msg"));
                                }
                            } else {
                                ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(UserHomeActivity.this, "请求失败");
                        }

                    }
                });
                break;
        }
    }
}