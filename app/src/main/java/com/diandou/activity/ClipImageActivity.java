package com.diandou.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baselibrary.utils.BitmapUtils;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.FileUtils;
import com.baselibrary.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.diandou.R;
import com.diandou.databinding.ActivityClipImageBinding;
import com.edmodo.cropper.CropImageView;

public class ClipImageActivity extends BaseActivity {

    private static final String TAG = "ClipPictureActivity";
    private ActivityClipImageBinding mBinding;

    public static final String ARG_PATH = "path";
    public static final String ARG_TYPE = "type";
    public static final String ARG_FIXED_RATIO = "fixed_ratio";
    public static final String ARG_WIDTH = "width";
    public static final String ARG_HEIGHT = "height";
    public static final String ARG_CLIP_PATH = "clip_path";

    private CropImageView cropImageView;
    private View bottomView;
    private String filePath;
    private boolean ratio;

    private float imgWidth;
    private float imgHeight;
    private int angle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_clip_image);

        cropImageView = mBinding.cropImageView;
        bottomView = mBinding.bottomView;
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            filePath = bundle.getString(ARG_PATH);
            ratio = bundle.getBoolean(ARG_FIXED_RATIO);
            imgWidth = bundle.getFloat(ARG_WIDTH);
            imgHeight = bundle.getFloat(ARG_HEIGHT);
            cropImageView.setFixedAspectRatio(ratio);

            showPic(filePath);
        } else {
            finish();
        }
        mBinding.ivRotaing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angle -= 90;
                showPic(filePath);
                mBinding.restore.setTextColor(getResources().getColor(com.edmodo.cropper.R.color.white));
            }
        });
        mBinding.restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angle = 0;
                showPic(filePath);
                mBinding.restore.setTextColor(Color.parseColor("#5E5E5E"));
            }
        });
        mBinding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                final Bitmap croppedImage = cropImageView.getCroppedImage();
                filePath = FileUtils.saveBitmap(croppedImage, 300, 300, 200);
                hideLoadingDialog();
                if (filePath != null) {
                    Intent intent = new Intent();
                    intent.putExtra(ARG_CLIP_PATH, filePath);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showShort(ClipImageActivity.this,"裁剪失败");
                }
            }
        });
        mBinding.cancel.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
        }
        if (returnBm != null) {
            returnBm.recycle();
        }
    }

    private Bitmap bitmap;
    private void showPic(final String picPath) {
        Log.e(TAG, "showPic: angle="+angle );
        if (!BitmapUtils.getEffective(picPath)) {
            ToastUtils.showShort(getApplication(), "图片已损坏");
            return;
        }
        if (!CommonUtil.isBlank(picPath)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    showLoadingDialog();
                    Glide.with(getApplication()).asBitmap().load(picPath).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@android.support.annotation.NonNull Bitmap resource, @android.support.annotation.Nullable com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {
                            if (resource != null) {
                                if (bitmap != null) {
                                    bitmap.recycle();
                                }
                                if (returnBm != null) {
                                    returnBm.recycle();
                                }
                                bitmap = rotaingImageView(angle, resource);
                                if (ratio) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Glide.with(ClipImageActivity.this)
                                                    .load(bitmap)
                                                    .placeholder(R.drawable.placeholder)
                                                    .error(R.drawable.placeholder)
                                                    .into(cropImageView);
                                            float width = bitmap.getWidth();
                                            float height = bitmap.getHeight();
                                            if (imgWidth > imgHeight) {
                                                float scaleH5 = imgWidth / imgHeight;
                                                float scale = width / height;
                                                if (scaleH5 >= scale) {
                                                    cropImageView.setAspectRatio((int) width, (int) (width * imgHeight / imgWidth));
                                                } else {
                                                    cropImageView.setAspectRatio((int) width, (int) height);
                                                }
                                            } else if (imgWidth == imgHeight) {
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
                                }else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Glide.with(ClipImageActivity.this)
                                                    .load(bitmap)
                                                    .placeholder(R.drawable.placeholder)
                                                    .error(R.drawable.placeholder)
                                                    .into(cropImageView);
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
                        }
                    });
                    hideLoadingDialog();
                }
            }).start();
        }
    }

    private Bitmap returnBm;
    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        return returnBm;
    }

}
