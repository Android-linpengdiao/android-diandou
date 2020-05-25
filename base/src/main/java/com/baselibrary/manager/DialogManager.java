package com.baselibrary.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.baselibrary.R;
import com.baselibrary.utils.CommonUtil;

/**
 * Created by Administrator on 2017/9/15.
 */

public class DialogManager {

    private static final String TAG = "DialogManager";

    public static AppCompatDialog createLoadingDialog(Activity act) {
        return createLoadingDialog(act, "");
    }

    public static AppCompatDialog createLoadingDialog(Activity act, String content) {
        if (null != act) {
            AppCompatDialog appCompatDialog = new AppCompatDialog(act, R.style.LoadingDialogTheme);
            View view = act.getLayoutInflater().inflate(R.layout.web_layout_loading, null);
            appCompatDialog.setContentView(view);
            TextView title = view.findViewById(R.id.content);
            ImageView progress = view.findViewById(R.id.progress);
            if (!CommonUtil.isBlank(content)) {
                title.setText("" + content);
            }
            Animation antv = AnimationUtils.loadAnimation(act, R.anim.loading_progressbar);
            LinearInterpolator lin = new LinearInterpolator();
            antv.setInterpolator(lin);
            antv.setRepeatCount(-1);
            progress.startAnimation(antv);
            appCompatDialog.setCanceledOnTouchOutside(false);
            return appCompatDialog;
        } else {
            return null;
        }
    }

    public interface Listener {
        void onItemLeft();

        void onItemRight();
    }

    public static void showConfirmDialog(Activity act, String content, final Listener listener) {
        showConfirmDialog(act, null, content, null, null, listener);
    }

    public static void showConfirmDialog(Activity act, String title, String content, final Listener listener) {
        showConfirmDialog(act, title, content, null, null, listener);
    }

    public static void showConfirmDialog(Activity act, String title, String content, String leftText, String rightText, final Listener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(act, AlertDialog.THEME_HOLO_DARK).create();
        dialog.setCancelable(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.getDecorView().setBackgroundColor(act.getResources().getColor(R.color.transparent));
        window.setContentView(R.layout.view_confirm_dialog_alert);
        TextView tvTitle = window.findViewById(R.id.title);
        TextView tvContent = window.findViewById(R.id.content);
        TextView tvLeft = window.findViewById(R.id.tv_left);
        TextView tvRight = window.findViewById(R.id.tv_right);
        tvContent.setText(content);
        if (!CommonUtil.isBlank(title)) {
            tvTitle.setText(title);
        }
        if (!CommonUtil.isBlank(leftText)) {
            tvLeft.setText(leftText);
        }
        if (!CommonUtil.isBlank(rightText)) {
            tvRight.setText(rightText);
        }
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemLeft();
                }
                dialog.cancel();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemRight();
                }
                dialog.cancel();
            }
        });
    }

}
