package com.okhttp.utils;

public class APIUrls {


    public final static String url_domain = "http://admin.udiandou.com/";

    public final static String url_fileUpload = url_domain + "api/common/fileUpload";
    public final static String url_createSecurityToken = url_domain + "http://store.chuinp.com/storage/createSecurityToken?session=";

    //用户信息
    public final static String url_login = url_domain + "api/common/login";
    public final static String url_phoneCode = url_domain + "api/common/phoneCode";
    public final static String url_updatePasswordAndLogin = url_domain + "api/common/phoneCode";
    public final static String url_register = url_domain + "api/common/register";
    public final static String url_cancel = url_domain + "api/center/cancel";
    public final static String url_baseInfo = url_domain + "api/center/baseInfo";
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
    //获取我关注的人
    public final static String url_centerConcern = url_domain + "api/center/concern";
    //查看谁评论了我
    public final static String url_centerDiscuss = url_domain + "api/center/discuss";
    // 查看谁赞了我我的作品
    public final static String url_centerFabulous = url_domain + "api/center/fabulous";
    // 获取关注我的人
    public final static String url_centerAttention = url_domain + "api/center/attention";
    //获取系统消息
    public final static String url_commonMessage = url_domain + "api/common/message";


}
