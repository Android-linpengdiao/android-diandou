package com.baselibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.baselibrary.R;
import com.baselibrary.manager.LoadingManager;

public class ProgressView extends Dialog {

    private static final String TAG = "ProgressView";
    private boolean isButtom = false;
    private LoadingManager.Listener mListener;

    public ProgressView(Context context) {
        super(context);
        init(context);
    }

    public ProgressView(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    public ProgressView(Context context, int theme, boolean isButtom, LoadingManager.Listener listener) {
        super(context, theme);
        this.isButtom = isButtom;
        this.mListener = listener;
        if (!isButtom) {
            init(context);
        } else {
            initNew(context);
        }
    }

    TextView title;

    private void init(Context context) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.web_layout_progress);
        title = findViewById(R.id.tv_progress_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }

    private void initNew(Context context) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.web_layout_progress_new);
        title = findViewById(R.id.tv_progress_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        getWindow().getDecorView().setBackgroundColor(context.getResources().getColor(R.color.transparentDark));
        findViewById(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemLeft();
                }
            }
        });
        findViewById(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemRight();
                }
            }
        });

    }

    public void updateProgress(final String progress) {
        Log.i(TAG, "updateProgress: "+progress);
        title.post(new Runnable() {
            @Override
            public void run() {
                title.setText(progress);
            }
        });
    }

    @Override
    public void show() {//开启
        super.show();
    }

    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }

}
