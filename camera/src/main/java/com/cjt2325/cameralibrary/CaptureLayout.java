package com.cjt2325.cameralibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.cjt2325.cameralibrary.listener.CaptureListener;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ReturnListener;
import com.cjt2325.cameralibrary.listener.TypeListener;


/**
 * =====================================
 * 作    者: 陈嘉桐 445263848@qq.com
 * 版    本：1.0.4
 * 创建日期：2017/4/26
 * 描    述：集成各个控件的布局
 * =====================================
 */

public class CaptureLayout extends FrameLayout {

    private CaptureListener captureLisenter;    //拍照按钮监听
    private TypeListener typeLisenter;          //拍照或录制后接结果按钮监听
    private ReturnListener returnListener;      //退出按钮监听
    private ClickListener leftClickListener;    //左边按钮监听
    private ClickListener rightClickListener;   //右边按钮监听

    public void setTypeLisenter(TypeListener typeLisenter) {
        this.typeLisenter = typeLisenter;
    }

    public void setCaptureLisenter(CaptureListener captureLisenter) {
        this.captureLisenter = captureLisenter;
    }

    public void setReturnLisenter(ReturnListener returnListener) {
        this.returnListener = returnListener;
    }

    private ProgressBar progressBar;
    private ImageView btn_capture;      //拍照按钮
    //    private CaptureButton btn_capture;      //拍照按钮
    private TypeButton btn_confirm;         //确认按钮
    private TypeButton btn_cancel;          //取消按钮
    private ReturnButton btn_return;        //返回按钮
    private ImageView iv_custom_left;            //左边自定义按钮
    private ImageView iv_custom_right;            //右边自定义按钮
    private TextView txt_tip;               //提示文本
    private TextView txt_time;               //录制时间

    private int layout_width;
    private int layout_height;
    private int button_size;
    private int iconLeft = 0;
    private int iconRight = 0;

    private boolean isFirst = true;

    private Context context;

    public CaptureLayout(Context context) {
        this(context, null);
        this.context = context;
    }

    public CaptureLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public CaptureLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layout_width = outMetrics.widthPixels;
        } else {
            layout_width = outMetrics.widthPixels / 2;
        }
        button_size = (int) (layout_width / 4.5f);
        layout_height = button_size + (button_size / 5) * 2 + 100;

        initView();
        initEvent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(layout_width, layout_height);
    }

    public void initEvent() {
        //默认Typebutton为隐藏
        iv_custom_right.setVisibility(GONE);
        btn_cancel.setVisibility(GONE);
        btn_confirm.setVisibility(GONE);
    }

    public void startTypeBtnAnimator() {
        //拍照录制结果后的动画
        if (this.iconLeft != 0)
            iv_custom_left.setVisibility(GONE);
        else
            btn_return.setVisibility(GONE);
        if (this.iconRight != 0)
            iv_custom_right.setVisibility(GONE);
//        btn_capture.setVisibility(GONE);
//        btn_cancel.setVisibility(VISIBLE);
//        btn_confirm.setVisibility(VISIBLE);
        btn_cancel.setClickable(false);
        btn_confirm.setClickable(false);
        btn_capture.setClickable(false);
        ObjectAnimator animator_cancel = ObjectAnimator.ofFloat(btn_cancel, "translationX", layout_width / 4, 0);
        ObjectAnimator animator_confirm = ObjectAnimator.ofFloat(btn_confirm, "translationX", -layout_width / 4, 0);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator_cancel, animator_confirm);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                btn_cancel.setClickable(true);
//                btn_confirm.setClickable(true);
                btn_capture.setClickable(true);

                if (typeLisenter != null) {
                    typeLisenter.confirm();
                }
                startAlphaAnimation();
            }
        });
        set.setDuration(200);
        set.start();
    }

    private static final String TAG = "CaptureLayout";

    private void initView() {
        setWillNotDraw(false);
        //拍照按钮

        btn_capture = new ImageView(getContext());
        btn_capture.setImageResource(R.drawable.capture);
        LayoutParams btn_capture_param = new LayoutParams(CommonUtil.dip2px(context, 82), CommonUtil.dip2px(context, 82));
        btn_capture_param.gravity = Gravity.CENTER_VERTICAL;
        btn_capture_param.setMargins((layout_width / 2) - CommonUtil.dip2px(context, 82) / 2, 0, 0, 0);
        btn_capture.setLayoutParams(btn_capture_param);
        btn_capture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_capture.setSelected(!btn_capture.isSelected());
                if (btn_capture.isSelected()) {
                    if (captureLisenter != null) {
                        captureLisenter.recordStart();
                        startUpdateTimer();
                    }
                    startAlphaAnimation();
                } else {
                    if (captureLisenter != null) {
                        captureLisenter.recordEnd(0);
                        stopUpdateTimer();
                    }
                    startAlphaAnimation();
                    startTypeBtnAnimator();
                }
            }
        });

