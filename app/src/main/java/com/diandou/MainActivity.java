package com.diandou;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.baselibrary.utils.ToastUtils;
import com.cjt2325.cameralibrary.CameraActivity;
import com.cjt2325.cameralibrary.JCameraView;
import com.diandou.activity.BaseActivity;
import com.diandou.activity.LoginActivity;
import com.diandou.databinding.ActivityMainBinding;
import com.diandou.fragment.FollowFragment;
import com.diandou.fragment.HomeFragment;
import com.diandou.fragment.MessageFragment;
import com.diandou.fragment.MineFragment;
import com.diandou.utils.ViewUtils;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.StringCallback;

import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private ActivityMainBinding mainBinding;
    private FragmentManager mFragmentManager;
    public static Fragment mCurrentFragment;

    private final static int REQUEST_WXCAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        addActivity(this);

        mainBinding.radioGroupView.setOnCheckedChangeListener(this);
        initDefaultFragment();

        mainBinding.radioButtonRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserInfo().getData() != null) {
                    int type = JCameraView.BUTTON_STATE_ONLY_RECORDER;
                    int minTime = 90;
                    int maxTime = 180;
                    CameraActivity.startCameraActivity(MainActivity.this, minTime, maxTime, "#44bf19", type, REQUEST_WXCAMERA);
                } else {
                    openActivity(LoginActivity.class);
                }
            }
        });

        SendRequest.commonStartUp(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_home:
                replaceContentFragment(HomeFragment.class);
                break;
            case R.id.radio_button_follow:
                if (getUserInfo().getData() != null) {
                    replaceContentFragment(FollowFragment.class);
                } else {
                    openActivity(LoginActivity.class);
                }
                break;
//            case R.id.radio_button_release:
//                replaceContentFragment(ReleaseFragment.class);
//                break;
            case R.id.radio_button_message:
                if (getUserInfo().getData() != null) {
                    replaceContentFragment(MessageFragment.class);
                } else {
                    openActivity(LoginActivity.class);
                }
                break;
            case R.id.radio_button_mine:
                replaceContentFragment(MineFragment.class);
                break;
            default:
                break;
        }
    }

    private void initDefaultFragment() {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = ViewUtils.createFragment(HomeFragment.class, true);
        mFragmentManager.beginTransaction().add(R.id.content_frame, mCurrentFragment).commit();
    }

    public Fragment replaceContentFragment(Class<?> mclass) {
        Fragment fragment = ViewUtils.createFragment(mclass, true);
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.content_frame, fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
        return mCurrentFragment;
    }


    private long lastTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastTime > 2000) {
                lastTime = System.currentTimeMillis();
                ToastUtils.showShort(MainActivity.this, "再按一次退出应用");
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}