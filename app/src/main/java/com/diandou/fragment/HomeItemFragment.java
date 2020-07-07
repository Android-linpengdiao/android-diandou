package com.diandou.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.activity.SearchActivity;
import com.diandou.adapter.PagerAdapter;
import com.diandou.databinding.FragmentHomeItemBinding;
import com.diandou.model.BannerData;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;

public class HomeItemFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeItemBinding binding;
    private int navId;

    public static HomeItemFragment newInstance(int navId) {
        HomeItemFragment fragment = new HomeItemFragment();
        Bundle args = new Bundle();
        args.putInt("id",navId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            navId = getArguments().getInt("id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_item, container, false);
        setStatusBarHeight(binding.getRoot());
        setStatusBarDarkTheme(true);

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        if (navId==0) {
            pagerAdapter.addFragment("最热", HomeItemListFragment.newInstance(navId, 1));
            pagerAdapter.addFragment("推荐", HomeItemListFragment.newInstance(navId, 2));
            binding.viewPager.setCurrentItem(0);
        }else {
            pagerAdapter.addFragment("推荐", HomeItemListFragment.newInstance(navId, 2));
            pagerAdapter.addFragment("最新", HomeItemListFragment.newInstance(navId, 3));
            binding.viewPager.setCurrentItem(1);
        }
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
