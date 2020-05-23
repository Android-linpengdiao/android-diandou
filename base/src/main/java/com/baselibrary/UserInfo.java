package com.baselibrary;

import java.io.Serializable;

public class UserInfo implements Serializable {

    /**
     * code : 200
     * msg : 登录成功
     * data : {"id":3,"name":"xu","phone":"13699162862","avatar":"upload/20200324084143gKEBF.jpg","password":"$2y$10$hoZjKOg.nwZ72VWGQDFukeCvzV8UfEVWUR3l47kdDPXm7JFHRibA2","remember_token":null,"settings":null,"created_at":"2020-03-24 12:24:42","updated_at":"2020-04-11 16:39:17","tourist_id":"64796762","sex":2,"birth":"2020-3-27","openid":"oH48X0_S-Uhjp-C-m4nK0S-bunRU","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":"加油加油","weibo":"","reg":"","qq_id":"CFA10663BDA8EFA1D50D09101CB74A8F","weibo_id":"1189548407","liker":0,"followers":0,"comment":0,"profile":{"id":1,"created_at":"2020-03-26 17:58:00","updated_at":"2020-03-27 19:06:05","face_photo":"upload/20200327070531Isgtu.jpg","back_photo":"upload/20200327070531u6HNg.jpg","status":2,"tourist_id":3}}
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

    public static class DataBean implements Serializable{
        /**
         * id : 3
         * name : xu
         * phone : 13699162862
         * avatar : upload/20200324084143gKEBF.jpg
         * password : $2y$10$hoZjKOg.nwZ72VWGQDFukeCvzV8UfEVWUR3l47kdDPXm7JFHRibA2
         * remember_token : null
         * settings : null
         * created_at : 2020-03-24 12:24:42
         * updated_at : 2020-04-11 16:39:17
         * tourist_id : 64796762
         * sex : 2
         * birth : 2020-3-27
         * openid : oH48X0_S-Uhjp-C-m4nK0S-bunRU
         * headimgurl : null
         * city : null
         * province : null
         * cancel : 1
         * autograph : 加油加油
         * weibo :
         * reg :
         * qq_id : CFA10663BDA8EFA1D50D09101CB74A8F
         * weibo_id : 1189548407
         * liker : 0
         * followers : 0
         * comment : 0
         * profile : {"id":1,"created_at":"2020-03-26 17:58:00","updated_at":"2020-03-27 19:06:05","face_photo":"upload/20200327070531Isgtu.jpg","back_photo":"upload/20200327070531u6HNg.jpg","status":2,"tourist_id":3}
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
        private String birth;
        private String openid;
        private Object headimgurl;
        private Object city;
        private Object province;
        private int cancel;
        private String autograph;
        private String weibo;
        private String reg;
        private String qq_id;
        private String weibo_id;
        private int liker;
        private int followers;
        private int comment;
        private ProfileBean profile;

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

        public ProfileBean getProfile() {
            return profile;
        }

        public void setProfile(ProfileBean profile) {
            this.profile = profile;
        }

        public static class ProfileBean implements Serializable{
            /**
             * id : 1
             * created_at : 2020-03-26 17:58:00
             * updated_at : 2020-03-27 19:06:05
             * face_photo : upload/20200327070531Isgtu.jpg
             * back_photo : upload/20200327070531u6HNg.jpg
             * status : 2
             * tourist_id : 3
             */

            private int id;
            private String created_at;
            private String updated_at;
            private String face_photo;
            private String back_photo;
            private int status;
            private int tourist_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getFace_photo() {
                return face_photo;
            }

            public void setFace_photo(String face_photo) {
                this.face_photo = face_photo;
            }

            public String getBack_photo() {
                return back_photo;
            }

            public void setBack_photo(String back_photo) {
                this.back_photo = back_photo;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getTourist_id() {
                return tourist_id;
            }

            public void setTourist_id(int tourist_id) {
                this.tourist_id = tourist_id;
            }
        }
    }
}
