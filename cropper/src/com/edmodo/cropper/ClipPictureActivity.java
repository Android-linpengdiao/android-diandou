package com.edmodo.cropper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.baselibrary.manager.LoadingManager;
import com.baselibrary.utils.BitmapUtils;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.baselibrary.utils.FileUtils;

public class ClipPictureActivity extends AppCompatActivity {

    private static final String TAG = "ClipPictureActivity";

    public static final String ARG_PATH = "path";
    public static final String ARG_TYPE = "type";
    public static final String ARG_FIXED_RATIO = "fixed_ratio";
    public static final String ARG_WIDTH = "width";
    public static final String ARG_HEIGHT = "height";

    private CropImageView cropImageView;
    private TextView confirm;
    private TextView cancel;
    private View bottomView;
    private String filePath;
    private int type; //
    private boolean ratio;

    private float imgWidth;
    private float imgHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_picture);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        confirm = (TextView) findViewById(R.id.confirm);
        cancel = (TextView) findViewById(R.id.cancel);
        bottomView = findViewById(R.id.bottomView);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            filePath = bundle.getString(ARG_PATH);
            ratio = bundle.getBoolean(ARG_FIXED_RATIO);
            imgWidth = bundle.getFloat(ARG_WIDTH);
            imgHeight = bundle.getFloat(ARG_HEIGHT);
            cropImageView.setFixedAspectRatio(ratio);
            GlideLoader.LoderClipImage(getApplication(), filePath, cropImageView);
            showPic(filePath);
        } else {
            finish();
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingManager.showLoadingDialog(ClipPictureActivity.this);
                final Bitmap croppedImage = cropImageView.getCroppedImage();
                filePath = FileUtils.saveBitmap(croppedImage, 300, 300, 200);
                LoadingManager.hideLoadingDialog(ClipPictureActivity.this);
                if (filePath != null) {
                    Intent intent = new Intent();
                    intent.putExtra("clipPicture", filePath);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showLong(getApplication(), "裁剪失败");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showPic(final String picPath) {
        if (!BitmapUtils.getEffective(picPath)) {
            ToastUtils.showShort(getApplication(), "图片已损坏");
            return;
        }
        if (!CommonUtil.isBlank(picPath)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoadingManager.showLoadingDialog(ClipPictureActivity.this);
                    final Bitmap loadBitmap = GlideLoader.load(getApplication(), picPath);
                    if (loadBitmap != null) {
                        if (ratio) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    float width = loadBitmap.getWidth();
                                    float height = loadBitmap.getHeight();
                                    if (imgWidth > imgHeight) {
                                        float scaleH5 = imgWidth / imgHeight;
                                        float scale = width / height;
                                        if (scaleH5 >= scale) {
                                            cropImageView.setAspectRatio((int) width, (int) (width * imgHeight / imgWidth));
                                        } else {
                                            cropImageView.setAspectRatio((int) width, (int) height);
                                        }
                                    }else if (imgWidth == imgHeight) {
                                        if (width >= height) {
                                            cropImageView.setAspectRatio((int) height, (int) height);
                                        } else {
                                            cropImageView.setAspectRatio((int) width, (int) width);
                                        }
                                    } else {
                                        float scaleH5 = imgWidth / imgHeight;
                                        float scale = width / height;
                                        if (scaleH5 >= scale) {
                                            cropImageView.setAspectRatio((int) width, (int) height);
                                        } else {
                                            cropImageView.setAspectRatio((int) (height * imgWidth / imgHeight), (int) height);
                                        }
                                    }
                                    cropImageView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            cropImageView.invalidate();
                                        }
                                    });

                                }
                            });
                        }
                        bottomView.post(new Runnable() {
                            @Override
                            public void run() {
                                bottomView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    LoadingManager.hideLoadingDialog(ClipPictureActivity.this);
                }
            }).start();
        }
    }

}