//        btn_capture = new CaptureButton(getContext(), button_size);
//        LayoutParams btn_capture_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        btn_capture_param.gravity = Gravity.CENTER;
//        btn_capture.setLayoutParams(btn_capture_param);
//        btn_capture.setCaptureLisenter(new CaptureListener() {
//            @Override
//            public void takePictures() {
//                if (captureLisenter != null) {
//                    captureLisenter.takePictures();
//                }
//            }
//
//            @Override
//            public void recordShort(long time) {
//                if (captureLisenter != null) {
//                    captureLisenter.recordShort(time);
//                }
//                startAlphaAnimation();
//            }
//
//            @Override
//            public void recordStart() {
//                if (captureLisenter != null) {
//                    captureLisenter.recordStart();
//                }
//                startAlphaAnimation();
//            }
//
//            @Override
//            public void recordEnd(long time) {
//                if (captureLisenter != null) {
//                    captureLisenter.recordEnd(time);
//                }
//                startAlphaAnimation();
//                startTypeBtnAnimator();
//            }
//
//            @Override
//            public void recordZoom(float zoom) {
//                if (captureLisenter != null) {
//                    captureLisenter.recordZoom(zoom);
//                }
//            }
//
//            @Override
//            public void recordError() {
//                if (captureLisenter != null) {
//                    captureLisenter.recordError();
//                }
//            }
//        });

        //取消按钮
        btn_cancel = new TypeButton(getContext(), TypeButton.TYPE_CANCEL, button_size);
        final LayoutParams btn_cancel_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        btn_cancel_param.gravity = Gravity.CENTER_VERTICAL;
        btn_cancel_param.setMargins((layout_width / 4) - button_size / 2, 0, 0, 0);
        btn_cancel.setLayoutParams(btn_cancel_param);
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeLisenter != null) {
                    typeLisenter.cancel();
                }
                startAlphaAnimation();
//                resetCaptureLayout();
            }
        });

        //确认按钮
        btn_confirm = new TypeButton(getContext(), TypeButton.TYPE_CONFIRM, button_size);
        LayoutParams btn_confirm_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        btn_confirm_param.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        btn_confirm_param.setMargins(0, 0, (layout_width / 4) - button_size / 2, 0);
        btn_confirm.setLayoutParams(btn_confirm_param);
        btn_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeLisenter != null) {
                    typeLisenter.confirm();
                }
                startAlphaAnimation();
//                resetCaptureLayout();
            }
        });

        //返回按钮
        btn_return = new ReturnButton(getContext(), (int) (button_size / 2.5f));
        LayoutParams btn_return_param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btn_return_param.gravity = Gravity.CENTER_VERTICAL;
        btn_return_param.setMargins(layout_width / 6, 0, 0, 0);
        btn_return.setLayoutParams(btn_return_param);
        btn_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftClickListener != null) {
                    leftClickListener.onClick();
                }
            }
        });
        //左边自定义按钮
        iv_custom_left = new ImageView(getContext());
        LayoutParams iv_custom_param_left = new LayoutParams((int) (button_size / 2.5f), (int) (button_size / 2.5f));
        iv_custom_param_left.gravity = Gravity.CENTER_VERTICAL;
        iv_custom_param_left.setMargins(layout_width / 6, 0, 0, 0);
        iv_custom_left.setLayoutParams(iv_custom_param_left);
        iv_custom_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftClickListener != null) {
                    leftClickListener.onClick();
                }
            }
        });

        //右边自定义按钮
        iv_custom_right = new ImageView(getContext());
        LayoutParams iv_custom_param_right = new LayoutParams((int) (button_size / 2.5f), (int) (button_size / 2.5f));
        iv_custom_param_right.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        iv_custom_param_right.setMargins(0, 0, layout_width / 6, 0);
        iv_custom_right.setLayoutParams(iv_custom_param_right);
        iv_custom_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captureTime >= minDuration) {
                    if (rightClickListener != null) {
                        rightClickListener.onClick();
                    }
                } else {
                    ToastUtils.showShort(context, "最低录制" + minDuration + "s");
                }
            }
        });

        txt_tip = new TextView(getContext());
        txt_time = new TextView(getContext());
        LayoutParams txt_param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        txt_param.gravity = Gravity.CENTER_HORIZONTAL;
        txt_param.setMargins(0, 0, 0, 0);
        txt_tip.setText("长按拍摄");
        txt_tip.setTextColor(0xFFFFFFFF);
        txt_tip.setGravity(Gravity.CENTER);
        txt_tip.setLayoutParams(txt_param);

        txt_time.setTextColor(0xFFFFFFFF);
        txt_time.setGravity(Gravity.CENTER);
        txt_time.setLayoutParams(txt_param);
        txt_time.setVisibility(GONE);

        this.addView(btn_capture);
        this.addView(btn_cancel);
        this.addView(btn_confirm);
        this.addView(btn_return);
        this.addView(iv_custom_left);
        this.addView(iv_custom_right);
        this.addView(txt_tip);
        this.addView(txt_time);
