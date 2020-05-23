package com.diandou.model;

import java.util.List;

public class WorkData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":26,"created_at":"2020-04-06 19:08:12","updated_at":"2020-05-23 09:39:26","status":1,"tourist_id":6,"nav_id":6,"nav_name":"音乐","desc":"号楼","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/62020461987storageemulated0Androiddatacom.diandoufilesJCameravideo_1586171204200.mp4\",\"original_name\":\"62020461987storageemulated0Androiddatacom.diandoufilesJCameravideo_1586171204200.mp4\"}]","tourist_name":"O9Cm8J","addr":"北京市海淀区农大南路88号","play_time":35,"assist":1,"img":"upload/20200406070809ZJjln.jpg","hot":1,"recommend":2,"name":"O9Cm8J","avatar":"users/default.png"}],"first_page_url":"http://admin.udiandou.com/api/center/searchWork?page=1","from":1,"last_page":1,"last_page_url":"http://admin.udiandou.com/api/center/searchWork?page=1","next_page_url":null,"path":"http://admin.udiandou.com/api/center/searchWork","per_page":"10","prev_page_url":null,"to":1,"total":1}
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
         * data : [{"id":26,"created_at":"2020-04-06 19:08:12","updated_at":"2020-05-23 09:39:26","status":1,"tourist_id":6,"nav_id":6,"nav_name":"音乐","desc":"号楼","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/62020461987storageemulated0Androiddatacom.diandoufilesJCameravideo_1586171204200.mp4\",\"original_name\":\"62020461987storageemulated0Androiddatacom.diandoufilesJCameravideo_1586171204200.mp4\"}]","tourist_name":"O9Cm8J","addr":"北京市海淀区农大南路88号","play_time":35,"assist":1,"img":"upload/20200406070809ZJjln.jpg","hot":1,"recommend":2,"name":"O9Cm8J","avatar":"users/default.png"}]
         * first_page_url : http://admin.udiandou.com/api/center/searchWork?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://admin.udiandou.com/api/center/searchWork?page=1
         * next_page_url : null
         * path : http://admin.udiandou.com/api/center/searchWork
         * per_page : 10
         * prev_page_url : null
         * to : 1
         * total : 1
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
             * id : 26
             * created_at : 2020-04-06 19:08:12
             * updated_at : 2020-05-23 09:39:26
             * status : 1
             * tourist_id : 6
             * nav_id : 6
             * nav_name : 音乐
             * desc : 号楼
             * link : [{"download_link":"https:\/\/diandou-test.oss-cn-beijing.aliyuncs.com\/62020461987storageemulated0Androiddatacom.diandoufilesJCameravideo_1586171204200.mp4","original_name":"62020461987storageemulated0Androiddatacom.diandoufilesJCameravideo_1586171204200.mp4"}]
             * tourist_name : O9Cm8J
             * addr : 北京市海淀区农大南路88号
             * play_time : 35
             * assist : 1
             * img : upload/20200406070809ZJjln.jpg
             * hot : 1
             * recommend : 2
             * name : O9Cm8J
             * avatar : users/default.png
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
            private String name;
            private String avatar;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
