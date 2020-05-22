package com.diandou.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.baselibrary.utils.LogUtil;
import com.diandou.utils.Config;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	LogUtil.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        this.api= WXAPIFactory.createWXAPI(this, Config.WECHAT_APP_ID, false);
		this.api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		LogUtil.i(TAG, "onNewIntent");
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp arg0) {
		LogUtil.d(TAG, "onPayFinish, errCode = " + arg0.errCode);
		if(arg0 instanceof PayResp){
			PayResp resp=(PayResp)arg0;
			LocalBroadcastManager broadcastManager= LocalBroadcastManager.getInstance(getApplicationContext());
			boolean result;
			if(resp.errCode!=0){
//				ToastUtils.showShort(getApplicationContext(), "支付失败");
				result=false;
			}else{
				result=true;
			}
			Intent intent=new Intent(Config.wechat_pay_result);
			intent.putExtra("result", result);
			broadcastManager.sendBroadcast(intent);
			finish();
		}else{
			finish();
		}
	}
}
