package com.diandou.model;

import java.util.List;

public class MessageData {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":1,"created_at":"2020-03-24 20:53:36","updated_at":"2020-03-24 20:53:36","status":1,"tourist_id":3,"content_id":1,"parent_id":0,"body":"这个视频不错","root_id":0,"tourist":{"id":3,"name":"xu","phone":"13699162862","avatar":"upload/20200324084143gKEBF.jpg","password":"$2y$10$hoZjKOg.nwZ72VWGQDFukeCvzV8UfEVWUR3l47kdDPXm7JFHRibA2","remember_token":null,"settings":null,"created_at":"2020-03-24 12:24:42","updated_at":"2020-04-11 16:39:17","tourist_id":"64796762","sex":2,"birth":"2020-3-27","openid":"oH48X0_S-Uhjp-C-m4nK0S-bunRU","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":"加油加油","weibo":"","reg":"","qq_id":"CFA10663BDA8EFA1D50D09101CB74A8F","weibo_id":"1189548407","liker":0,"followers":0,"comment":0},"contents":{"id":1,"created_at":"2020-03-24 20:50:02","updated_at":"2020-04-27 01:09:06","status":1,"tourist_id":3,"nav_id":5,"nav_name":"故事","desc":"看房子","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/3202032420500storageemulated0tencentMicroMsgWeiXinwx_camera_1584944757945.mp4\",\"original_name\":\"3202032420500storageemulated0tencentMicroMsgWeiXinwx_camera_1584944757945.mp4\"}]","tourist_name":"xu","addr":"","play_time":47,"assist":1,"img":"upload/20200324085002zXxBE.jpg","hot":2,"recommend":2}}]
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
         * id : 1
         * created_at : 2020-03-24 20:53:36
         * updated_at : 2020-03-24 20:53:36
         * status : 1
         * tourist_id : 3
         * content_id : 1
         * parent_id : 0
         * body : 这个视频不错
         * root_id : 0
         * tourist : {"id":3,"name":"xu","phone":"13699162862","avatar":"upload/20200324084143gKEBF.jpg","password":"$2y$10$hoZjKOg.nwZ72VWGQDFukeCvzV8UfEVWUR3l47kdDPXm7JFHRibA2","remember_token":null,"settings":null,"created_at":"2020-03-24 12:24:42","updated_at":"2020-04-11 16:39:17","tourist_id":"64796762","sex":2,"birth":"2020-3-27","openid":"oH48X0_S-Uhjp-C-m4nK0S-bunRU","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":"加油加油","weibo":"","reg":"","qq_id":"CFA10663BDA8EFA1D50D09101CB74A8F","weibo_id":"1189548407","liker":0,"followers":0,"comment":0}
         * contents : {"id":1,"created_at":"2020-03-24 20:50:02","updated_at":"2020-04-27 01:09:06","status":1,"tourist_id":3,"nav_id":5,"nav_name":"故事","desc":"看房子","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/3202032420500storageemulated0tencentMicroMsgWeiXinwx_camera_1584944757945.mp4\",\"original_name\":\"3202032420500storageemulated0tencentMicroMsgWeiXinwx_camera_1584944757945.mp4\"}]","tourist_name":"xu","addr":"","play_time":47,"assist":1,"img":"upload/20200324085002zXxBE.jpg","hot":2,"recommend":2}
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
        private ContentsBean contents;

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

        public ContentsBean getContents() {
            return contents;
        }

        public void setContents(ContentsBean contents) {
            this.contents = contents;
        }

        public static class TouristBean {
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
        }

        public static class ContentsBean {
            /**
             * id : 1
             * created_at : 2020-03-24 20:50:02
             * updated_at : 2020-04-27 01:09:06
             * status : 1
             * tourist_id : 3
             * nav_id : 5
             * nav_name : 故事
             * desc : 看房子
             * link : [{"download_link":"https:\/\/diandou-test.oss-cn-beijing.aliyuncs.com\/3202032420500storageemulated0tencentMicroMsgWeiXinwx_camera_1584944757945.mp4","original_name":"3202032420500storageemulated0tencentMicroMsgWeiXinwx_camera_1584944757945.mp4"}]
             * tourist_name : xu
             * addr :
             * play_time : 47
             * assist : 1
             * img : upload/20200324085002zXxBE.jpg
             * hot : 2
             * recommend : 2
             */

            private int id;
            private String created_at;
            private String updated_at;
            private int status;
            private int tourist_id;
            private int nav_id;
            private String nav_name;
            private String desc;
            private String link;
            private String tourist_name;
            private String addr;
            private int play_time;
            private int assist;
            private String img;
            private int hot;
            private int recommend;

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

            public int getNav_id() {
                return nav_id;
            }

            public void setNav_id(int nav_id) {
                this.nav_id = nav_id;
            }

            public String getNav_name() {
                return nav_name;
            }

            public void setNav_name(String nav_name) {
                this.nav_name = nav_name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getTourist_name() {
                return tourist_name;
            }

            public void setTourist_name(String tourist_name) {
                this.tourist_name = tourist_name;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public int getPlay_time() {
                return play_time;
            }

            public void setPlay_time(int play_time) {
                this.play_time = play_time;
            }

            public int getAssist() {
                return assist;
            }

            public void setAssist(int assist) {
                this.assist = assist;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }
        }
    }
}
