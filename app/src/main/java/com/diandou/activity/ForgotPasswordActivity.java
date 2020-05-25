package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.databinding.ActivityForgotPasswordBinding;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class ForgotPasswordActivity extends BaseActivity {
    private ActivityForgotPasswordBinding forgotPasswordBinding;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgotPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);

        forgotPasswordBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        forgotPasswordBinding.tvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createForgotAuthCode();
            }
        });
        forgotPasswordBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePasswordAndLogin();
            }
        });

        forgotPasswordBinding.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (null != charSequence) {
                    forgotPasswordBinding.tvConfirm.setSelected(charSequence.length() < 11 ? false : true);
                    forgotPasswordBinding.tvConfirm.setEnabled(charSequence.length() < 11 ? false : true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void createForgotAuthCode() {
        String phone = forgotPasswordBinding.etPhone.getText().toString().trim();
        if (phone.length() < 11) {
            ToastUtils.showShort(ForgotPasswordActivity.this, "手机号码不正确");
            return;
        }
        SendRequest.phoneCode(phone, "forget.password", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.optInt("code") == 200) {
                        forgotPasswordBinding.tvSendCode.setEnabled(false);
                        timer = new CountDownTimer(60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                forgotPasswordBinding.tvSendCode.setText(millisUntilFinished / 1000 + "");
                            }

                            @Override
                            public void onFinish() {
                                forgotPasswordBinding.tvSendCode.setEnabled(true);
                                forgotPasswordBinding.tvSendCode.setText("获取验证码");
                            }
                        }.start();
                        ToastUtils.showShort(ForgotPasswordActivity.this, "验证码发送成功");
                    } else {
                        String msg = json.optString("msg");
                        ToastUtils.showShort(getApplication(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updatePasswordAndLogin() {
        String phone = forgotPasswordBinding.etPhone.getText().toString().trim();
        String authCode = forgotPasswordBinding.etCode.getText().toString().trim();
        String password = forgotPasswordBinding.etPassword.getText().toString().trim();
        String confirmPassword = forgotPasswordBinding.etConfirmPassword.getText().toString().trim();

        if (phone.length() < 11) {
            ToastUtils.showShort(ForgotPasswordActivity.this, "手机号码不正确");
            return;
        }
        if (CommonUtil.isBlank(authCode)) {
            ToastUtils.showShort(ForgotPasswordActivity.this, "验证码不能为空");
            return;
        }
        if (password.length() < 8 || confirmPassword.length() < 6) {
            ToastUtils.showShort(ForgotPasswordActivity.this, "密码不能小于6位");
            return;
        }
        if (!password.equals(confirmPassword)) {
            ToastUtils.showShort(ForgotPasswordActivity.this, "密码不一致");
            return;
        }
        SendRequest.forgetPassword(phone, authCode, password, password, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.optInt("code") == 200) {
                        ToastUtils.showShort(ForgotPasswordActivity.this, "修改密码成功");
                        finish();
                    } else {
                        String msg = json.optString("msg");
                        ToastUtils.showShort(getApplication(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}