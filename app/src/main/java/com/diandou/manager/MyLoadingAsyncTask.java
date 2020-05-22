package com.diandou.manager;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;

import com.baselibrary.manager.DialogManager;

public abstract class MyLoadingAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private static final String TAG = "MyLoadingAsyncTask";

    private Activity act = null;

    private AppCompatDialog loadingDialog = null;

    private boolean showLoadingDialog = true;

    public MyLoadingAsyncTask(Activity act) {
        this.act = act;
        this.loadingDialog = DialogManager.createLoadingDialog(act);
        this.loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });
    }

    public MyLoadingAsyncTask(Activity act, String title) {
        this.act = act;
        this.loadingDialog = DialogManager.createLoadingDialog(act, title);
        this.loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });
    }

    public MyLoadingAsyncTask<Params, Progress, Result> setShowLoadingDialog(boolean show) {
        this.showLoadingDialog = show;
        return this;
    }

    public Activity getAct() {
        return this.act;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showLoadingDialog) {
            loadingDialog.show();
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        loadingDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d(TAG, "onCancelled: ");
    }

}