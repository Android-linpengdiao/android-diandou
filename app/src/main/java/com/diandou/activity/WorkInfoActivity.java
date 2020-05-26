package com.diandou.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.WorkAdapter;
import com.diandou.databinding.ActivityWorkInfoBinding;
import com.diandou.model.CommentData;
import com.diandou.model.WorkData;
import com.diandou.model.WorkDetail;
import com.diandou.view.CommentListPopupWindow;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.OnClickListener;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Request;

public class WorkInfoActivity extends BaseActivity implements View.OnClickListener {

    private ActivityWorkInfoBinding binding;
    private WorkAdapter adapter;
    private int id;

    private WorkDetail workDetail;
    private CommentData commentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_info);

        binding.back.setOnClickListener(this);
        binding.fullscreen.setOnClickListener(this);
        binding.tvLike.setOnClickListener(this);
        binding.tvAppreciate.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvShare.setOnClickListener(this);
        GlideLoader.LoderImage(this, CommonUtil.getImageListString().get(5), binding.thumbnails);

        adapter = new WorkAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setNestedScrollingEnabled(false);
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 10));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);

        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
    }

    private void initData() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", 0);
        } else {
            finish();
        }
        SendRequest.workDetail(id, new GenericsCallback<WorkDetail>(new JsonGenericsSerializator()) {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                binding.viewLayout.setVisibility(View.GONE);
                binding.bottomView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(WorkDetail response, int id) {
                workDetail = response;
                if (response.getCode() == 200 && response.getData() != null) {
                    initView(response.getData());
                } else {
                    ToastUtils.showShort(WorkInfoActivity.this, response.getMsg());
                }
            }

        });
    }

    private void initView(WorkDetail.DataBean data) {

        binding.viewLayout.setVisibility(View.VISIBLE);
        binding.bottomView.setVisibility(View.VISIBLE);

        binding.tvDesc.setText(data.getDesc());
        binding.tvAddr.setText(data.getAddr());
        binding.tvTime.setText(data.getUpdated_at());
        binding.tvAppreciate.setText(data.getAssist() + "");
        GlideLoader.LoderImage(WorkInfoActivity.this, data.getImg(), binding.thumbnails);

        getVideos(data);
        showContentComment(data);
    }

    private void getVideos(WorkDetail.DataBean data) {
        SendRequest.searchWorkType(data.getNav_id(), 10, 1, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(WorkData response, int id) {
                if (response.getCode() == 200) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(WorkInfoActivity.this, response.getMsg());
                }
            }

        });

    }

    private void showContentComment(WorkDetail.DataBean data) {
        SendRequest.showContentComment(data.getId(), new GenericsCallback<CommentData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(CommentData response, int id) {
                commentData = response;
                if (commentData != null && response.getCode() == 200) {
                    if (commentData.getData() != null) {
                        binding.tvComment.setText(commentData.getData().size() + "");
                        if (commentListPopupWindow != null) {
                            commentListPopupWindow.setCommentData(commentData);
                        }
                    }
                } else {
                    ToastUtils.showShort(WorkInfoActivity.this, response.getMsg());
                }
            }

        });

    }

    private void publishComment(WorkDetail.DataBean data, String content) {
        SendRequest.publishComment(getUserInfo().getData().getId(), data.getId(), content, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
                    if (jsonObject.optInt("code") == 200) {
                        if (workDetail != null && workDetail.getData() != null) {
                            showContentComment(workDetail.getData());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private CommentListPopupWindow commentListPopupWindow;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    smallScreen();
                } else {
                    finish();
                }

                break;
            case R.id.fullscreen:
                toggleOrientation();
                break;
            case R.id.tv_like:
                binding.tvLike.setSelected(!binding.tvLike.isSelected());
                break;
            case R.id.tv_appreciate:
                binding.tvAppreciate.setSelected(!binding.tvAppreciate.isSelected());
                break;
            case R.id.tv_comment:
                commentListPopupWindow = new CommentListPopupWindow(WorkInfoActivity.this);
                commentListPopupWindow.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view, Object object) {
                        if (workDetail != null && workDetail.getData() != null) {
                            publishComment(workDetail.getData(), (String) object);
                        }
                    }

                    @Override
                    public void onLongClick(View view, Object object) {

                    }
                });
                commentListPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                commentListPopupWindow.setCommentData(commentData);
                break;
            case R.id.tv_share:

                break;
        }
    }

    //------------------------------------全屏切换-------------------------------------------------

    private void toggleOrientation() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            smallScreen();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            fullScreen();
        }
    }


    private void fullScreen() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.videoContainer.getLayoutParams();
        layoutParams.width = CommonUtil.dip2px(WorkInfoActivity.this, CommonUtil.getScreenWidth(WorkInfoActivity.this));
        layoutParams.height = CommonUtil.dip2px(WorkInfoActivity.this, CommonUtil.getScreenHeight(WorkInfoActivity.this));
        binding.videoContainer.setLayoutParams(layoutParams);
        binding.currentDurationFull.setVisibility(View.VISIBLE);
        binding.currentDuration.setVisibility(View.GONE);
        binding.fullscreen.setVisibility(View.GONE);
        hideNavigationBar();
    }

    private void smallScreen() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.videoContainer.getLayoutParams();
        layoutParams.width = CommonUtil.dip2px(WorkInfoActivity.this, CommonUtil.getScreenWidth(WorkInfoActivity.this));
        layoutParams.height = CommonUtil.dip2px(WorkInfoActivity.this, 211);
        binding.videoContainer.setLayoutParams(layoutParams);
        binding.currentDurationFull.setVisibility(View.GONE);
        binding.currentDurationFull.setVisibility(View.GONE);
        binding.currentDuration.setVisibility(View.VISIBLE);
        binding.fullscreen.setVisibility(View.VISIBLE);
        showNavigationBar();
    }

    public void showNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    public void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            smallScreen();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
