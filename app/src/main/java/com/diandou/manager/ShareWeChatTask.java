package com.diandou.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.baselibrary.utils.CommonUtil;
import com.diandou.R;
import com.diandou.utils.Config;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by daniel on 15-8-7.
 */
public class ShareWeChatTask extends AsyncTask<Object, Void, Void> {

    private Activity activity = null;

    private IWXAPI iwxapi=null;

    public ShareWeChatTask(Activity activity) {
        this.activity = activity;
        this.iwxapi= WXAPIFactory.createWXAPI(activity.getApplicationContext(), Config.WECHAT_APP_ID, false);
        this.iwxapi.registerApp(Config.WECHAT_APP_ID);
    }

    @Override
    protected Void doInBackground(Object... params) {
        ShareEntity entity = (ShareEntity) params[0];
        int scene = (Integer) params[1];

        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
//        if (!CommonUtil.isBlank(entity.thumbnailUrl)) {
//            try {
//                URL url = new URL(entity.thumbnailUrl);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                conn.setReadTimeout(10 * 1000);
//                conn.setConnectTimeout(10 * 1000);
//                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = BitmapUtils.calculateInSampleSize(options, 50, 50);
//                bitmap = BitmapFactory.decodeStream(bis, null, options);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = entity.webpageUrl;

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = webpageObject;
        mediaMessage.title = entity.title;
        if (CommonUtil.isBlank(entity.desc)) {
            mediaMessage.description = "点击查看详细内容…";
        } else {
            mediaMessage.description = entity.desc;
        }
        mediaMessage.setThumbImage(bitmap);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = mediaMessage;
        req.scene = scene;
        req.transaction = String.valueOf(System.currentTimeMillis());

        if (!isCancelled()) {
            iwxapi.sendReq(req);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

}

