package com.diandou.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.LogUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.manager.WXManager;
import com.diandou.utils.Config;
import com.okhttp.callbacks.StringCallback;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import okhttp3.Call;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXEntryActivity";
	private IWXAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.api= WXAPIFactory.createWXAPI(this, Config.WECHAT_APP_ID, false);
		this.api.handleIntent(getIntent(), this);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		this.api.handleIntent(getIntent(), this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onReq(BaseReq arg0) {
	}

	@Override
	public void onResp(BaseResp arg0) {
		LogUtil.d(TAG, "onResp: ");
//		LogUtil.i("", arg0.getType() + "");
//		LogUtil.i("", arg0.openId);
		// getType 是 2 为分享动作
		if(arg0 instanceof SendAuth.Resp){
			SendAuth.Resp resp=(SendAuth.Resp)arg0;
			if(resp.errCode!=0){
				ToastUtils.showShort(getApplicationContext(), "授权取消");
				finish();
			}else{
				WXManager.setCode(resp.code);
				WXManager.fetchToken(new StringCallback() {
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
								ToastUtils.showShort(getApplicationContext(), "获取授权信息失败");
							} else {
								WXManager.setToken(json.optString("access_token"));
								WXManager.setOpenid(json.optString("openid"));
								LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent(Config.wechat_get_token_success));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						finish();
					}
				});
			}
		}else{
			finish();
		}
	}

}
