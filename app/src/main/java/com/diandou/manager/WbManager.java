package com.diandou.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.diandou.MyApplication;
import com.diandou.R;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;

public class WbManager {

    public static final int SHARE_CLIENT = 1;
    private static int mShareType = SHARE_CLIENT;

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    public static void sendMultiMessage(WbShareHandler shareHandler, boolean hasText, boolean hasImage) {

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
        shareHandler.shareMessage(weiboMessage, mShareType == SHARE_CLIENT);

    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private static TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "我正在使用微博客户端发博器分享文字";
        textObject.actionUrl = "http://www.baidu.com";
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private static ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.ic_app);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

}
