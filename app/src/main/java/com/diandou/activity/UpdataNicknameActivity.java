package com.diandou.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.databinding.ActivityUpdataNicknameBinding;
import com.diandou.model.MineWorkData;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class UpdataNicknameActivity extends BaseActivity implements View.OnClickListener {

    private ActivityUpdataNicknameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_updata_nickname);

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
                    ToastUtils.showShort(UpdataNicknameActivity.this, "请输入昵称,");
                    return;
                }
                SendRequest.editPersonal(getUserInfo().getData().getId(), getUserInfo().getData().getId(), getUserInfo().getData().getAvatar(), content, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            if (!CommonUtil.isBlank(response)) {
                                JSONObject jsonObject = new JSONObject(response);
                                ToastUtils.showShort(UpdataNicknameActivity.this, jsonObject.optString("msg"));
                                if (jsonObject.optInt("code") == 200) {
                                    baseInfo();
                                    Intent intent = new Intent();
                                    intent.putExtra("name",content);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                            } else {
                                ToastUtils.showShort(UpdataNicknameActivity.this, "编辑失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(UpdataNicknameActivity.this, "编辑失败");
                        }

                    }
                });
                break;
        }
    }


}