package com.diandou.manager;

import android.app.Activity;

import com.sina.weibo.sdk.share.WbShareHandler;

public class WbShareManager {

    public void registerApp(Activity activity){
        WbShareHandler shareHandler = new WbShareHandler(activity);
        shareHandler.registerApp();
    }

}
