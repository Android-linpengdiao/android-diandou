package com.diandou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
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
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding loginBinding;
    private WechatReceiver wxReceiver = null;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginBinding.sendCode.setOnClickListener(this);
        loginBinding.login.setOnClickListener(this);
        loginBinding.switchLogin.setOnClickListener(this);
        loginBinding.passwordLoginLayout.passwordLoginView.setOnClickListener(this);
        loginBinding.passwordLoginLayout.back.setOnClickListener(this);
        loginBinding.passwordLoginLayout.passwordLogin.setOnClickListener(this);
        loginBinding.loginWx.setOnClickListener(this);
        loginBinding.loginQq.setOnClickListener(this);
        loginBinding.loginWb.setOnClickListener(this);
        loginBinding.register.setOnClickListener(this);
        loginBinding.forgotPassword.setOnClickListener(this);
        loginBinding.passwordLoginLayout.register.setOnClickListener(this);
        loginBinding.passwordLoginLayout.forgotPassword.setOnClickListener(this);

        wxReceiver = new WechatReceiver();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(wxReceiver, new IntentFilter(Config.wechat_get_token_success));

    }

    @Override
    protected void onResume() {
        super.onResume();
        finishAllActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_code:
                String phone = loginBinding.phone.getText().toString().trim();
                if (phone.length() < 11) {
                    ToastUtils.showShort(LoginActivity.this, "手机号码不正确");
                    return;
                }
                SendRequest.phoneCode(phone, "phone.login", new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 200) {
                                loginBinding.sendCode.setEnabled(false);
                                timer = new CountDownTimer(60000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        loginBinding.sendCode.setText(millisUntilFinished / 1000 + "");
                                    }

                                    @Override
                                    public void onFinish() {
                                        loginBinding.sendCode.setEnabled(true);
                                        loginBinding.sendCode.setText("获取验证码");
                                    }
                                }.start();
                                ToastUtils.showShort(LoginActivity.this, "验证码成功");
                            } else {
                                ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.login:
                phoneLogin();
                break;
            case R.id.switch_login:
                loginBinding.passwordLoginLayout.passwordLoginView.setVisibility(View.VISIBLE);
                break;
            case R.id.back:
                loginBinding.passwordLoginLayout.passwordLoginView.setVisibility(View.GONE);
                break;
            case R.id.password_login:
                passwordLogin();
                break;
            case R.id.register:
                openActivity(RegisterActivity.class);
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
                                isBindThird("qq", TencentHelper.getOpenId());
//                                ToastUtils.showShort(getApplication(), "QQ授权成功: Openid = " + TencentHelper.getOpenId());

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
                mSsoHandler = new SsoHandler(LoginActivity.this);
                mSsoHandler.authorizeClientSso(new SelfWbAuthListener());
                break;
        }
    }

    /**
     * ****************************** 短信登录 **********************************
     */

    private void phoneLogin() {
        String phone = loginBinding.phone.getText().toString().trim();
        String phoneCode = loginBinding.authCode.getText().toString().trim();

        if (phone.length() < 11) {
            ToastUtils.showShort(LoginActivity.this, "手机号码不正确");
            return;
        }

        if (phoneCode.length() <= 0) {
            ToastUtils.showShort(LoginActivity.this, "请输入验证码");
            return;
        }
        SendRequest.phoneLogin(phone, phoneCode, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
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
                    ToastUtils.showShort(LoginActivity.this, response.getMsg());
                }
            }

        });
    }

    /**
     * ****************************** 密码登录 **********************************
     */

    private void passwordLogin() {
        String phone = loginBinding.passwordLoginLayout.loginUsername.getText().toString().trim();
        String password = loginBinding.passwordLoginLayout.loginPassword.getText().toString().trim();

        if (phone.length() < 11) {
            ToastUtils.showShort(LoginActivity.this, "手机号码不正确");
            return;
        }

        if (password.length() < 6) {
            ToastUtils.showShort(LoginActivity.this, "密码不能小于6位");
            return;
        }
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
                    ToastUtils.showShort(LoginActivity.this, response.getMsg());
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
                    ToastUtils.showShort(getApplication(), "微信授权成功");
                    WXManager.getUserInfo(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            finish();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject json = new JSONObject(response);
                                String errorCode = json.optString("errcode");
                                if (!CommonUtil.isBlank(errorCode)) {
                                    ToastUtils.showShort(LoginActivity.this, "授权失败");
                                } else {
                                    String openid = json.optString("openid");
                                    String nickname = json.optString("nickname");
                                    String headimgurl = json.optString("headimgurl");
                                    String sex = json.optString("sex");
                                    String city = json.optString("city");
                                    String province = json.optString("province");
                                    isBindThird("weChat", openid);
//                                    WXLogin(openid,nickname,headimgurl,sex,city,province);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    private void WXLogin(String openid, String nickname, String headimgurl, String sex, String city, String province) {
        SendRequest.WXLogin(openid, nickname, headimgurl, sex, city, province, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
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
                    ToastUtils.showShort(LoginActivity.this, response.getMsg());
                }
            }

        });
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
        }else {
            if (mSsoHandler != null) {
                mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * ****************************** 微博登录 **********************************
     */

    private SsoHandler mSsoHandler;

    private class SelfWbAuthListener implements WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            isBindThird("weiBo", token.getToken());
        }

        @Override
        public void cancel() {
            Toast.makeText(LoginActivity.this, "取消授权", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            Toast.makeText(LoginActivity.this, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void isBindThird(final String type, final String type_id) {
        SendRequest.isBindThird(type, type_id, new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(UserInfo response, int id) {
                if (response.getCode() == 200) {
                    MyApplication.getInstance().setUserInfo(response);
                    openActivity(MainActivity.class);
                    finish();
                } else if (response.getCode() == 500) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", type);
                    bundle.putString("type_id", type_id);
                    openActivity(RegisterActivity.class, bundle);
                } else {
                    ToastUtils.showShort(LoginActivity.this, response.getMsg());
                }
            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (loginBinding.passwordLoginLayout.passwordLoginView.isShown()) {
                loginBinding.passwordLoginLayout.passwordLoginView.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wxReceiver != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(wxReceiver);
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
