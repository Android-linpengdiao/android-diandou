package com.okhttp;

import com.baselibrary.utils.CommonUtil;
import com.okhttp.callbacks.Callback;
import com.okhttp.utils.APIUrls;
import com.okhttp.utils.OkHttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SendRequest {
    private static String TAG = "SendRequest";

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param password_confirmation
     * @param authCode
     * @param is_register           第三方的身份 false:true
     * @param reg                   极光推送的regId
     * @param openid
     * @param qq_id
     * @param weibo_id
     * @param call
     */
    public static void register(String phone, String password, String password_confirmation, String authCode, String is_register, String reg, String openid, String qq_id, String weibo_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", phone);
        map.put("password_confirmation", phone);
        map.put("authCode", "");
        map.put("is_register", "");
        map.put("reg", "");
        map.put("openid", "");
        map.put("qq_id", "");
        map.put("weibo_id", "");
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_register).build().execute(call);

    }

    /**
     * 密码登录
     *
     * @param phone
     * @param password
     * @param call
     */
    public static void login(String phone, String password, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_login).build().execute(call);

    }

    /**
     * 忘记密码 时获取验证码
     *
     * @param phone
     * @param call
     */
    public static void phoneCode(String phone, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("key", "forget.password");
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_phoneCode).build().execute(call);

    }

    /**
     * 修改密码
     *
     * @param phone
     * @param code
     * @param password
     * @param againPassword
     * @param call
     */
    public static void updatePasswordAndLogin(String phone, String code, String password, String againPassword, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        map.put("password", password);
        map.put("againPassword", againPassword);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_updatePasswordAndLogin).build().execute(call);

    }

    /**
     * 注销用户
     *
     * @param tourist_id 类型下的唯一标识
     * @param call
     */
    public static void cancel(String tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_cancel).build().execute(call);

    }

    /**
     * 读取本地身份证信息
     *
     * @param tourist_id 类型下的唯一标识
     * @param call
     */
    public static void baseInfo(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_baseInfo).build().execute(call);

    }

    /**
     * 重置密码
     *
     * @param tourist_id
     * @param old_password
     * @param password
     * @param call
     */
    public static void resetPassword(String tourist_id, String old_password, String password, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("old_password", old_password);
        map.put("password", password);
        map.put("confirm_password", password);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_resetPassword).build().execute(call);

    }

    public static void commonStartUp(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonStartUp).build().execute(call);

    }

    /**
     * 获取轮播图
     *
     * @param call
     */
    public static void commonBanner(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonBanner).build().execute(call);

    }

    /**
     * 获取首页导航分类
     *
     * @param call
     */
    public static void commonNav(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonNav).build().execute(call);

    }

    /**
     * 用户搜索作品
     *
     * @param tourist_id 搜索人id(非必填)
     * @param type       1 最热 ；2 推荐
     * @param nav_id     分类id(非必填)
     * @param word       关键词搜索(非必填)
     * @param per_page   每页条数(非必填 默认10)
     * @param page       页数(非必填 默认1)
     * @param call
     */
    public static void searchWork(String tourist_id, int type, int nav_id, String word, int per_page, int page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("type", String.valueOf(type));
        map.put("nav_id", String.valueOf(nav_id));
        if (!CommonUtil.isBlank(word)) {
            map.put("word", word);
        }
        map.put("per_page", String.valueOf(per_page));
        map.put("page", String.valueOf(page));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_searchWork).build().execute(call);

    }

    /**
     * 获取作品详情
     *
     * @param content_id 作品ID
     * @param call
     */
    public static void workDetail(int content_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("content_id", String.valueOf(content_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_workDetail).build().execute(call);

    }

    /**
     * 获取我关注的人
     *
     * @param tourist_id 用户id（必填）.
     * @param per_page   每页条数（非必填 默认10）.
     * @param page       页数（非必填 默认1）.
     * @param call
     */
    public static void centerConcern(String tourist_id, String per_page, String page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("per_page", per_page);
        map.put("page", page);
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerConcern).build().execute(call);

    }

    public static void centerAttention(String tourist_id, String per_page, String page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("per_page", per_page);
        map.put("page", page);
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerAttention).build().execute(call);

    }

    public static void centerDiscuss(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerDiscuss).build().execute(call);

    }

    public static void centerFabulous(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerFabulous).build().execute(call);

    }

    public static void commonMessage(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonMessage).build().execute(call);

    }

    public static void centerSelfWork(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerSelfWork).build().execute(call);
    }

    public static void url_favouriteContent(int tourist_id, int per_page, int page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("per_page", String.valueOf(per_page));
        map.put("page", String.valueOf(page));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_favouriteContent).build().execute(call);
    }


    /**
     * 修改个人信息
     *
     * @param avatar
     * @param name
     * @param birth
     * @param sex
     * @param autograph
     * @param weibo
     * @param call
     */
    public static void editPersonal(String avatar, String name, String birth, String sex, String autograph, String weibo, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("avatar", avatar);
        map.put("name", name);
        map.put("birth", birth);
        map.put("sex", sex);
        map.put("autograph", autograph);
        map.put("weibo", weibo);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_editPersonal).build().execute(call);

    }

    public static void editPersonal(int tourist_id, int base_id, String avatar, String name, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("base_id", String.valueOf(base_id));
        map.put("avatar", avatar);
        map.put("name", name);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_editPersonal).build().execute(call);

    }

    /**
     * 举报
     *
     * @param tourist_id
     * @param report_id
     * @param report
     * @param call
     */
    public static void report(String tourist_id, String report_id, String report, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("report_id", report_id);
        map.put("report", report);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_report).build().execute(call);

    }


    /**
     * 举报
     *
     * @param tourist_id
     * @param face_photo //身份证正面照片
     * @param back_photo //身份证反面照片
     * @param call
     */
    public static void profile(String tourist_id, String face_photo, String back_photo, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("face_photo", face_photo);
        map.put("back_photo", back_photo);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_profile).build().execute(call);

    }

    /**
     * 上传文件
     *
     * @param file
     * @param call
     */
    public static void fileUpload(String file, String name, Callback call) {
        OkHttpUtils.getInstance().post().addFile("file", name, new File(file)).url(APIUrls.url_fileUpload).build().execute(call);

    }
}
