package com.diandou.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diandou.R;
import com.diandou.databinding.FragmentFollowBinding;

public class MineFragment extends BaseFragment{

    private FragmentFollowBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow, container, false);
        setStatusBarHeight(binding.getRoot());
        setStatusBarDarkTheme(true);

        return binding.getRoot();
    }
}
