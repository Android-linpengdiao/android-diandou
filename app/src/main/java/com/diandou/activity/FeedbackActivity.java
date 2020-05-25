package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.databinding.ActivityFeedbackBinding;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    private ActivityFeedbackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:
                final String content = binding.edContent.getText().toString().trim();
                if (CommonUtil.isBlank(content)) {
                    ToastUtils.showShort(FeedbackActivity.this, "请输入您的宝贵意见");
                    return;
                }
                SendRequest.centerComment(getUserInfo().getData().getId(), content, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            if (!CommonUtil.isBlank(response)) {
                                JSONObject jsonObject = new JSONObject(response);
                                ToastUtils.showShort(FeedbackActivity.this, jsonObject.optString("msg"));
                                if (jsonObject.optInt("code") == 200) {
                                    finish();
                                }
                            } else {
                                ToastUtils.showShort(FeedbackActivity.this, "请求失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(FeedbackActivity.this, "请求失败");
                        }

                    }
                });
                break;
        }
    }


}
