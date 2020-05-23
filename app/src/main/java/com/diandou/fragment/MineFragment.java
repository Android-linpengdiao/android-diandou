package com.diandou.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.databinding.FragmentFollowBinding;
import com.diandou.databinding.FragmentMineBinding;

public class MineFragment extends BaseFragment {

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

        initView();

        return binding.getRoot();
    }

    private void initView() {
        if (getUserInfo().getData() != null) {

            binding.headLogoutLayout.headLogoutView.setVisibility(View.GONE);
            binding.headLoginLayout.headLoginView.setVisibility(View.VISIBLE);

            binding.headLoginLayout.userName.setText(getUserInfo().getData().getName());
            binding.headLoginLayout.touristId.setText("点逗号：" + getUserInfo().getData().getTourist_id());
            GlideLoader.LoderImage(getActivity(), getUserInfo().getData().getAvatar(), binding.headLoginLayout.userIcon, 100);
        } else {
            binding.headLogoutLayout.headLogoutView.setVisibility(View.VISIBLE);
            binding.headLoginLayout.headLoginView.setVisibility(View.GONE);
        }
    }
}
