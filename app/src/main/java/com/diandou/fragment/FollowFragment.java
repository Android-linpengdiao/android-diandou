package com.diandou.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.diandou.R;
import com.diandou.activity.SearchActivity;
import com.diandou.adapter.PagerAdapter;
import com.diandou.adapter.VideoListAdapter;
import com.diandou.databinding.FragmentFollowBinding;
import com.diandou.databinding.FragmentHomeBinding;
import com.diandou.view.GridItemDecoration;

public class FollowFragment extends BaseFragment{

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

        VideoListAdapter adapter = new VideoListAdapter(getActivity());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getActivity());
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(getActivity(), 10));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);
        adapter.refreshData(CommonUtil.getImageListString());

        binding.swipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });


        return binding.getRoot();
    }
}
