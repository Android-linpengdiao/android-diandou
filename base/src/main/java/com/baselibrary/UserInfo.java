package com.baselibrary;

import java.io.Serializable;

public class UserInfo implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : {"id":2,"name":"Android","phone":"13521614827","avatar":"upload/202006170812198laPK.jpg","password":"$2y$10$FpVca5kl1QmNHxM5tsOZI.9rH3NKEHkP38Ts5ZRJGQNx33IIzGN2i","remember_token":null,"settings":null,"created_at":"2020-03-24 11:54:40","updated_at":"2020-06-24 12:45:38","tourist_id":"2","sex":1,"birth":"","openid":null,"headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":"","weibo":"","reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":2,"comment":0,"attention":1,"concern":2,"liker_num":2,"profile":null,"content_num":2,"assist_num":2}
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

    public static class DataBean implements Serializable {
        /**
         * id : 2
         * name : Android
         * phone : 13521614827
         * avatar : upload/202006170812198laPK.jpg
         * password : $2y$10$FpVca5kl1QmNHxM5tsOZI.9rH3NKEHkP38Ts5ZRJGQNx33IIzGN2i
         * remember_token : null
         * settings : null
         * created_at : 2020-03-24 11:54:40
         * updated_at : 2020-06-24 12:45:38
         * tourist_id : 2
         * sex : 1
         * birth :
         * openid : null
         * headimgurl : null
         * city : null
         * province : null
         * cancel : 1
         * autograph :
         * weibo :
         * reg :
         * qq_id : null
         * weibo_id : null
         * liker : 0
         * followers : 2
         * comment : 0
         * attention : 1
         * concern : 2
         * liker_num : 2
         * profile : null
         * content_num : 2
         * assist_num : 2
         */

        private int id;
        private String name;
        private String phone;
        private String avatar;
        private String password;
        private String remember_token;
        private String settings;
        private String created_at;
        private String updated_at;
        private String tourist_id;
        private int sex;
        private String birth;
        private String openid;
        private String headimgurl;
        private String city;
        private String province;
        private int cancel;
        private String autograph;
        private String weibo;
        private String reg;
        private String qq_id;
        private String weibo_id;
        private int liker;
        private int followers;
        private int comment;
        private int attention;
        private int concern;
        private int liker_num;
        private Object profile;
        private int content_num;
        private int assist_num;

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

        public String getRemember_token() {
            return remember_token;
        }

        public void setRemember_token(String remember_token) {
            this.remember_token = remember_token;
        }

        public String getSettings() {
            return settings;
        }

        public void setSettings(String settings) {
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

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getCancel() {
            return cancel;
        }

        public void setCancel(int cancel) {
            this.cancel = cancel;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getWeibo() {
            return weibo;
        }

        public void setWeibo(String weibo) {
            this.weibo = weibo;
        }

        public String getReg() {
            return reg;
        }

        public void setReg(String reg) {
            this.reg = reg;
        }

        public String getQq_id() {
            return qq_id;
        }

        public void setQq_id(String qq_id) {
            this.qq_id = qq_id;
        }

        public String getWeibo_id() {
            return weibo_id;
        }

        public void setWeibo_id(String weibo_id) {
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

        public int getAttention() {
            return attention;
        }

        public void setAttention(int attention) {
            this.attention = attention;
        }

        public int getConcern() {
            return concern;
        }

        public void setConcern(int concern) {
            this.concern = concern;
        }

        public int getLiker_num() {
            return liker_num;
        }

        public void setLiker_num(int liker_num) {
            this.liker_num = liker_num;
        }

        public Object getProfile() {
            return profile;
        }

        public void setProfile(Object profile) {
            this.profile = profile;
        }

        public int getContent_num() {
            return content_num;
        }

        public void setContent_num(int content_num) {
            this.content_num = content_num;
        }

        public int getAssist_num() {
            return assist_num;
        }

        public void setAssist_num(int assist_num) {
            this.assist_num = assist_num;
        }
    }
}
