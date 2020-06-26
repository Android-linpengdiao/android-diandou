package com.diandou.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.WorkAdapter;
import com.diandou.databinding.FragmentHomeItemListBinding;
import com.diandou.model.WorkData;
import com.diandou.view.GridItemDecoration;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;

public class HomeItemListFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeItemListBinding binding;
    private WorkAdapter adapter;
    private int navId;
    private int type = 1; //1 最热 ；2 推荐

    public static HomeItemListFragment newInstance(int navId, int type) {
        HomeItemListFragment fragment = new HomeItemListFragment();
        Bundle args = new Bundle();
        args.putInt("navId", navId);
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            navId = getArguments().getInt("navId");
            type = getArguments().getInt("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_item_list, container, false);
        setStatusBarHeight(binding.getRoot());
        setStatusBarDarkTheme(true);


        adapter = new WorkAdapter(getActivity());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getActivity());
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(getActivity(), 10));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);

        searchWork();

        return binding.getRoot();
    }

    private void searchWork() {
        SendRequest.searchWorkHome(type, navId, 100, 1, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(WorkData response, int id) {
                if (response.getCode() == 200) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
