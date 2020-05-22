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
import com.diandou.R;
import com.diandou.activity.SearchActivity;
import com.diandou.adapter.PagerAdapter;
import com.diandou.databinding.FragmentHomeItemBinding;

public class HomeItemFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeItemBinding binding;

    public static HomeItemFragment newInstance() {
        HomeItemFragment fragment = new HomeItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_item, container, false);
        setStatusBarHeight(binding.getRoot());
        setStatusBarDarkTheme(true);

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment("最热", HomeItemListFragment.newInstance());
        pagerAdapter.addFragment("推荐", HomeItemListFragment.newInstance());
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.viewPager.setCurrentItem(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        GlideLoader.LoderImage(getActivity(), CommonUtil.getImageListString().get(3), binding.cover, 10);

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