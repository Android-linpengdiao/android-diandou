package com.diandou.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.diandou.R;
import com.diandou.databinding.FragmentMessageBinding;
import com.diandou.utils.ViewUtils;

public class MessageFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    private FragmentMessageBinding binding;

    private FragmentManager mFragmentManager;
    public static Fragment mCurrentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        setStatusBarHeight(binding.getRoot());
        setStatusBarDarkTheme(true);

        binding.radioGroupView.setOnCheckedChangeListener(this);
        initDefaultFragment();

        return binding.getRoot();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_like:
                replaceContentFragment(LikeFragment.class);
                break;
            case R.id.radio_button_fans:
                replaceContentFragment(FansFragment.class);
                break;
            case R.id.radio_button_comment:
                replaceContentFragment(CommentFragment.class);
                break;
            case R.id.radio_button_notice:
                replaceContentFragment(NoticeFragment.class);
                break;
            default:
                break;
        }
    }

    private void initDefaultFragment() {
        mFragmentManager = getChildFragmentManager();
        mCurrentFragment = ViewUtils.createFragment(LikeFragment.class, true);
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

}
