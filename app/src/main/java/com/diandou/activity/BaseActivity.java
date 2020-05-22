package com.diandou.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.baselibrary.Constants;
import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.MsgCache;
import com.baselibrary.utils.StatusBarUtil;
import com.diandou.R;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.io.File;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDarkTheme(true);
    }

    public void setStatusBarDarkTheme(boolean dark) {
        if (!StatusBarUtil.setStatusBarDarkTheme(this, dark)) {
            StatusBarUtil.setStatusBarColor(this, dark ? R.color.black : R.color.white);
        }
    }

    // 5.0版本以上
    @SuppressLint("NewApi")
    public void setStatusBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            if (findViewById(R.id.status_bar) != null) {
                findViewById(R.id.status_bar).setVisibility(View.VISIBLE);
                int statusBarHeight = CommonUtil.getStatusBarHeight(getApplication());
                findViewById(R.id.status_bar).getLayoutParams().height = statusBarHeight;
            }
        }
    }

    public void openActivity(Class<?> mClass) {
        openActivity(mClass, null);
    }

    public void openActivity(Class<?> mClass, Bundle mBundle) {
        Intent intent = new Intent(this, mClass);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        startActivity(intent);
    }

    public void setUserInfo(UserInfo userInfo) {
        MsgCache.get(this).put(Constants.USER_INFO, userInfo);
    }

    public UserInfo getUserInfo() {
        UserInfo userinfo = (UserInfo) MsgCache.get(this).getAsObject(Constants.USER_INFO);
        if (!CommonUtil.isBlank(userinfo)) {
            return userinfo;
        }
        return new UserInfo();
    }
}
