package com.diandou.model;

import java.util.List;

public class FansData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":5,"name":"8Zbu5V","phone":"18515692029","avatar":"users/default.png","password":"$2y$10$bjsecx2Rn4Jh9o8gJoUf3eO.jiZB6o5W1XAJOqFWDxUTw/FYZPSm.","remember_token":null,"settings":null,"created_at":"2020-03-24 20:32:27","updated_at":"2020-03-24 20:32:27","tourist_id":"92859738","sex":1,"birth":null,"openid":"oH48X03285sQPqnnRy2GYOKdYukc","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0,"pivot":{"likeable_id":3,"liker_id":5,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-03-24 21:34:19","updated_at":"2020-03-24 21:34:19"},"attention":false,"attention_num":0,"content_num":2},{"id":6,"name":"O9Cm8J","phone":"18611001339","avatar":"users/default.png","password":"$2y$10$juBfS1JzvkT6B6kl7O8R9uvWh/8aNK0yqFF.ZKXpIHpTy3HdiyPee","remember_token":null,"settings":null,"created_at":"2020-03-25 21:10:55","updated_at":"2020-03-25 21:23:10","tourist_id":"75567584","sex":1,"birth":null,"openid":"oH48X07cqrOuB_KLDXZbvgUk3wi4","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":"73775B2E666798A1482423132B0E9CD2","weibo_id":null,"liker":0,"followers":0,"comment":0,"pivot":{"likeable_id":3,"liker_id":6,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-03-25 21:20:31","updated_at":"2020-03-25 21:20:31"},"attention":true,"attention_num":3,"content_num":1},{"id":2,"name":"Android","phone":"13521614827","avatar":"users/default.png","password":"$2y$10$lRBWSdhAAOGTY4vzKQlUSucslsbWecLyXsZrgO2Jh1yDFKbQ4QeIq","remember_token":null,"settings":null,"created_at":"2020-03-24 11:54:40","updated_at":"2020-05-25 06:14:52","tourist_id":"72858237","sex":1,"birth":"","openid":null,"headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":"","weibo":"","reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0,"pivot":{"likeable_id":3,"liker_id":2,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-05-23 08:51:26","updated_at":"2020-05-23 08:51:26"},"attention":true,"attention_num":1,"content_num":0}],"first_page_url":"http://admin.udiandou.com/api/center/attention?page=1","from":1,"last_page":1,"last_page_url":"http://admin.udiandou.com/api/center/attention?page=1","next_page_url":null,"path":"http://admin.udiandou.com/api/center/attention","per_page":"10","prev_page_url":null,"to":3,"total":3}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"id":5,"name":"8Zbu5V","phone":"18515692029","avatar":"users/default.png","password":"$2y$10$bjsecx2Rn4Jh9o8gJoUf3eO.jiZB6o5W1XAJOqFWDxUTw/FYZPSm.","remember_token":null,"settings":null,"created_at":"2020-03-24 20:32:27","updated_at":"2020-03-24 20:32:27","tourist_id":"92859738","sex":1,"birth":null,"openid":"oH48X03285sQPqnnRy2GYOKdYukc","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0,"pivot":{"likeable_id":3,"liker_id":5,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-03-24 21:34:19","updated_at":"2020-03-24 21:34:19"},"attention":false,"attention_num":0,"content_num":2},{"id":6,"name":"O9Cm8J","phone":"18611001339","avatar":"users/default.png","password":"$2y$10$juBfS1JzvkT6B6kl7O8R9uvWh/8aNK0yqFF.ZKXpIHpTy3HdiyPee","remember_token":null,"settings":null,"created_at":"2020-03-25 21:10:55","updated_at":"2020-03-25 21:23:10","tourist_id":"75567584","sex":1,"birth":null,"openid":"oH48X07cqrOuB_KLDXZbvgUk3wi4","headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":null,"weibo":null,"reg":"","qq_id":"73775B2E666798A1482423132B0E9CD2","weibo_id":null,"liker":0,"followers":0,"comment":0,"pivot":{"likeable_id":3,"liker_id":6,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-03-25 21:20:31","updated_at":"2020-03-25 21:20:31"},"attention":true,"attention_num":3,"content_num":1},{"id":2,"name":"Android","phone":"13521614827","avatar":"users/default.png","password":"$2y$10$lRBWSdhAAOGTY4vzKQlUSucslsbWecLyXsZrgO2Jh1yDFKbQ4QeIq","remember_token":null,"settings":null,"created_at":"2020-03-24 11:54:40","updated_at":"2020-05-25 06:14:52","tourist_id":"72858237","sex":1,"birth":"","openid":null,"headimgurl":null,"city":null,"province":null,"cancel":1,"autograph":"","weibo":"","reg":"","qq_id":null,"weibo_id":null,"liker":0,"followers":0,"comment":0,"pivot":{"likeable_id":3,"liker_id":2,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-05-23 08:51:26","updated_at":"2020-05-23 08:51:26"},"attention":true,"attention_num":1,"content_num":0}]
         * first_page_url : http://admin.udiandou.com/api/center/attention?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://admin.udiandou.com/api/center/attention?page=1
         * next_page_url : null
         * path : http://admin.udiandou.com/api/center/attention
         * per_page : 10
         * prev_page_url : null
         * to : 3
         * total : 3
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private Object next_page_url;
        private String path;
        private String per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public String getFirst_page_url() {
            return first_page_url;
        }

        public void setFirst_page_url(String first_page_url) {
            this.first_page_url = first_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getLast_page_url() {
            return last_page_url;
        }

        public void setLast_page_url(String last_page_url) {
            this.last_page_url = last_page_url;
        }

        public Object getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(Object next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 5
             * name : 8Zbu5V
             * phone : 18515692029
             * avatar : users/default.png
             * password : $2y$10$bjsecx2Rn4Jh9o8gJoUf3eO.jiZB6o5W1XAJOqFWDxUTw/FYZPSm.
             * remember_token : null
             * settings : null
             * created_at : 2020-03-24 20:32:27
             * updated_at : 2020-03-24 20:32:27
             * tourist_id : 92859738
             * sex : 1
             * birth : null
             * openid : oH48X03285sQPqnnRy2GYOKdYukc
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
             * pivot : {"likeable_id":3,"liker_id":5,"likeable_type":"App\\Tourist","liker_type":"App\\Tourist","created_at":"2020-03-24 21:34:19","updated_at":"2020-03-24 21:34:19"}
             * attention : false
             * attention_num : 0
             * content_num : 2
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
            private String openid;
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
            private PivotBean pivot;
            private boolean attention;
            private int attention_num;
            private int content_num;

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

            public PivotBean getPivot() {
                return pivot;
            }

            public void setPivot(PivotBean pivot) {
                this.pivot = pivot;
            }

            public boolean isAttention() {
                return attention;
            }

            public void setAttention(boolean attention) {
                this.attention = attention;
            }

            public int getAttention_num() {
                return attention_num;
            }

            public void setAttention_num(int attention_num) {
                this.attention_num = attention_num;
            }

            public int getContent_num() {
                return content_num;
            }

            public void setContent_num(int content_num) {
                this.content_num = content_num;
            }

            public static class PivotBean {
                /**
                 * likeable_id : 3
                 * liker_id : 5
                 * likeable_type : App\Tourist
                 * liker_type : App\Tourist
                 * created_at : 2020-03-24 21:34:19
                 * updated_at : 2020-03-24 21:34:19
                 */

                private int likeable_id;
                private int liker_id;
                private String likeable_type;
                private String liker_type;
                private String created_at;
                private String updated_at;

                public int getLikeable_id() {
                    return likeable_id;
                }

                public void setLikeable_id(int likeable_id) {
                    this.likeable_id = likeable_id;
                }

                public int getLiker_id() {
                    return liker_id;
                }

                public void setLiker_id(int liker_id) {
                    this.liker_id = liker_id;
                }

                public String getLikeable_type() {
                    return likeable_type;
                }

                public void setLikeable_type(String likeable_type) {
                    this.likeable_type = likeable_type;
                }

                public String getLiker_type() {
                    return liker_type;
                }

                public void setLiker_type(String liker_type) {
                    this.liker_type = liker_type;
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
            }
        }
    }
}
