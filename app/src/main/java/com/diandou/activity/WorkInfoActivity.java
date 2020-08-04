package com.diandou.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

//import com.alivc.player.AliVcMediaPlayer;
//import com.alivc.player.MediaPlayer;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.bean.InfoCode;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.UrlSource;
import com.baselibrary.Constants;
import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.FileUtils;
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

import java.util.Date;

import okhttp3.Call;
import okhttp3.Request;

public class WorkInfoActivity extends BaseActivity implements View.OnClickListener, ObservableScrollView.ScrollViewListener {

    private static final String TAG = "WorkInfoActivity";
    private ActivityWorkInfoBinding binding;
    private WorkAdapter adapter;
    private int id;

    private WorkDetail workDetail;
    private CommentData commentData;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_info);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setStatusBarDarkTheme(false);

        binding.playerBack.setOnClickListener(this);
        binding.videoPlay.setOnClickListener(this);
        binding.fullscreen.setOnClickListener(this);
        binding.tvFollower.setOnClickListener(this);
        binding.tvAppreciate.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvShare.setOnClickListener(this);
        binding.progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (aliyunVodPlayer != null) {
                    aliyunVodPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

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

        initPlayer();
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

        if (CommonUtil.isBlank(getUserInfo().getData()) || !CommonUtil.isBlank(getUserInfo().getData()) && data.getTourist_id() != getUserInfo().getData().getId()) {
            binding.tousuView.setVisibility(View.VISIBLE);
        } else {
            binding.tousuView.setVisibility(View.GONE);
        }

        binding.tousuView.setOnClickListener(this);
        binding.viewLayout.setScrollViewListener(this);
        binding.viewLayout.setVisibility(View.VISIBLE);
        binding.bottomView.setVisibility(View.VISIBLE);

        binding.tvDesc.setText(data.getDesc());
        binding.tvAddr.setText(data.getAddr());
        binding.tvTime.setText(data.getUpdated_at());
        binding.tvFollower.setText(String.valueOf(data.getFollower_num()));
//        binding.tvFollower.setSelected(data.isFollower_status());
        binding.tvAppreciate.setText(String.valueOf(data.getAssist_num()));
        binding.tvAppreciate.setSelected(data.isAssist_status());
        GlideLoader.LoderImage(WorkInfoActivity.this, data.getImg(), binding.thumbnails);

        getVideos(data);
        showContentComment(data);
        if (getUserInfo().getData() != null && data != null && data.getTourist() != null) {
            isFollow(data);
        }

        if (!CommonUtil.isBlank(data.getLink())) {
            try {
                JSONArray jsonArray = new JSONArray(data.getLink());
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.optJSONObject(0);

                    UrlSource UrlSource = new UrlSource();
                    UrlSource.setUri(jsonObject.optString("download_link"));
                    aliyunVodPlayer.setDataSource(UrlSource);
                    aliyunVodPlayer.prepare();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getVideos(WorkDetail.DataBean data) {
//        SendRequest.searchWorkType(data.getNav_id(), Constants.perPage, workData != null && workData.getData() != null ? workData.getData().getCurrent_page() + 1 : 1, new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(WorkData response, int id) {
//                workData = response;
//                if (response.getCode() == 200) {
//                    adapter.loadMoreData(response.getData().getData());
//                } else {
//                    ToastUtils.showShort(WorkInfoActivity.this, response.getMsg());
//                }
//            }
//
//        });

        SendRequest.centerSelfWork(data.getTourist().getId(), new GenericsCallback<WorkData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(WorkData response, int id) {
                if (response.getCode() == 200 && response.getData() != null && response.getData().getData() != null) {
                    adapter.refreshData(response.getData().getData());
                } else {
                    ToastUtils.showShort(getApplication(), response.getMsg());
                }
            }

        });

    }

    private void isFollow(final WorkDetail.DataBean data) {
        SendRequest.isFollow(getUserInfo().getData().getId(), data.getTourist().getId(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!CommonUtil.isBlank(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("code") == 200
                                && !CommonUtil.isBlank(jsonObject.optJSONObject("data"))
                                && !CommonUtil.isBlank(jsonObject.optJSONObject("data").optString("id"))) {
                            binding.tvFollower.setSelected(true);
                            binding.tvFollower.setText(String.valueOf(data.getFollower_num()));
                        } else {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                            binding.tvFollower.setText((data.getData().getFollower_num()>0?data.getData().getFollower_num():0) + "");
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
            case R.id.video_play:
                if (binding.videoPlay.isSelected()) {
                    aliyunVodPlayer.pause();
                } else {
                    aliyunVodPlayer.start();
                }
                binding.videoPlay.setSelected(!binding.videoPlay.isSelected());
                break;
            case R.id.fullscreen:
                toggleOrientation();
                break;
            case R.id.tousuView:
                if (getUserInfo().getData() != null) {
                    if (workDetail != null && workDetail.getData() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",id);
                        openActivity(TousuActivity.class,bundle);
                    }
                } else {
                    openActivity(LoginActivity.class);
                }
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
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.pause();
            binding.videoPlay.setSelected(false);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (aliyunVodPlayer != null) {
            aliyunVodPlayer.release();
        }
        super.onDestroy();
    }


    /**
     * ================================ 播放器 ================================================
     */

    private AliPlayer aliyunVodPlayer;

    private void initPlayer() {
        aliyunVodPlayer = AliPlayerFactory.createAliPlayer(getApplicationContext());

        binding.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                aliyunVodPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                aliyunVodPlayer.redraw();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                aliyunVodPlayer.setDisplay(null);
            }
        });

        aliyunVodPlayer.setOnCompletionListener(new IPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                //播放完成事件
                binding.videoPlay.setSelected(false);
//                aliyunVodPlayer.reset();
                binding.currentDuration.setText(CommonUtil.Formatter.formatTime(0));
                binding.progress.setProgress(0);
                aliyunVodPlayer.seekTo(1);
            }
        });
        aliyunVodPlayer.setOnErrorListener(new IPlayer.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                //出错事件
            }
        });
        aliyunVodPlayer.setOnPreparedListener(new IPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                Log.i(TAG, "onPrepared: ");
                //准备成功事件
                aliyunVodPlayer.start();
                playTime();
            }
        });
        aliyunVodPlayer.setOnVideoSizeChangedListener(new IPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(int width, int height) {
                //视频分辨率变化回调
            }
        });
        aliyunVodPlayer.setOnRenderingStartListener(new IPlayer.OnRenderingStartListener() {
            @Override
            public void onRenderingStart() {
                Log.i(TAG, "onRenderingStart: ");
                //首帧渲染显示事件
                binding.surfaceView.setBackgroundColor(Color.TRANSPARENT);
                binding.loading.setVisibility(View.GONE);
                binding.thumbnails.setVisibility(View.GONE);
                binding.videoPlay.setSelected(true);
//                showVideoProgressInfo();
            }
        });
        aliyunVodPlayer.setOnInfoListener(new IPlayer.OnInfoListener() {
            @Override
            public void onInfo(InfoBean infoBean) {
                //其他信息的事件，type包括了：循环播放开始，缓冲位置，当前播放位置，自动播放开始等
                if (infoBean.getCode() == InfoCode.CurrentPosition) {
                    binding.currentDuration.setText(CommonUtil.Formatter.formatTime((int) infoBean.getExtraValue()));
                    binding.totalDuration.setText(CommonUtil.Formatter.formatTime((int) aliyunVodPlayer.getDuration()));
                    binding.progress.setProgress((int) infoBean.getExtraValue());
                    binding.progress.setMax((int) aliyunVodPlayer.getDuration());
                }
            }

        });
        aliyunVodPlayer.setOnLoadingStatusListener(new IPlayer.OnLoadingStatusListener() {
            @Override
            public void onLoadingBegin() {
                //缓冲开始。
            }

            @Override
            public void onLoadingProgress(int percent, float kbps) {
                //缓冲进度
            }

            @Override
            public void onLoadingEnd() {
                //缓冲结束
            }
        });
        aliyunVodPlayer.setOnSeekCompleteListener(new IPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                //拖动结束
            }
        });
        aliyunVodPlayer.setOnSubtitleDisplayListener(new IPlayer.OnSubtitleDisplayListener() {
            @Override
            public void onSubtitleExtAdded(int i, String s) {

            }

            @Override
            public void onSubtitleShow(int i, long l, String s) {

            }

            @Override
            public void onSubtitleHide(int i, long l) {

            }
        });
        aliyunVodPlayer.setOnTrackChangedListener(new IPlayer.OnTrackChangedListener() {
            @Override
            public void onChangedSuccess(TrackInfo trackInfo) {
                //切换音视频流或者清晰度成功
            }

            @Override
            public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
                //切换音视频流或者清晰度失败
            }
        });
        aliyunVodPlayer.setOnStateChangedListener(new IPlayer.OnStateChangedListener() {
            @Override
            public void onStateChanged(int newState) {
                //播放器状态改变事件
            }
        });
        aliyunVodPlayer.setOnSnapShotListener(new IPlayer.OnSnapShotListener() {
            @Override
            public void onSnapShot(Bitmap bm, int with, int height) {
                //截图事件
            }
        });

        //先获取配置
        PlayerConfig config = aliyunVodPlayer.getConfig();
        //设置referer
        config.mStartBufferDuration = 1;
        //设置配置给播放器
        aliyunVodPlayer.setConfig(config);
        //循环播放
        aliyunVodPlayer.setLoop(true);


