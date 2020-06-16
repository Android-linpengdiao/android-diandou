package com.diandou.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.MessageAdapter;
import com.diandou.databinding.FragmentLikeBinding;
import com.diandou.model.MessageData;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;

public class CommentFragment extends BaseFragment {

    private FragmentLikeBinding binding;
    private MessageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_like, container, false);

        adapter = new MessageAdapter(getActivity(), 3);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        binding.swipeRefreshLayout.setRefreshing(true);
        initData();

        return binding.getRoot();
    }

    private void initData() {
        SendRequest.centerDiscuss(getUserInfo().getData().getId(), new GenericsCallback<MessageData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(MessageData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null) {
                    adapter.refreshData(response.getData());
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });
    }
}
