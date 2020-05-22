package com.diandou.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.diandou.R;
import com.diandou.adapter.VideoListAdapter;
import com.diandou.databinding.FragmentHomeItemListBinding;
import com.diandou.view.GridItemDecoration;

public class HomeItemListFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeItemListBinding binding;

    public static HomeItemListFragment newInstance() {
        HomeItemListFragment fragment = new HomeItemListFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_item_list, container, false);
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

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
