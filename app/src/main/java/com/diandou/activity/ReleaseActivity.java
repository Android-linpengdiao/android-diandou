package com.diandou.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClient;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.SvideoInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;
import com.baselibrary.Constants;
import com.baselibrary.manager.LoadingManager;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.FileUtils;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.LogUtil;
import com.baselibrary.utils.PermissionUtils;
import com.baselibrary.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cjt2325.cameralibrary.CameraActivity;
import com.diandou.NavData;
import com.diandou.R;
import com.diandou.databinding.ActivityReleaseBinding;
import com.edmodo.cropper.ClipPictureActivity;
import com.media.MediaActivity;
import com.media.image.ImageModel;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.utils.APIUrls;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ReleaseActivity extends BaseActivity implements View.OnClickListener {

    private ActivityReleaseBinding binding;
    private static final int REQUEST_TYPE = 100;
    private static final int REQUEST_IMAGE = 200;
    private static final int REQUEST_CAMERA = 300;
    private static final int REQUEST_CROP = 400;
    private NavData.DataBean dataBean;
    private String videoPath;
    private String coverPath;

    private VODSVideoUploadClient vodsVideoUploadClient;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_release);

        binding.back.setOnClickListener(this);
        binding.releaseConfirm.setOnClickListener(this);
        binding.cover.setOnClickListener(this);
        binding.videoType.setOnClickListener(this);

        videoPath = getIntent().getStringExtra("videoPath");
        coverPath = getIntent().getStringExtra("coverPath");

        //阿里云视频上传
        client = new OkHttpClient.Builder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return "demo-vod.cn-shanghai.aliyuncs.com".equals(hostname);
                    }
                })
                .build();
        vodsVideoUploadClient = new VODSVideoUploadClientImpl(getApplicationContext());
        vodsVideoUploadClient.init();

//        uploadVideo("");

//        try {
//            getVodUploadInfo();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        GlideLoader.LoderLoadImage(this, coverPath, binding.cover, 10);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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


    private void publishWork(String coverUrl, String videoUrl) {
        SendRequest.publishWork(getUserInfo().getData().getId(), dataBean.getId(), dataBean.getName(), videoUrl, binding.content.getText().toString().trim(), coverUrl, new StringCallback() {
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
                        uploadVideo(url);
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

    private void uploadVideo(final String coverUrl) {
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
                        String returnUrl = object.optString("returnUrl");
                        String requestID = null;
                        startVodsVideoUpload(accessKeyId, accessKeySecret, securityToken, expriedTime, requestID, returnUrl, coverUrl);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void getVodUploadInfo() throws Exception {

        long time = System.currentTimeMillis();
        Request request = new Request.Builder()
                .url(APIUrls.URL_STORAGE_CreateSecurityToken + time)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    LogUtil.d(TAG, "onResponse: json " + jsonObject.toString());
                    JSONObject object = jsonObject.optJSONObject("data");
                    if (!CommonUtil.isBlank(object)) {
                        String accessKeyId = object.optString("AccessKeyId");
                        String accessKeySecret = object.optString("AccessKeySecret");
                        String securityToken = object.optString("SecurityToken");
                        String expriedTime = object.optString("Expiration");
                        String returnUrl = object.optString("returnUrl");
                        String requestID = null;
                        startVodsVideoUpload(accessKeyId, accessKeySecret, securityToken, expriedTime, requestID, returnUrl, null);
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startVodsVideoUpload(String accessKeyId, String accessKeySecret, String securityToken, String expriedTime, String requestID, final String returnUrl, final String coverUrl) {
        Log.i(TAG, "startVodsVideoUpload: videoPath = " + videoPath);
        Log.i(TAG, "startVodsVideoUpload: coverPath = " + coverPath);
        Log.i(TAG, "startVodsVideoUpload: accessKeyId = " + accessKeyId);
        Log.i(TAG, "startVodsVideoUpload: accessKeySecret = " + accessKeySecret);
        Log.i(TAG, "startVodsVideoUpload: securityToken = " + securityToken);
        Log.i(TAG, "startVodsVideoUpload: expriedTime = " + expriedTime);
        Log.i(TAG, "startVodsVideoUpload: requestID = " + requestID);
        Log.i(TAG, "startVodsVideoUpload: returnUrl = " + returnUrl);
        //参数请确保存在，如不存在SDK内部将会直接将错误throw Exception
        // 文件路径保证存在之外因为Android 6.0之后需要动态获取权限，请开发者自行实现获取"文件读写权限".
        VodHttpClientConfig vodHttpClientConfig = new VodHttpClientConfig.Builder()
                .setMaxRetryCount(2)
                .setConnectionTimeout(15 * 1000)
                .setSocketTimeout(15 * 1000)
                .build();

        SvideoInfo svideoInfo = new SvideoInfo();
        svideoInfo.setTitle(new File(videoPath).getName());
        svideoInfo.setDesc("");
        svideoInfo.setCateId(1);
        VodSessionCreateInfo vodSessionCreateInfo = new VodSessionCreateInfo.Builder()
                .setImagePath(coverPath)
                .setVideoPath(videoPath)
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setSecurityToken(securityToken)
                .setRequestID(requestID)
                .setExpriedTime(expriedTime)
                .setIsTranscode(true)
                .setSvideoInfo(svideoInfo)
                .setPartSize(500 * 1024)
                .setVodHttpClientConfig(vodHttpClientConfig)
                .build();
        LoadingManager.showProgress(ReleaseActivity.this, String.format(Constants.str_updata_wait, "0%"));
        vodsVideoUploadClient.uploadWithVideoAndImg(vodSessionCreateInfo, new VODSVideoUploadCallback() {
            @Override
            public void onUploadSucceed(String videoId, final String imageUrl) {
                LogUtil.i(TAG, "onUploadSucceed" + "videoId: " + videoId + "  imageUrl " + imageUrl);
                LoadingManager.hideProgress(ReleaseActivity.this);
                publishWork(coverUrl, returnUrl + videoId + ".mp4");
            }

            @Override
            public void onUploadFailed(String code, String message) {
                LogUtil.i(TAG, "onUploadFailed =  code = " + code + "  ;  message = " + message);
                LoadingManager.hideProgress(ReleaseActivity.this);
                ToastUtils.showShort(ReleaseActivity.this, "上传视频失败");
            }

            @Override
            public void onUploadProgress(long uploadedSize, long totalSize) {
                LogUtil.i(TAG, "onUploadProgress" + uploadedSize * 100 / totalSize);
                String temp = "" + uploadedSize * 100 / totalSize;
                LoadingManager.updateProgress(ReleaseActivity.this, String.format(Constants.str_updata_wait, temp + "%"));
            }

            @Override
            public void onSTSTokenExpried() {
                //STS token过期之后刷新STStoken，如正在上传将会断点续传
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUploadRetry(String code, String message) {
                //上传重试的提醒
            }

            @Override
            public void onUploadRetryResume() {
                //上传重试成功的回调.告知用户重试成功
            }
        });
    }

}