//        btn_capture.setTxtTime(txt_time);

    }

    /**************************************************
     * 对外提供的API                      *
     **************************************************/
    public void resetCaptureLayout() {
//        btn_capture.resetState();
        btn_cancel.setVisibility(GONE);
        btn_confirm.setVisibility(GONE);
        btn_capture.setVisibility(VISIBLE);
        if (this.iconLeft != 0)
            iv_custom_left.setVisibility(VISIBLE);
        else
            btn_return.setVisibility(VISIBLE);
        if (this.iconRight != 0)
            iv_custom_right.setVisibility(VISIBLE);
    }


    public void startAlphaAnimation() {
        if (isFirst) {
            ObjectAnimator animator_txt_tip = ObjectAnimator.ofFloat(txt_tip, "alpha", 1f, 0f);
            animator_txt_tip.setDuration(500);
            animator_txt_tip.start();
            isFirst = false;
        }
    }

    public void setTextWithAnimation(String tip) {
        txt_tip.setText(tip);
        ObjectAnimator animator_txt_tip = ObjectAnimator.ofFloat(txt_tip, "alpha", 0f, 1f, 1f, 0f);
        animator_txt_tip.setDuration(2500);
        animator_txt_tip.start();
    }

    private void startUpdateTimer() {
        iv_custom_left.setVisibility(GONE);
        iv_custom_right.setVisibility(GONE);
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(0);
            progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
        }
    }

    private void stopUpdateTimer() {
        iv_custom_left.setVisibility(VISIBLE);
        if (captureTime >= minDuration) {
            iv_custom_right.setVisibility(VISIBLE);
        }
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(0);
        }
    }

    private int captureTime = 0;

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (progressBar != null) {
                captureTime++;
                if (captureTime < duration) {
                    progressBar.setProgress(captureTime);
                    startUpdateTimer();
                } else {
                    if (captureLisenter != null) {
                        captureLisenter.recordEnd(0);
                        stopUpdateTimer();
                    }
                    startAlphaAnimation();
                    startTypeBtnAnimator();
                }
            }
        }
    };

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    private int duration ;
    public void setDuration(int duration) {
        this.duration = duration;
//        btn_capture.setDuration(duration);
    }

    private int minDuration;

    public void setMinDuration(int duration) {
        this.minDuration = duration;
//        btn_capture.setMinDuration(duration);
    }

    public void setColor(String color) {
//        btn_capture.setColor(color);
    }

    public void setButtonFeatures(int state) {
//        btn_capture.setButtonFeatures(state);
    }

    public void setTip(String tip) {
        txt_tip.setText(tip);
    }

    public void showTip() {
        txt_tip.setVisibility(VISIBLE);
    }

    public void setIconSrc(int iconLeft, int iconRight) {
        this.iconLeft = iconLeft;
        this.iconRight = iconRight;
        if (this.iconLeft != 0) {
            iv_custom_left.setImageResource(iconLeft);
            iv_custom_left.setVisibility(VISIBLE);
            btn_return.setVisibility(GONE);
        } else {
            iv_custom_left.setVisibility(GONE);
            btn_return.setVisibility(VISIBLE);
        }
        if (this.iconRight != 0) {
            iv_custom_right.setImageResource(iconRight);
            iv_custom_right.setVisibility(VISIBLE);
        } else {
            iv_custom_right.setVisibility(GONE);
        }
    }

    public void setLeftClickListener(ClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
    }

    public void setRightClickListener(ClickListener rightClickListener) {
        this.rightClickListener = rightClickListener;
    }
}
