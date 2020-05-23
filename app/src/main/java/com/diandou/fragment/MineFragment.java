package com.diandou.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.activity.EditorActivity;
import com.diandou.activity.SettingsActivity;
import com.diandou.adapter.PagerAdapter;
import com.diandou.databinding.FragmentMineBinding;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMineBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        setStatusBarHeight(binding.getRoot());
        setStatusBarDarkTheme(true);

        binding.headLoginLayout.tvEditor.setOnClickListener(this);
        binding.headLoginLayout.tvSetting.setOnClickListener(this);

        initTab();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initView();
        }
    }

    private void initTab() {
        PagerAdapter mainHomePagerAdapter = new PagerAdapter(getChildFragmentManager());
        mainHomePagerAdapter.addFragment("作品 28", new MineWorkFragment());
        mainHomePagerAdapter.addFragment("喜欢 132", new MineLikeFragment());
        binding.viewPager.setAdapter(mainHomePagerAdapter);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.viewPager.setCurrentItem(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void initView() {
        if (getUserInfo().getData() != null) {

            binding.headLogoutLayout.headLogoutView.setVisibility(View.GONE);
            binding.headLoginLayout.headLoginView.setVisibility(View.VISIBLE);

            binding.headLoginLayout.userName.setText(getUserInfo().getData().getName());
            binding.headLoginLayout.touristId.setText("点逗号：" + getUserInfo().getData().getTourist_id());
            GlideLoader.LoderClipImage(getActivity(), getUserInfo().getData().getAvatar(), binding.headLoginLayout.userIcon);
        } else {
            binding.headLogoutLayout.headLogoutView.setVisibility(View.VISIBLE);
            binding.headLoginLayout.headLoginView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_editor:
                openActivity(EditorActivity.class);
                break;
            case R.id.tv_setting:
                openActivity(SettingsActivity.class);
                break;
        }
    }
}
