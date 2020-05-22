package com.baselibrary;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Looper;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.MsgCache;

public class BaseApplication extends Application {

    private static BaseApplication myApplication;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        handler = new Handler(Looper.getMainLooper());
    }

    public static BaseApplication getInstance() {
        return myApplication;
    }

    public static Handler getHandler() {
        return handler;
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

    public String getMetaData(String property) {
        String out = "";
        if (property.equals("version")) {
            try {
                PackageInfo pi = getInstance().getPackageManager().getPackageInfo(getInstance().getPackageName(), 0);
                return pi.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return out;
    }
}
