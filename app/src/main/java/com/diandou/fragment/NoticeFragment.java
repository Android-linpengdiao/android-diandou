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
import com.diandou.activity.NoticeListActivity;
import com.diandou.adapter.NoticeAdapter;
import com.diandou.databinding.FragmentLikeBinding;
import com.diandou.databinding.FragmentNoticeBinding;
import com.diandou.model.MessageData;
import com.diandou.model.NoticeData;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;

public class NoticeFragment extends BaseFragment {

    private FragmentNoticeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notice, container, false);

        initData();

        binding.noticeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NoticeListActivity.class);
            }
        });

        return binding.getRoot();
    }

    private void initData() {
        SendRequest.commonMessage(new GenericsCallback<NoticeData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(NoticeData response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {

                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });
    }
}
