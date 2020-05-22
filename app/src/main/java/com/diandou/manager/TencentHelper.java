package com.diandou.manager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.FileUtils;
import com.baselibrary.utils.LogUtil;
import com.diandou.MyApplication;
import com.diandou.R;
import com.diandou.utils.Config;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;

public class TencentHelper {

    private static final String TAG = "TencentHelper";

    private static final String SP_TENCENT_INFO = "third_tencent_info";
    private static final String SP_TENCENT_OPENID = "third_tencent_openid";
    private static final String SP_TENCENT_TOKEN = "third_tencent_token";
    private static final String SP_TENCENT_EXPIRES = "third_tencent_expires";
    private static final String SP_TENCENT_USERINFO = "third_tencent_userinfo";

    public static final String TENCENT_APPID = Config.QQ_APP_ID;

    private static Tencent tencent;

    public synchronized static Tencent getTencent() {
        if (CommonUtil.isBlank(TENCENT_APPID)) {
            return null;
        }
        if (null == tencent) {
            tencent = Tencent.createInstance(TENCENT_APPID, MyApplication.getInstance());
        }
        return tencent;
    }

    public static void auth(final Activity activity, final IUiListener iUiListener) {
        final Tencent tencent = getTencent();
        if (null == tencent) {
            LogUtil.i(TencentHelper.class.getSimpleName(), "tencent is null");
            return;
        }

        tencent.login(activity, "all", new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Object arg0) {
                Log.d(TAG, "onComplete: ");
                try {
                    JSONObject jsonObject = (JSONObject) arg0;
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
                    if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                            && !TextUtils.isEmpty(openId)) {
                        SharedPreferences sp = activity.getSharedPreferences(SP_TENCENT_INFO, Activity.MODE_PRIVATE);
                        sp.edit().putString(SP_TENCENT_OPENID, openId).commit();
                        sp.edit().putString(SP_TENCENT_TOKEN, token).commit();
                        sp.edit().putString(SP_TENCENT_EXPIRES, expires).commit();

                        tencent.setAccessToken(token, expires);
                        tencent.setOpenId(openId);
                    }
                    if (null != iUiListener) {
                        iUiListener.onComplete(arg0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        });
    }

    public static void refreshUserInfo(final Activity activity, final IUiListener l) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("请稍后...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        UserInfo userInfo = new UserInfo(activity.getApplicationContext(), tencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onComplete(Object arg0) {
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                SharedPreferences sp = activity.getSharedPreferences(SP_TENCENT_INFO, Activity.MODE_PRIVATE);
                sp.edit().putString(SP_TENCENT_USERINFO, arg0.toString()).commit();
                if (null != l) {
                    l.onComplete(arg0);
                }
            }

            @Override
            public void onCancel() {
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    public static String getUserInfoToken() {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_TENCENT_INFO,
                Activity.MODE_PRIVATE);
        return sp.getString(SP_TENCENT_USERINFO, "");
    }

    public static String getToken() {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_TENCENT_INFO,
                Activity.MODE_PRIVATE);
        return sp.getString(SP_TENCENT_TOKEN, "");
    }

    public static String getOpenId() {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_TENCENT_INFO,
                Activity.MODE_PRIVATE);
        LogUtil.i("getOpenId", sp.getAll().toString());
        return sp.getString(SP_TENCENT_OPENID, "");
    }

    public static String getExpires() {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_TENCENT_INFO,
                Activity.MODE_PRIVATE);
        return sp.getString(SP_TENCENT_EXPIRES, "");
    }

    public static void reset() {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_TENCENT_INFO,
                Activity.MODE_PRIVATE);
        sp.edit().clear().apply();
        Tencent t = getTencent();
        if (null != t) {
            getTencent().logout(MyApplication.getInstance());
            tencent = null;
        }
    }

    public static void shareToQQ(Activity activity, String shareUrl, String shareTitle, String shareSummary, String imageUrl, IUiListener l) {
        Tencent mTencent = getTencent();
        if (null == mTencent) {
            LogUtil.i(TencentHelper.class.getSimpleName(), "tencent is null");
            return;
        }
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareUrl);
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, shareTitle);

        imageUrl = null;
        //分享的图片URL
        if (!CommonUtil.isBlank(imageUrl)){
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        }else {
            Resources res = MyApplication.getInstance().getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.ic_media_play);
            String sharePath = FileUtils.saveBitmap(bitmap);
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, sharePath);
        }
        //分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareSummary);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
        //标识该消息的来源应用，值为应用名称+AppId。
        bundle.putString("appSource", "星期几" + TENCENT_APPID);
        mTencent.shareToQQ(activity, bundle, new IUiListener() {
            @Override
            public void onComplete(Object o) {
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    public static void shareToQzone(Activity activity, String shareUrl, String shareTitle, String shareSummary, String imageUrl, IUiListener l) {
        Tencent mTencent = getTencent();
        if (null == mTencent) {
            LogUtil.i(TencentHelper.class.getSimpleName(), "tencent is null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareUrl);
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareTitle);

        imageUrl = null;
        //分享的图片URL
        ArrayList<String> imgUrls = new ArrayList<String>();
        imgUrls.add("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2534506313,1688529724&fm=26&gp=0.jpg");
        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrls);
        //分享的消息摘要，最长50个字
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareSummary);
        bundle.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
        //标识该消息的来源应用，值为应用名称+AppId。
        bundle.putString("appSource", "星期几" + TENCENT_APPID);
        mTencent.shareToQzone(activity, bundle, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.i(TAG, "onComplete: ");
            }

            @Override
            public void onError(UiError uiError) {
                Log.i(TAG, "onError: errorMessage = "+uiError.errorMessage);
                Log.i(TAG, "onError: errorCode = "+uiError.errorCode);
                Log.i(TAG, "onError: errorDetail = "+uiError.errorDetail);
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: ");
            }
        });
    }

}
