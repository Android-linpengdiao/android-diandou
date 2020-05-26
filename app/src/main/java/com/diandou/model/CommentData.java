package com.diandou.model;

import java.util.List;

public class CommentData {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":20,"created_at":"2020-04-27 01:04:05","updated_at":"2020-04-27 01:04:05","status":1,"tourist_id":12,"content_id":26,"parent_id":0,"body":"25555855","root_id":0,"child":[],"tourist":{"id":12,"name":"LE0sR3","phone":"18335929380","avatar":"users/default.png","password":"$2y$10$r4ig8Pxx2I8vnwKTOyCzje2VI0dUtcUvHFuSUj3Z2Kq8l4CyWT4Z.","remember_token":null,"settings":null,"created_at":"2020-04-21 20:52:41","updated_at":"2020-04-21 20:52:41","tourist_id":"80841516","sex":1,"birth":null,"openid":null,"headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0}}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 20
         * created_at : 2020-04-27 01:04:05
         * updated_at : 2020-04-27 01:04:05
         * status : 1
         * tourist_id : 12
         * content_id : 26
         * parent_id : 0
         * body : 25555855
         * root_id : 0
         * child : []
         * tourist : {"id":12,"name":"LE0sR3","phone":"18335929380","avatar":"users/default.png","password":"$2y$10$r4ig8Pxx2I8vnwKTOyCzje2VI0dUtcUvHFuSUj3Z2Kq8l4CyWT4Z.","remember_token":null,"settings":null,"created_at":"2020-04-21 20:52:41","updated_at":"2020-04-21 20:52:41","tourist_id":"80841516","sex":1,"birth":null,"openid":null,"headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0}
         */

        private int id;
        private String created_at;
        private String updated_at;
        private int status;
        private int tourist_id;
        private int content_id;
        private int parent_id;
        private String body;
        private int root_id;
        private TouristBean tourist;
        private List<?> child;

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

        public int getContent_id() {
            return content_id;
        }

        public void setContent_id(int content_id) {
            this.content_id = content_id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getRoot_id() {
            return root_id;
        }

        public void setRoot_id(int root_id) {
            this.root_id = root_id;
        }

        public TouristBean getTourist() {
            return tourist;
        }

        public void setTourist(TouristBean tourist) {
            this.tourist = tourist;
        }

        public List<?> getChild() {
            return child;
        }

        public void setChild(List<?> child) {
            this.child = child;
        }

        public static class TouristBean {
            /**
             * id : 12
             * name : LE0sR3
             * phone : 18335929380
             * avatar : users/default.png
             * password : $2y$10$r4ig8Pxx2I8vnwKTOyCzje2VI0dUtcUvHFuSUj3Z2Kq8l4CyWT4Z.
             * remember_token : null
             * settings : null
             * created_at : 2020-04-21 20:52:41
             * updated_at : 2020-04-21 20:52:41
             * tourist_id : 80841516
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
        }
    }
}
