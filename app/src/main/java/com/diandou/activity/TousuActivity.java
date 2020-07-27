package com.diandou.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.baselibrary.Constants;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.baselibrary.view.ProgressView;
import com.diandou.R;
import com.diandou.adapter.ImageAdapter;
import com.diandou.databinding.ActivityTousuBinding;
import com.diandou.model.BaseData;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.OnClickListener;
import com.media.MediaActivity;
import com.media.image.ImageModel;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;
import com.okhttp.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

public class TousuActivity extends BaseActivity implements View.OnClickListener {

    private ActivityTousuBinding binding;
    private ImageAdapter imageAdapter;
    private List<String> images = new ArrayList<>();
    private int id;

    private final static int REQUEST_MEDIA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tousu);

        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getInt("id");
        } else {
            finish();
        }

        binding.back.setOnClickListener(this);

        images.add("");
        imageAdapter = new ImageAdapter(this);
        binding.imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 10));
        binding.imageRecyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.imageRecyclerView.setAdapter(imageAdapter);
        imageAdapter.refreshData(images);
        imageAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (object instanceof Integer && (int) object == 0) {
                    if (imageAdapter.getList().size() <= 9) {
                        Intent intent = new Intent(TousuActivity.this, MediaActivity.class);
                        intent.putExtra("maxstr", 10 - imageAdapter.getList().size());
                        intent.putExtra("type", ImageModel.TYPE_IMAGE);
                        intent.putExtra("compressor", false);
                        startActivityForResult(intent, REQUEST_MEDIA);
                    } else {
                        ToastUtils.showShort(TousuActivity.this, "最多选五张");
                    }
                }

            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });
        binding.tvonfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = binding.content.getText().toString().trim();
                if (!CommonUtil.isBlank(content)) {
                    if (images.size() > 1) {
                        uploadFiles(content);
                    } else {
                        centerTip(content, "");
                    }
                } else {
                    ToastUtils.showShort(TousuActivity.this, "请输入投诉内容");
                }
            }
        });
    }

    private void centerTip(String content, String img) {
        SendRequest.centerTip(getUserInfo().getData().getId(), id, content, img, new GenericsCallback<BaseData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseData response, int id) {
                if (response.getCode() == 200) {
                    ToastUtils.showShort(TousuActivity.this, "提交成功");
                    finish();
                } else {
                    ToastUtils.showShort(TousuActivity.this, response.getMsg());
                }
            }
        });
    }

    private ProgressView progressView;
    private int progress = 0;

    List<String> list = new ArrayList<>();

    private void uploadFiles(final String content) {
        list.addAll(images.subList(1, images.size()));
        progressView = new ProgressView(TousuActivity.this, R.style.ProgressDialogTheme);
        progress = 0;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressView.show();
                progressView.updateProgress((String.format(Constants.str_updata_wait, progress + "/" + list.size())));
            }
        });
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            OkHttpUtils.post()
                    .addFile("file", list.get(i).substring(list.get(i).lastIndexOf("/") + 1), new File(list.get(i)))
                    .url(APIUrls.url_fileUpload)
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onBefore(Request request, int id) {

                        }

                        @Override
                        public void onAfter(int id) {

                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                progress += 1;
                                JSONObject object = new JSONObject(response);
                                String url = object.optString("data");
                                list.set(finalI, url);
                                progressView.updateProgress((String.format(Constants.str_updata_wait, progress + "/" + list.size())));
                                if (progress == list.size()) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (progressView != null) {
                                                        progressView.dismiss();
                                                    }
                                                }
                                            });
                                            centerTip(content, TextUtils.join(",", list));
                                        }
                                    }, 500);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            super.inProgress(progress, total, id);
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_MEDIA:
                    if (data != null) {
                        String resultJson = data.getStringExtra("resultJson");
                        try {
                            JSONObject object = new JSONObject(resultJson);
                            if (object.optString("type").equals(ImageModel.TYPE_IMAGE)) {
                                JSONArray files = object.optJSONArray("imageList");
                                if (files.length() > 0) {
                                    for (int i = 0; i < files.length(); i++) {
                                        images.add(String.valueOf(files.get(i)));
                                    }
                                    binding.imageRecyclerView.setVisibility(View.VISIBLE);
                                    imageAdapter.refreshData(images);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}