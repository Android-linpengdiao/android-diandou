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

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.activity.SearchActivity;
import com.diandou.activity.TabTypeActivity;
import com.diandou.adapter.PagerAdapter;
import com.diandou.databinding.FragmentHomeBinding;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        setStatusBarHeight(binding.getRoot());

        PagerAdapter mainHomePagerAdapter = new PagerAdapter(getChildFragmentManager());
        for (int i = 0; i < CommonUtil.getTextTabListString().size(); i++) {
            mainHomePagerAdapter.addFragment(CommonUtil.getTextTabListString().get(i), HomeItemFragment.newInstance());
        }
        binding.viewPager.setAdapter(mainHomePagerAdapter);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.viewPager.setCurrentItem(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.search.setOnClickListener(this);
        binding.tabType.setOnClickListener(this);

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
            case R.id.tab_type:
                intent = new Intent(getActivity(), TabTypeActivity.class);
                startActivityForResult(intent,100);
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
                        int position = data.getIntExtra("position",0);
                        binding.viewPager.setCurrentItem(position);
                    }
                    break;
            }
        }
    }
}
