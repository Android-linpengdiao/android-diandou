package com.diandou.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baselibrary.Constants;
import com.baselibrary.manager.DialogManager;
import com.baselibrary.utils.FileSizeUtil;
import com.baselibrary.utils.FileUtils;
import com.baselibrary.utils.MsgCache;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.databinding.ActivitySettingsBinding;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        addActivity(this);

        binding.back.setOnClickListener(this);
        binding.version.setOnClickListener(this);
        binding.about.setOnClickListener(this);
        binding.feedback.setOnClickListener(this);
        binding.clear.setOnClickListener(this);
        binding.resetPassword.setOnClickListener(this);
        binding.logout.setOnClickListener(this);

        double fileSize = FileSizeUtil.getFileOrFilesSize(FileUtils.getPath(), 3);
        binding.tvFileSize.setText(fileSize + "MB");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.version:
                ToastUtils.showShort(SettingsActivity.this, "当前为最新版本");
                break;
            case R.id.back:
                finish();
                break;
            case R.id.about:
                openActivity(AboutActivity.class);
                break;
            case R.id.feedback:
                openActivity(FeedbackActivity.class);
                break;
            case R.id.clear:
                DialogManager.showConfirmDialog(this, "确定清理缓存？", new DialogManager.Listener() {

                    @Override
                    public void onItemLeft() {

                    }

                    @Override
                    public void onItemRight() {
                        FileUtils.clearFile();
                        double fileSize = FileSizeUtil.getFileOrFilesSize(FileUtils.getPath(), 3);
                        binding.tvFileSize.setText(fileSize + "MB");
                        ToastUtils.showShort(SettingsActivity.this, "已完成清理");
                    }
                });
                break;
            case R.id.resetPassword:
                openActivity(ResetPasswordActivity.class);
                break;
            case R.id.logout:
                DialogManager.showConfirmDialog(SettingsActivity.this, "确定要退出登录？", new DialogManager.Listener() {
                    @Override
                    public void onItemLeft() {

                    }

                    @Override
                    public void onItemRight() {
                        MsgCache.get(SettingsActivity.this).remove(Constants.USER_INFO);
                        openActivity(LoginActivity.class);
                        finish();
                    }
                });
                break;
        }
    }
}
