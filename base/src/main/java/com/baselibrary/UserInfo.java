package com.baselibrary;

import java.io.Serializable;

public class UserInfo implements Serializable {

    /**
     * code : 200
     * msg : 登录成功
     * data : {"id":2,"name":"3v8bpk","phone":"13521614827","avatar":"users/default.png","password":"$2y$10$MTmB8DKF7rJaALu/n7zVu.kPVYskE0DqZzFRuf4YmeT8lW1Ps0CNS","remember_token":null,"settings":null,"created_at":"2020-03-24 11:54:40","updated_at":"2020-03-24 11:54:40","tourist_id":"72858237","sex":1,"birth":null,"openid":null,"headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0,"profile":null}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * name : 3v8bpk
         * phone : 13521614827
         * avatar : users/default.png
         * password : $2y$10$MTmB8DKF7rJaALu/n7zVu.kPVYskE0DqZzFRuf4YmeT8lW1Ps0CNS
         * remember_token : null
         * settings : null
         * created_at : 2020-03-24 11:54:40
         * updated_at : 2020-03-24 11:54:40
         * tourist_id : 72858237
         * sex : 1
         * birth : null
         * openid : null
         * headimgurl : null
         * city : null
         * province : null
         * cancel : 1
         * autograph : null
         * weibo : null
         * reg :
         * qq_id : null
         * weibo_id : null
         * liker : 0
         * followers : 0
         * comment : 0
         * profile : null
         */

        private int id;
        private String name;
        private String phone;
        private String avatar;
        private String password;
        private Object remember_token;
        private Object settings;
        private String created_at;
        private String updated_at;
        private String tourist_id;
        private int sex;
        private Object birth;
        private Object openid;
        private Object headimgurl;
        private Object city;
        private Object province;
        private int cancel;
        private Object autograph;
        private Object weibo;
        private String reg;
        private Object qq_id;
        private Object weibo_id;
        private int liker;
        private int followers;
        private int comment;
        private Object profile;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getRemember_token() {
            return remember_token;
        }

        public void setRemember_token(Object remember_token) {
            this.remember_token = remember_token;
        }

        public Object getSettings() {
            return settings;
        }

        public void setSettings(Object settings) {
            this.settings = settings;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getTourist_id() {
            return tourist_id;
        }

        public void setTourist_id(String tourist_id) {
            this.tourist_id = tourist_id;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getBirth() {
            return birth;
        }

        public void setBirth(Object birth) {
            this.birth = birth;
        }

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public Object getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(Object headimgurl) {
            this.headimgurl = headimgurl;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public int getCancel() {
            return cancel;
        }

        public void setCancel(int cancel) {
            this.cancel = cancel;
        }

        public Object getAutograph() {
            return autograph;
        }

        public void setAutograph(Object autograph) {
            this.autograph = autograph;
        }

        public Object getWeibo() {
            return weibo;
        }

        public void setWeibo(Object weibo) {
            this.weibo = weibo;
        }

        public String getReg() {
            return reg;
        }

        public void setReg(String reg) {
            this.reg = reg;
        }

        public Object getQq_id() {
            return qq_id;
        }

        public void setQq_id(Object qq_id) {
            this.qq_id = qq_id;
        }

        public Object getWeibo_id() {
            return weibo_id;
        }

        public void setWeibo_id(Object weibo_id) {
            this.weibo_id = weibo_id;
        }

        public int getLiker() {
            return liker;
        }

        public void setLiker(int liker) {
            this.liker = liker;
        }

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public Object getProfile() {
            return profile;
        }

        public void setProfile(Object profile) {
            this.profile = profile;
        }
    }
}
