package com.diandou.fragment;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baselibrary.MessageBus;
import com.baselibrary.UserInfo;
import com.baselibrary.manager.DialogManager;
import com.baselibrary.utils.GlideLoader;
import com.diandou.R;
import com.diandou.activity.EditorActivity;
import com.diandou.activity.LoginActivity;
import com.diandou.activity.MineFansActivity;
import com.diandou.activity.MineFollowActivity;
import com.diandou.activity.SettingsActivity;
import com.diandou.adapter.MinePagerAdapter;
import com.diandou.adapter.PagerAdapter;
import com.diandou.databinding.FragmentMineBinding;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.Call;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "MineFragment";
    private FragmentMineBinding binding;
    private MinePagerAdapter mainHomePagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        setStatusBarHeight(binding.getRoot());
        setStatusBarDarkTheme(true);

        binding.headLoginLayout.tvEditor.setOnClickListener(this);
        binding.headLoginLayout.tvSetting.setOnClickListener(this);
        binding.headLoginLayout.followersView.setOnClickListener(this);
        binding.headLoginLayout.likerView.setOnClickListener(this);
        binding.headLoginLayout.appreciateView.setOnClickListener(this);
        binding.workDeleteView.setOnClickListener(this);
        binding.tvDelete.setOnClickListener(this);
        binding.tvConfirm.setOnClickListener(this);
        binding.headLogoutLayout.loginView.setOnClickListener(this);

        initTab();

        EventBus.getDefault().register(this);

        return binding.getRoot();
    }

    private int tag = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMainMessage(MessageBus messageBus) {
        if (messageBus.getCodeType().equals(messageBus.msgId_workSelection)) {
            Log.i(TAG, "getMainMessage: ");
            tag = (int) messageBus.getMessage();
            binding.workDeleteView.setVisibility(View.VISIBLE);
            binding.tvTag.setText(tag == 0 ? "作品 " + getUserInfo().getData().getContent_num() : "喜欢 " + getUserInfo().getData().getLiker_num());
        }

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserInfo().getData() != null) {
            baseInfo();
        } else {
            initView();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (getUserInfo().getData() != null) {
                baseInfo();
            } else {
                initView();
            }
        }
    }

    public void baseInfo() {
        SendRequest.baseInfo(getUserInfo().getData().getId(), new GenericsCallback<UserInfo>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(UserInfo response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    setUserInfo(response);
                    initView();
                }
            }

        });
    }

    private void initTab() {
        mainHomePagerAdapter = new MinePagerAdapter(getChildFragmentManager());
        mainHomePagerAdapter.addTitle("作品 " + (getUserInfo().getData() != null ? getUserInfo().getData().getContent_num() : 0));
        mainHomePagerAdapter.addTitle("喜欢 " + (getUserInfo().getData() != null ? getUserInfo().getData().getLiker_num() : 0));
        binding.viewPager.setAdapter(mainHomePagerAdapter);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.viewPager.setCurrentItem(0);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void initView() {
        if (getUserInfo().getData() != null) {

            binding.headLogoutLayout.headLogoutView.setVisibility(View.GONE);
            binding.headLoginLayout.headLoginView.setVisibility(View.VISIBLE);

            binding.headLoginLayout.userName.setText(getUserInfo().getData().getName());
            binding.headLoginLayout.touristId.setText("点逗号：" + getUserInfo().getData().getTourist_id());
            GlideLoader.LoderCircleImage(getActivity(), getUserInfo().getData().getAvatar(), binding.headLoginLayout.userIcon);

            binding.headLoginLayout.tvFollowers.setText(String.valueOf(getUserInfo().getData().getConcern()));
            binding.headLoginLayout.tvLiker.setText(String.valueOf(getUserInfo().getData().getAttention()));
            binding.headLoginLayout.tvAssistNum.setText(String.valueOf(getUserInfo().getData().getAssist_num()));

        } else {
            binding.headLogoutLayout.headLogoutView.setVisibility(View.VISIBLE);
            binding.headLoginLayout.headLoginView.setVisibility(View.GONE);
        }
        if (mainHomePagerAdapter != null) {
            mainHomePagerAdapter.setPageTitle(0, "作品 " + (getUserInfo().getData() != null ? getUserInfo().getData().getContent_num() : 0));
            mainHomePagerAdapter.setPageTitle(1, "喜欢 " + (getUserInfo().getData() != null ? getUserInfo().getData().getLiker_num() : 0));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_delete:
                DialogManager.showConfirmDialog(getActivity(), "确定要删除该作品？", new DialogManager.Listener() {
                    @Override
                    public void onItemLeft() {

                    }

                    @Override
                    public void onItemRight() {
                        MessageBus.Builder builder = new MessageBus.Builder();
                        MessageBus messageBus = builder
                                .codeType(MessageBus.msgId_workDelete)
                                .message(tag)
                                .build();
                        EventBus.getDefault().post(messageBus);
                        binding.workDeleteView.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.tv_confirm:
                MessageBus.Builder builder = new MessageBus.Builder();
                MessageBus messageBus = builder
                        .codeType(MessageBus.msgId_workConfirm)
                        .message(tag)
                        .build();
                EventBus.getDefault().post(messageBus);
                binding.workDeleteView.setVisibility(View.GONE);
                break;
            case R.id.loginView:
                openActivity(LoginActivity.class);
                break;
            case R.id.tv_editor:
                openActivity(EditorActivity.class);
                break;
            case R.id.tv_setting:
                openActivity(SettingsActivity.class);
                break;
            case R.id.followers_view:
                openActivity(MineFollowActivity.class);
                break;
            case R.id.liker_view:
                openActivity(MineFansActivity.class);
                break;
            case R.id.appreciate_view:

                break;
        }
    }

}
