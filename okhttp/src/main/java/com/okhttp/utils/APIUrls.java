package com.okhttp.utils;

public class APIUrls {


    public final static String url_domain = "http://www.udiandou.com/";

    public final static String url_fileUpload = url_domain + "api/common/fileUpload";
    public final static String url_ossFileUpload = url_domain + "api/common/ossFileUpload";
//    public final static String url_createSecurityToken = url_domain + "api/common/token";
    public final static String url_createSecurityToken = url_domain + "api/common/stsToken";
    public final static String url_protocol = url_domain + "upload/20200808092346og6bK.html";


    //用户信息
    public final static String url_phoneCode = url_domain + "api/common/phoneCode";
    public final static String url_phoneLogin = url_domain + "api/common/phoneLogin";
    public final static String url_login = url_domain + "api/common/login";
    public final static String url_thirdLogin = url_domain + "api/common/thirdLogin";
    public final static String url_updatePasswordAndLogin = url_domain + "api/common/phoneCode";
    public final static String url_register = url_domain + "api/common/register";
    //判断用户是否绑定过三方
    public final static String isBindThird = url_domain + "api/center/isBindThird";
    public final static String url_forgetPassword = url_domain + "api/common/forgetPassword";
    public final static String url_cancel = url_domain + "api/center/cancel";
    public final static String url_baseInfo = url_domain + "api/center/baseInfo";
    public final static String url_isFollow = url_domain + "api/center/IsFollow";
    public final static String url_resetPassword = url_domain + "api/common/resetPassword";
    public final static String url_editPersonal = url_domain + "api/center/editPersonal";
    public final static String url_report = url_domain + "api/center/report";
    public final static String url_profile = url_domain + "api/center/profile";

    public final static String url_commonStartUp = url_domain + "api/common/startUp";
    public final static String url_commonNav = url_domain + "api/common/nav";
    public final static String url_commonBanner = url_domain + "api/common/banner";

    //用户搜索作品
    public final static String url_searchWork = url_domain + "api/center/searchWork";
    //获取作品详情
    public final static String url_workDetail = url_domain + "api/center/workDetail";
    //删除自己作品
    public final static String url_deleteContent = url_domain + "api/center/deleteContent";
    //删除点赞作品
    public final static String url_deleteAssist = url_domain + "api/center/deleteAssist";
    //增加播放次数
    public final static String url_playTime = url_domain + "api/center/playTime";
    //获取我关注的人
    public final static String url_centerConcern = url_domain + "api/center/concern";
    //查看谁评论了我
    public final static String url_centerDiscuss = url_domain + "api/center/discuss";
    // 查看谁赞了我我的作品
    public final static String url_centerFabulous = url_domain + "api/center/fabulous";
    // 获取关注我的人
    public final static String url_centerAttention = url_domain + "api/center/attention";
    //取消关注用户接口
    public final static String url_centerUnFollow = url_domain + "api/center/unFollow";
    //关注用户接口
    public final static String url_centerFollow = url_domain + "api/center/follow";

    //查看作品是否被点赞
    public final static String url_contentIsAssist = url_domain + "api/center/contentIsAssist";
    //对作品取消点赞
    public final static String url_publishCommentDeleteAssist = url_domain + "api/center/publishCommentDeleteAssist";
    //对作品点赞
    public final static String url_publishCommentAssist = url_domain + "api/center/publishCommentAssist";
    //展示作品的评论
    public final static String url_showContentComment = url_domain + "api/center/showContentComment";
    //发布对作品的评论
    public final static String url_publishComment = url_domain + "api/center/publishComment";
    //获取用户作品
    public final static String url_centerSelfWork = url_domain + "api/center/selfWork";
    //获取用户最爱的作品
    public final static String url_favouriteContent = url_domain + "api/center/favouriteContent";
    //用户发表作品
    public final static String url_publishWork = url_domain + "api/center/publishWork";
    //获取用户的点赞
    public final static String url_centerAssist = url_domain + "api/center/assist";
    // 作品投诉
    public final static String url_centerTip = url_domain + "api/center/tip";

    //获取系统消息
    public final static String url_commonMessage = url_domain + "api/common/message";

    //关于我们
    public final static String url_commonAbout = url_domain + "api/common/about";
    //意见反馈
    public final static String url_centerComment = url_domain + "api/center/comment";


}