//        //创建UrlSource
//        UrlSource UrlSource = new UrlSource();
//        UrlSource.setUri(videoUrl);
//        //设置播放源
//        aliyunVodPlayer.setDataSource(UrlSource);
//
//        //准备播放
//        aliyunVodPlayer.prepare();

//        // 开始播放。
//        aliyunVodPlayer.start();
//        //暂停播放
//        aliyunVodPlayer.pause();
//        //停止播放
//        aliyunVodPlayer.stop();
//        // 跳转到。不精准。
////        aliyunVodPlayer.seekTo(long position);
//        // 重置
//        aliyunVodPlayer.reset();
//        //释放。释放后播放器将不可再被使用。
//        aliyunVodPlayer.release();
//
//        //循环播放
//        aliyunVodPlayer.setLoop(true);
//
//
//        //设置画面的镜像模式：水平镜像，垂直镜像，无镜像。
//        aliyunVodPlayer.setMirrorMode(IPlayer.MirrorMode.MIRROR_MODE_NONE);
//        //设置画面旋转模式：旋转0度，90度，180度，270度
//        aliyunVodPlayer.setRotateMode(IPlayer.RotateMode.ROTATE_0);
//        //设置画面缩放模式：宽高比填充，宽高比适应，拉伸填充
//        aliyunVodPlayer.setScaleMode(IPlayer.ScaleMode.SCALE_ASPECT_FIT);
//
//        //设置播放器静音
//        aliyunVodPlayer.setMute(true);
//        //设置播放器音量,范围0~1.
//        aliyunVodPlayer.setVolume(1f);
//
//
//        //设置倍速播放:支持0.5~2倍速的播放
//        aliyunVodPlayer.setSpeed(1.0f);
//
//        //设置倍速播放:支持0.5~2倍速的播放
//        aliyunVodPlayer.setSpeed(1.0f);
//
//        //设置截图回调
//        aliyunVodPlayer.setOnSnapShotListener(new IPlayer.OnSnapShotListener() {
//            @Override
//            public void onSnapShot(Bitmap bm, int with, int height) {
//                //获取到的bitmap。以及图片的宽高。
//            }
//        });
//        //截取当前播放的画面
//        aliyunVodPlayer.snapshot();
//
//        CacheConfig cacheConfig = new CacheConfig();
//        //开启缓存功能
//        cacheConfig.mEnable = true;
//        //能够缓存的单个文件最大时长。超过此长度则不缓存
//        cacheConfig.mMaxDurationS = 100;
//        //缓存目录的位置
//        cacheConfig.mDir = FileUtils.getMediaPath();
//        //缓存目录的最大大小。超过此大小，将会删除最旧的缓存文件
//        cacheConfig.mMaxSizeMB = 200;
//        //设置缓存配置给到播放器
//        aliyunVodPlayer.setCacheConfig(cacheConfig);

    }


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
        if (workDetail != null && workDetail.getData() != null) {
            getVideos(workDetail.getData());
        }
    }
}
