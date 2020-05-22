package com.baselibrary.manager;

import android.app.Activity;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
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
}
