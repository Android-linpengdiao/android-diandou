package com.diandou.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.MessageBus;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.MessageAdapter;
import com.diandou.adapter.MineWorkAdapter;
import com.diandou.databinding.FragmentLikeBinding;
import com.diandou.model.BaseData;
import com.diandou.model.MessageData;
import com.diandou.model.MineWorkData;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.OnClickListener;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MineWorkFragment extends BaseFragment {
    private static final String TAG = "MineWorkFragment";
    private FragmentLikeBinding binding;
    private MineWorkAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_like, container, false);

        adapter = new MineWorkAdapter(getActivity());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getActivity());
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(getActivity(), 2));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {

            }

            @Override
            public void onLongClick(View view, Object object) {
                MessageBus.Builder builder = new MessageBus.Builder();
                MessageBus messageBus = builder
                        .codeType(MessageBus.msgId_workSelection)
                        .message(0)
                        .build();
                EventBus.getDefault().post(messageBus);
            }
        });

        binding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        EventBus.getDefault().register(this);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMainMessage(MessageBus messageBus) {
        StringBuffer stringBuffer = new StringBuffer();
        if (messageBus.getCodeType().equals(messageBus.msgId_workDelete)) {
            int tag = (int) messageBus.getMessage();
            if (tag == 0) {
                adapter.setSelection(false);
                if (adapter.getList() != null && adapter.getList().size() > 0) {
                    for (int i = 0; i < adapter.getList().size(); i++) {
                        if (adapter.getList().get(i).isSelection()) {
                            stringBuffer.append(adapter.getList().get(i).getId() + ",");
                        }
                    }
                }
                if (stringBuffer.length() > 0) {
                    deleteContent(stringBuffer.substring(0, stringBuffer.length() - 1).toString());
                } else {
                    ToastUtils.showShort(getActivity(), "请选择作品");
                }
            }
        } else if (messageBus.getCodeType().equals(messageBus.msgId_workConfirm)) {
            int tag = (int) messageBus.getMessage();
            if (tag == 0) {
                adapter.setSelection(false);
            }
        }

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void initData() {
        if (getUserInfo().getData() == null) {
            binding.swipeRefreshLayout.setRefreshing(false);
            if (adapter.getList()!=null&&adapter.getList().size()>0){
                adapter.getList().clear();
                adapter.notifyDataSetChanged();
            }
            return;
        }
        binding.swipeRefreshLayout.setRefreshing(true);
        SendRequest.centerSelfWork(getUserInfo().getData().getId(), new GenericsCallback<MineWorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(MineWorkData response, int id) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(getActivity(), response.getMsg());
                }
            }

        });
    }

    private void deleteContent(String contentIds) {
        SendRequest.deleteContent(getUserInfo().getData().getId(), contentIds,
                new GenericsCallback<BaseData>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseData response, int id) {
                        if (response.getCode() == 200) {
                            initData();
                        } else {
                            ToastUtils.showShort(getActivity(), response.getMsg());
                        }
                    }
                });
    }

}
