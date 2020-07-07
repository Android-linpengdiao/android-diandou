package com.diandou.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.UserInfo;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.diandou.MainActivity;
import com.diandou.MyApplication;
import com.diandou.NavData;
import com.diandou.R;
import com.diandou.activity.LoginActivity;
import com.diandou.activity.SearchActivity;
import com.diandou.activity.TabTypeActivity;
import com.diandou.adapter.PagerAdapter;
import com.diandou.databinding.FragmentHomeBinding;
import com.diandou.model.BannerData;
import com.diandou.utils.GlideImageLoader;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private PagerAdapter mainHomePagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        setStatusBarHeight(binding.getRoot());

        binding.search.setOnClickListener(this);
        binding.tabType.setOnClickListener(this);

        SendRequest.commonNav(new GenericsCallback<NavData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(NavData response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    mainHomePagerAdapter = new PagerAdapter(getChildFragmentManager());
                    mainHomePagerAdapter.addFragment("热门", HomeItemFragment.newInstance(0));
                    for (int i = 0; i < response.getData().size(); i++) {
                        mainHomePagerAdapter.addFragment(response.getData().get(i).getName(), HomeItemFragment.newInstance(response.getData().get(i).getId()));
                    }
                    binding.viewPager.setAdapter(mainHomePagerAdapter);
                    binding.viewPager.setOffscreenPageLimit(1);
                    binding.viewPager.setCurrentItem(0);
                    binding.tabLayout.setupWithViewPager(binding.viewPager);
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });

        SendRequest.commonBanner(new GenericsCallback<BannerData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BannerData response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData().size() > 0) {
                    initBanner(response.getData());
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });

        return binding.getRoot();
    }

    private void initBanner(List<BannerData.DataBean> data) {
        binding.banner.setImageLoader(new GlideImageLoader(10));
        binding.banner.setDelayTime(5000);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getImg());
        }
        binding.banner.setImages(list);
        binding.banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                    }
                })
                .start();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tab_type:
                intent = new Intent(getActivity(), TabTypeActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case 100:
                    if (data != null) {
                        int position = data.getIntExtra("position", 0);
                        binding.viewPager.setCurrentItem(position);
                    }
                    break;
            }
        }
    }
}
