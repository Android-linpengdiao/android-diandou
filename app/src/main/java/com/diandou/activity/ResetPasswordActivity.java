package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.databinding.ActivityFeedbackBinding;
import com.diandou.databinding.ActivityResetPasswordBinding;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);

        binding.back.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
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
