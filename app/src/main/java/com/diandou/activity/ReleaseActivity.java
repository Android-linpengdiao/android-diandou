package com.diandou.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baselibrary.Constants;
import com.baselibrary.manager.LoadingManager;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.FileUtils;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.LogUtil;
import com.baselibrary.utils.PermissionUtils;
import com.baselibrary.utils.SharedPreferencesUtils;
import com.baselibrary.utils.ToastUtils;
import com.diandou.NavData;
import com.diandou.R;
import com.diandou.databinding.ActivityReleaseBinding;
import com.media.MediaActivity;
import com.media.image.ImageModel;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Request;

public class ReleaseActivity extends BaseActivity implements AMapLocationListener, View.OnClickListener {

    private ActivityReleaseBinding binding;
    private static final int REQUEST_TYPE = 100;
    private static final int REQUEST_IMAGE = 200;
    private static final int REQUEST_CAMERA = 300;
    private static final int REQUEST_CROP = 400;
    private final static int REQUEST_LOCATION = 500;
    private static final int REQUEST_LOCATION_SETTING = 600;
    private NavData.DataBean dataBean;
    private String videoPath;
    private String coverPath;
    private String addr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_release);

        binding.back.setOnClickListener(this);
        binding.releaseConfirm.setOnClickListener(this);
        binding.cover.setOnClickListener(this);
        binding.videoType.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);

        videoPath = getIntent().getStringExtra("videoPath");
        coverPath = getIntent().getStringExtra("coverPath");

        GlideLoader.LoderLoadImage(this, coverPath, binding.cover, 10);

        if (checkPermissionsAll(PermissionUtils.LOCATION, REQUEST_LOCATION)) {
            //如果未开启定位服务，提示用户去开启
            if (CommonUtil.isLocationEnabled(ReleaseActivity.this)) {
                initLocation();
            } else {
                CommonUtil.toOpenGPS(ReleaseActivity.this, REQUEST_LOCATION_SETTING);
            }
        }
        initLocation();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment:
                binding.tvComment.setSelected(!binding.tvComment.isSelected());
                break;
            case R.id.back:
                finish();
                break;
            case R.id.cover:
                AlertDialog.Builder dialog = new AlertDialog.Builder(ReleaseActivity.this);
                dialog.setTitle("");
                dialog.setItems(R.array.media_list_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (checkPermissionsAll(PermissionUtils.STORAGE, REQUEST_IMAGE)) {
                                    Intent intent = new Intent(ReleaseActivity.this, MediaActivity.class);
                                    intent.putExtra("type", ImageModel.TYPE_IMAGE);
                                    intent.putExtra("number", 1);
                                    startActivityForResult(intent, REQUEST_IMAGE);
                                }
                                break;
                            case 1:
                                if (checkPermissionsAll(PermissionUtils.CAMERA, REQUEST_CAMERA)) {
                                    openCamera();
                                }
                                break;
                            case 2:

                                break;
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.video_type:
                Intent intent2 = new Intent(ReleaseActivity.this, VideoTypeActivity.class);
                startActivityForResult(intent2, REQUEST_TYPE);
                break;
            case R.id.release_confirm:
                String desc = binding.content.getText().toString().trim();
                if (CommonUtil.isBlank(desc)) {
                    ToastUtils.showShort(ReleaseActivity.this, "请输入你的描述");
                    return;
                }
                if (dataBean == null) {
                    ToastUtils.showShort(ReleaseActivity.this, "请选择类型");
                    return;
                }
                if (CommonUtil.isBlank(videoPath)) {
                    ToastUtils.showShort(ReleaseActivity.this, "视频地址无效");
                    return;
                }
                if (CommonUtil.isBlank(coverPath)) {
                    ToastUtils.showShort(ReleaseActivity.this, "封面地址无效，请重新选择");
                    return;
                }
                uploadFile(coverPath);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = true;
        switch (requestCode) {
            case REQUEST_IMAGE:
                for (int i = 0; i < PermissionUtils.storage.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    Intent intent = new Intent(ReleaseActivity.this, MediaActivity.class);
                    intent.putExtra("type", ImageModel.TYPE_IMAGE);
                    intent.putExtra("number", 1);
                    startActivityForResult(intent, REQUEST_IMAGE);
                } else {
                    PermissionUtils.openAppDetails(ReleaseActivity.this, "储存");
                }
                break;
            case REQUEST_CAMERA:
                for (int i = 0; i < PermissionUtils.camera.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    openCamera();
                } else {
                    PermissionUtils.openAppDetails(ReleaseActivity.this, "储存和相机");
                }
                break;
            case REQUEST_LOCATION:
                for (int i = 0; i < PermissionUtils.location.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    initLocation();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE:
                    if (data != null) {
                        String resultJson = data.getStringExtra("resultJson");
                        try {
                            JSONObject object = new JSONObject(resultJson);
                            if (object.optString("type").equals(ImageModel.TYPE_IMAGE)) {
                                JSONArray files = object.optJSONArray("imageList");
                                if (files.length() > 0) {
                                    coverPath = String.valueOf(files.get(0));
                                    clipPicture(coverPath);
                                    GlideLoader.LoderLoadImage(this, coverPath, binding.cover, 10);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CAMERA:
                    coverPath = String.valueOf(outputImage.getPath());
                    clipPicture(coverPath);
                    GlideLoader.LoderLoadImage(this, coverPath, binding.cover, 10);
                    break;
                case REQUEST_CROP:
                    if (null != data) {
                        coverPath = data.getStringExtra(ClipImageActivity.ARG_CLIP_PATH);
                        GlideLoader.LoderLoadImage(this, coverPath, binding.cover, 10);
                    }
                    break;
                case REQUEST_TYPE:
                    if (data != null) {
                        dataBean = (NavData.DataBean) data.getSerializableExtra("nav");
                        binding.videoType.setText(dataBean.getName());
                    }
                    break;
            }
        } else {
            switch (requestCode) {
                case REQUEST_LOCATION_SETTING:
                    if (CommonUtil.isLocationEnabled(ReleaseActivity.this)) {
                        initLocation();
                    }
                    break;
            }
        }
    }

    private File outputImage;

    private void openCamera() {
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = FileUtils.createTempFile(fileName);
        if (null != file && file.exists()) {
            outputImage = file;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //系统7.0打开相机权限处理
            if (Build.VERSION.SDK_INT >= 24) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri uri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private void clipPicture(String path) {
        Intent intent = new Intent(ReleaseActivity.this, ClipImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ClipImageActivity.ARG_PATH, path);
        bundle.putBoolean(ClipImageActivity.ARG_FIXED_RATIO, false);
        bundle.putFloat(ClipImageActivity.ARG_WIDTH, 1);
        bundle.putFloat(ClipImageActivity.ARG_HEIGHT, 1);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CROP);
    }

    private void uploadFile(String file) {
        SendRequest.fileUpload(file, file.substring(file.lastIndexOf("/") + 1), new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    String url = object.optString("data");
                    if (!CommonUtil.isBlank(url)) {
                        createSecurityToken(url);
                    } else {
                        ToastUtils.showShort(ReleaseActivity.this, "封面上传失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static final String TAG = "ReleaseActivity";

    private void createSecurityToken(final String coverUrl) {
        SendRequest.createSecurityToken(new StringCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                LoadingManager.showLoadingDialog(ReleaseActivity.this);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                LoadingManager.hideLoadingDialog(ReleaseActivity.this);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = jsonObject.optJSONObject("data");
                    if (jsonObject.optInt("code") == 200) {
                        String accessKeyId = object.optString("AccessKeyId");
                        String accessKeySecret = object.optString("AccessKeySecret");
                        String securityToken = object.optString("SecurityToken");
                        String expriedTime = object.optString("Expiration");
                        uploadVideo(accessKeyId, accessKeySecret, securityToken, coverUrl);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void uploadVideo(String AccessKeyId, String SecretKeyId, String SecurityToken, final String coverUrl) {

        String endpoint = "http://oss-cn-beijing.aliyuncs.com";

        //if null , default will be init
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // connction time out default 15s
        conf.setSocketTimeout(15 * 1000); // socket timeout，default 15s
        conf.setMaxConcurrentRequest(5); // synchronous request number，default 5
        conf.setMaxErrorRetry(2); // retry，default 2
        OSSLog.enableLog(); //write local log file ,path is SDCard_path\OSSLog\logs.csv

        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId, SecretKeyId, SecurityToken);

        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);

        // Construct an upload request
        PutObjectRequest put = new PutObjectRequest("diandou-test", videoPath.substring(videoPath.lastIndexOf("/") + 1), videoPath);

        // You can set progress callback during asynchronous upload
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                LogUtil.d(TAG, "currentSize: " + currentSize + " totalSize: " + totalSize);
                String temp = "" + currentSize * 100 / totalSize;
                LoadingManager.updateProgress(ReleaseActivity.this, String.format(Constants.str_updata_wait, temp + "%"));
            }
        });

        LoadingManager.showProgress(ReleaseActivity.this, String.format(Constants.str_updata_wait, "0%"));
        final OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LoadingManager.hideProgress(ReleaseActivity.this);
                String videoUrl = "http://" + request.getBucketName() + ".oss-cn-beijing.aliyuncs.com/" + request.getObjectKey();
                Log.i(TAG, "onSuccess: " + videoUrl);
                publishWork(coverUrl, videoUrl);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                LoadingManager.hideProgress(ReleaseActivity.this);
                ToastUtils.showShort(ReleaseActivity.this, "上传视频失败");
                // Request exception
                if (clientExcepion != null) {
                    // Local exception, such as a network exception
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // Service exception
                    LogUtil.d(TAG, "ErrorCode " + serviceException.getErrorCode());
                    LogUtil.d(TAG, "RequestId " + serviceException.getRequestId());
                    LogUtil.d(TAG, "HostId " + serviceException.getHostId());
                    LogUtil.d(TAG, "RawMessage " + serviceException.getRawMessage());
                }
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });
        LoadingManager.OnDismissListener(ReleaseActivity.this, new LoadingManager.OnDismissListener() {
            @Override
            public void onDismiss() {
                task.cancel(); // Cancel the task
            }
        });

        // task.cancel(); // Cancel the task
        // task.waitUntilFinished(); // Wait till the task is finished
    }

    private void publishWork(String coverUrl, String videoUrl) {
        SendRequest.publishWork(getUserInfo().getData().getId(), dataBean.getId(), dataBean.getName(), videoUrl, binding.content.getText().toString().trim(), coverUrl, addr, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showShort(ReleaseActivity.this, "发布失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 200) {
                        ToastUtils.showShort(ReleaseActivity.this, "发布成功");
                        finish();
                    } else {
                        ToastUtils.showShort(ReleaseActivity.this, "发布失败 :" + jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShort(ReleaseActivity.this, "发布失败");
                }
            }
        });
    }


    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void initLocation() {
        mlocationClient = new AMapLocationClient(ReleaseActivity.this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        // 启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                addr = amapLocation.getAddress();
                LogUtil.e(TAG, "onLocationChanged: " + amapLocation.getAddress());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtil.e(TAG, "AmapError: " + "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}