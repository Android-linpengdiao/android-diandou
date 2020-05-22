package com.diandou.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.baselibrary.Constants;
import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.MsgCache;
import com.baselibrary.utils.StatusBarUtil;
import com.diandou.R;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStatusBarDarkTheme(boolean dark) {
        if (!StatusBarUtil.setStatusBarDarkTheme(getActivity(), dark)) {
            StatusBarUtil.setStatusBarColor(getActivity(), dark ? R.color.black : R.color.white);
        }
    }

    // 5.0版本以上
    @SuppressLint("NewApi")
    public void setStatusBarHeight(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            if (view.findViewById(R.id.status_bar) != null) {
                view.findViewById(R.id.status_bar).setVisibility(View.VISIBLE);
                int statusBarHeight = CommonUtil.getStatusBarHeight(getActivity());
                view.findViewById(R.id.status_bar).getLayoutParams().height = statusBarHeight;
            }
        }
    }

    public void openActivity(Class<?> mClass) {
        openActivity(mClass,null);
    }

    public void openActivity(Class<?> mClass, Bundle mBundle) {
        Intent intent = new Intent(getActivity(), mClass);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        startActivity(intent);
    }

    public void setUserInfo(UserInfo userInfo) {
        MsgCache.get(getActivity()).put(Constants.USER_INFO, userInfo);
    }

    public UserInfo getUserInfo() {
        UserInfo userinfo = (UserInfo) MsgCache.get(getActivity()).getAsObject(Constants.USER_INFO);
        if (!CommonUtil.isBlank(userinfo)) {
            return userinfo;
        }
        return new UserInfo();
    }
}
