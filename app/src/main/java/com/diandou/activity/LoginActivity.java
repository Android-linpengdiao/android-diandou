package com.diandou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.ToastUtils;
import com.diandou.MainActivity;
import com.diandou.MyApplication;
import com.diandou.R;
import com.diandou.databinding.ActivityLoginBinding;
import com.diandou.manager.TencentHelper;
import com.diandou.manager.WXManager;
import com.diandou.utils.Config;
import com.diandou.weibo.Constants;
import com.diandou.weibo.WBAuthActivity;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import okhttp3.Call;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding loginBinding;
    private WechatReceiver wxReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginBinding.loginConfirm.setOnClickListener(this);
        loginBinding.loginWx.setOnClickListener(this);
        loginBinding.loginQq.setOnClickListener(this);
        loginBinding.loginWb.setOnClickListener(this);
        loginBinding.forgotPassword.setOnClickListener(this);

        wxReceiver = new WechatReceiver();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(wxReceiver, new IntentFilter(Config.wechat_get_token_success));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_confirm:
                passwordLogin();
                break;
            case R.id.forgot_password:
                openActivity(ForgotPasswordActivity.class);
                break;
            case R.id.login_wx:
                WXManager.startAuth(getApplicationContext());
                break;
            case R.id.login_qq:
                TencentHelper.auth(this, new IUiListener() {

                    @Override
                    public void onError(UiError arg0) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onComplete(Object arg0) {
                        TencentHelper.refreshUserInfo(LoginActivity.this, new IUiListener() {

                            @Override
                            public void onError(UiError arg0) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onComplete(Object arg0) {
                                ToastUtils.showShort(getApplication(), "QQ授权成功: Openid = " + TencentHelper.getOpenId());

                            }

                            @Override
                            public void onCancel() {
                                // TODO Auto-generated method stub

                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        // TODO Auto-generated method stub·
                    }
                });
                break;
            case R.id.login_wb:
                WbSdk.install(LoginActivity.this, new AuthInfo(LoginActivity.this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
                openActivity(WBAuthActivity.class);
                break;
        }
    }

    /**
     * ****************************** 密码登录 **********************************
     */

    private void passwordLogin() {
        String phone = loginBinding.loginUsername.getText().toString().trim();
        String password = loginBinding.loginPassword.getText().toString().trim();

        if (phone.length() < 11) {
            ToastUtils.showShort(LoginActivity.this, "手机号码不正确");
            return;
        }

        if (password.length() < 6) {
            ToastUtils.showShort(LoginActivity.this, "密码不能小于8位");
            return;
        }

//        if (!loginBinding.checkBox.isChecked()) {
//            ToastUtils.showShort(getApplication(), "请同意服务协议和用户隐私政策");
//            return;
//        }
        SendRequest.login(phone, password, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(UserInfo response, int id) {
                if (response.getCode() == 200) {
                    MyApplication.getInstance().setUserInfo(response);
                    openActivity(MainActivity.class);
                    finish();
                } else {
                    ToastUtils.showShort(LoginActivity.this, "获取用户信息失败," + response.getMsg());
                }
            }

        });
    }

    /**
     * ****************************** 微信登录 **********************************
     */

    private class WechatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                String action = intent.getAction();
                if (action.equals(Config.wechat_get_token_success)) {
                    ToastUtils.showShort(getApplication(), "微信授权成功: Openid = " + WXManager.getOpenid());
                }
            }
        }
    }

    /**
     * ****************************** QQ登录 **********************************
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    TencentHelper.refreshUserInfo(LoginActivity.this, new IUiListener() {

                        @Override
                        public void onError(UiError arg0) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onComplete(Object arg0) {

                        }

                        @Override
                        public void onCancel() {
                            // TODO Auto-generated method stub

                        }
                    });
                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wxReceiver != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(wxReceiver);
        }
    }
}
