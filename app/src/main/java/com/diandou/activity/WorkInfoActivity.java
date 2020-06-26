package com.diandou.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.baselibrary.Constants;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.GlideLoader;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.WorkAdapter;
import com.diandou.databinding.ActivityWorkInfoBinding;
import com.diandou.model.BaseData;
import com.diandou.model.CommentData;
import com.diandou.model.WorkData;
import com.diandou.model.WorkDetail;
import com.diandou.view.CommentListPopupWindow;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.ObservableScrollView;
import com.diandou.view.OnClickListener;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.callbacks.StringCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;
import com.okhttp.utils.APIUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Request;

public class WorkInfoActivity extends BaseActivity implements View.OnClickListener, ObservableScrollView.ScrollViewListener {

    private ActivityWorkInfoBinding binding;
    private WorkAdapter adapter;
    private int id;

    private WorkDetail workDetail;
    private CommentData commentData;
    private WorkData workData;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_info);

        binding.playerBack.setOnClickListener(this);
        binding.fullscreen.setOnClickListener(this);
        binding.tvFollower.setOnClickListener(this);
        binding.tvAppreciate.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvShare.setOnClickListener(this);

        adapter = new WorkAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setNestedScrollingEnabled(false);
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 10));
        binding.recyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.recyclerView.setAdapter(adapter);

        // 手势
        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                try {
                    float XFrom = e1.getX();
                    float XTo = e2.getX();
                    float YFrom = e1.getY();
                    float YTo = e2.getY();
                    // 左右滑动的X轴幅度大于100，并且X轴方向的速度大于100
                    if (Math.abs(YFrom - YTo) > 100.0f && Math.abs(velocityY) > 100.0f) {
                        // X轴幅度大于Y轴的幅度
                        if (Math.abs(YFrom - YTo) >= Math.abs(XFrom - XTo)) {
                            if (YFrom > YTo) {
                                if (binding.bottomView.isShown()) {
                                    binding.bottomView.setVisibility(View.GONE);
                                    Animation animationExit = AnimationUtils.loadAnimation(getApplication(), R.anim.bottom_exit);
                                    LinearInterpolator lin = new LinearInterpolator();
                                    animationExit.setInterpolator(lin);
                                    animationExit.setRepeatCount(-1);
                                    binding.bottomView.startAnimation(animationExit);
                                }
                            } else {
                                if (!binding.bottomView.isShown()) {
                                    binding.bottomView.setVisibility(View.VISIBLE);
                                    Animation animationEnter = AnimationUtils.loadAnimation(getApplication(), R.anim.bottom_enter);
                                    LinearInterpolator lin = new LinearInterpolator();
                                    animationEnter.setInterpolator(lin);
                                    animationEnter.setRepeatCount(-1);
                                    binding.bottomView.startAnimation(animationEnter);
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }
        });

        initData();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
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
        SendRequest.workDetail(getUserInfo().getData() != null ? getUserInfo().getData().getId() : -1, id, new GenericsCallback<WorkDetail>(new JsonGenericsSerializator()) {

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

    private void playTime() {
        SendRequest.playTime(id, new GenericsCallback<BaseData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseData response, int id) {

            }
        });
    }

    private void initView(WorkDetail.DataBean data) {

        binding.viewLayout.setScrollViewListener(this);
        binding.viewLayout.setVisibility(View.VISIBLE);
        binding.bottomView.setVisibility(View.VISIBLE);

        binding.tvDesc.setText(data.getDesc());
        binding.tvAddr.setText(data.getAddr());
        binding.tvTime.setText(data.getUpdated_at());
        binding.tvFollower.setText(String.valueOf(data.getFollower_num()));
        binding.tvFollower.setSelected(data.isFollower_status());
        binding.tvAppreciate.setText(String.valueOf(data.getAssist_num()));
        binding.tvAppreciate.setSelected(data.isAssist_status());
        GlideLoader.LoderImage(WorkInfoActivity.this, data.getImg(), binding.thumbnails);

        getVideos(data);
        showContentComment(data);

        if (!CommonUtil.isBlank(data.getLink())) {
            try {
                JSONArray jsonArray = new JSONArray(data.getLink());
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.optJSONObject(0);
                    playVideo(jsonObject.optString("download_link"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getVideos(WorkDetail.DataBean data) {
        SendRequest.searchWorkType(data.getNav_id(), Constants.perPage, workData != null ? workData.getData().getCurrent_page() + 1 : 1, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(WorkData response, int id) {
                workData = response;
                if (response.getCode() == 200) {
                    adapter.loadMoreData(response.getData().getData());
                } else {
                    ToastUtils.showShort(WorkInfoActivity.this, response.getMsg());
                }
            }

        });

    }

    private void centerFollow(final WorkDetail data, String followUrl) {
        SendRequest.centerFollow(getUserInfo().getData().getId(), data.getData().getTourist_id(), followUrl, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            binding.tvFollower.setSelected(!binding.tvFollower.isSelected());
                            data.getData().setFollower_num(binding.tvFollower.isSelected() ? data.getData().getFollower_num() + 1 : data.getData().getFollower_num() - 1);
                            binding.tvFollower.setText(data.getData().getFollower_num() + "");
                            if (binding.tvFollower.isSelected()) {
                                ToastUtils.showShort(WorkInfoActivity.this, "已关注");
                            }
                        } else {
                            ToastUtils.showShort(WorkInfoActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(WorkInfoActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(WorkInfoActivity.this, "请求失败");
                }

            }
        });

    }

    private void publishContentAssist(final WorkDetail data, String assistUrl) {
        SendRequest.publishContentAssist(getUserInfo().getData().getId(), data.getData().getId(), assistUrl, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200) {
                            binding.tvAppreciate.setSelected(!binding.tvAppreciate.isSelected());
                            data.getData().setAssist(binding.tvAppreciate.isSelected() ? data.getData().getAssist() + 1 : data.getData().getAssist() - 1);
                            binding.tvAppreciate.setText(data.getData().getAssist() + "");
                            if (binding.tvAppreciate.isSelected()) {
                                ToastUtils.showShort(WorkInfoActivity.this, "已点赞");
                            }
                        } else {
                            ToastUtils.showShort(WorkInfoActivity.this, jsonObject.optString("msg"));
                        }
                    } else {
                        ToastUtils.showShort(WorkInfoActivity.this, "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(WorkInfoActivity.this, "请求失败");
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
                    if (jsonObject.optInt("code") == 200) {
                        if (workDetail != null && workDetail.getData() != null) {
                            showContentComment(workDetail.getData());
                        }
                    } else {
                        ToastUtils.showShort(getApplication(), jsonObject.optString("msg"));
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
            case R.id.player_back:
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
            case R.id.tv_follower:
                if (getUserInfo().getData() != null) {
                    if (workDetail != null && workDetail.getData() != null) {
                        String url = binding.tvFollower.isSelected() ? APIUrls.url_centerUnFollow : APIUrls.url_centerFollow;
                        centerFollow(workDetail, url);
                    }
                } else {
                    openActivity(LoginActivity.class);
                }
                break;
            case R.id.tv_appreciate:
                if (getUserInfo().getData() != null) {
                    if (workDetail != null && workDetail.getData() != null) {
                        String url = binding.tvAppreciate.isSelected() ? APIUrls.url_publishCommentDeleteAssist : APIUrls.url_publishCommentAssist;
                        publishContentAssist(workDetail, url);
                    }
                } else {
                    openActivity(LoginActivity.class);
                }
                break;
            case R.id.tv_comment:
                if (getUserInfo().getData() != null) {
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

                } else {
                    openActivity(LoginActivity.class);
                }
                break;
            case R.id.tv_share:
                shareView(WorkInfoActivity.this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stop();
        destroy();
        stopUpdateTimer();
        progressUpdateTimer = null;
        super.onDestroy();
    }

    private void showVideoProgressInfo() {
        if (mPlayer != null && !inSeek) {
            int curPosition = mPlayer.getCurrentPosition();
            int duration = mPlayer.getDuration();
            int bufferPosition = mPlayer.getBufferPosition();
            binding.currentDuration.setText(CommonUtil.Formatter.formatTime(curPosition));
            binding.totalDuration.setText(CommonUtil.Formatter.formatTime(duration));
            binding.progress.setMax(duration);
            binding.progress.setSecondaryProgress(bufferPosition);
            binding.progress.setProgress(curPosition);
        }
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(0);
            progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
        }
    }

    private void stopUpdateTimer() {
        if (progressUpdateTimer != null) {
            progressUpdateTimer.removeMessages(0);
        }
    }

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showVideoProgressInfo();
        }
    };
    private static final String TAG = "WorkInfoActivity";

    private void playVideo(String videoUrl) {
        Log.i(TAG, "playVideo: " + videoUrl);
        binding.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
                holder.setKeepScreenOn(true);
                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
                if (mPlayer != null) {
                    mPlayer.setVideoSurface(holder.getSurface());
                }

            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                if (mPlayer != null) {
                    mPlayer.setSurfaceChanged();
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
        binding.videoPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    binding.videoPlay.setImageResource(R.drawable.video_play);
                    mPlayer.pause();
                } else {
                    binding.videoPlay.setImageResource(R.drawable.video_pause);
                    mPlayer.play();
                }
            }
        });


        mPlayer = new AliVcMediaPlayer(WorkInfoActivity.this, binding.surfaceView);
        mPlayer.setCirclePlay(true);

        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {
                binding.surfaceView.setBackgroundColor(Color.TRANSPARENT);
                binding.loading.setVisibility(View.GONE);
                binding.thumbnails.setVisibility(View.GONE);
                if (mPlayer != null) {
                    mPlayer.play();
                    playTime();
                }

            }
        });
//        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
//        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
        mPlayer.setFrameInfoListener(new MediaPlayer.MediaPlayerFrameInfoListener() {
            @Override
            public void onFrameInfoListener() {
                binding.videoPlay.setImageResource(R.drawable.video_pause);
                binding.thumbnails.animate().alpha(0).setDuration(200).start();
                showVideoProgressInfo();
            }
        });
        mPlayer.setErrorListener(new MediaPlayer.MediaPlayerErrorListener() {
            @Override
            public void onError(int i, String s) {
                ToastUtils.showShort(getApplicationContext(), "网络连接似乎出现问题，请重试");
            }
        });
        mPlayer.setCompletedListener(new MediaPlayer.MediaPlayerCompletedListener() {
            @Override
            public void onCompleted() {
                isCompleted = true;
                showVideoProgressInfo();
                stopUpdateTimer();
            }
        });
        mPlayer.setSeekCompleteListener(new MediaPlayer.MediaPlayerSeekCompleteListener() {
            @Override
            public void onSeekCompleted() {
                inSeek = false;
            }
        });
        mPlayer.setStoppedListener(new MediaPlayer.MediaPlayerStoppedListener() {
            @Override
            public void onStopped() {
                binding.videoPlay.setImageResource(R.drawable.video_play);
            }
        });
        mPlayer.enableNativeLog();
        if (mPlayer != null) {
            mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        }
        mPlayer.prepareToPlay(videoUrl);
        binding.loading.setVisibility(View.VISIBLE);

    }

    private boolean inSeek = false;
    private boolean isCompleted = false;

    private AliVcMediaPlayer mPlayer;

    private void start() {
        if (mPlayer != null) {
//            mPlayer.prepareToPlay(url1);
        }
    }

    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    private void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    private void resume() {
        if (mPlayer != null) {
            mPlayer.play();
        }
    }

    private void destroy() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.destroy();
        }
    }

    private void replay() {
        stop();
        start();
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
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        binding.videoContainer.setLayoutParams(layoutParams);
        binding.currentDuration.setVisibility(View.GONE);
        binding.fullscreen.setVisibility(View.GONE);
        hideNavigationBar();
    }

    private void smallScreen() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.videoContainer.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = CommonUtil.dip2px(WorkInfoActivity.this, 211);
        binding.videoContainer.setLayoutParams(layoutParams);
        binding.currentDuration.setVisibility(View.VISIBLE);
        binding.fullscreen.setVisibility(View.VISIBLE);
        showNavigationBar();
    }

    public void showNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

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

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

    }

    @Override
    public void onScrollToEnd() {
        Log.i(TAG, "onScrollToEnd: ");
        if (workDetail != null && workDetail.getData() != null) {
            getVideos(workDetail.getData());
        }
    }
}
