package com.diandou.model;

import java.util.List;

public class MineWorkData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":25,"created_at":"2020-03-31 20:12:59","status":1,"tourist_id":3,"nav_id":6,"nav_name":"音乐","desc":"裤子很好","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\",\"original_name\":\"32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\"}]","addr":"北京市海淀区大有庄南上坡29号楼(国际关系学院西南)","play_time":39,"assist":3,"img":"upload/20200331081258Nl5Ch.jpg","tourist_name":"xu"}]}
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
         * data : [{"id":25,"created_at":"2020-03-31 20:12:59","status":1,"tourist_id":3,"nav_id":6,"nav_name":"音乐","desc":"裤子很好","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\",\"original_name\":\"32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\"}]","addr":"北京市海淀区大有庄南上坡29号楼(国际关系学院西南)","play_time":39,"assist":3,"img":"upload/20200331081258Nl5Ch.jpg","tourist_name":"xu"}]
         */

        private int current_page;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 25
             * created_at : 2020-03-31 20:12:59
             * status : 1
             * tourist_id : 3
             * nav_id : 6
             * nav_name : 音乐
             * desc : 裤子很好
             * link : [{"download_link":"https:\/\/diandou-test.oss-cn-beijing.aliyuncs.com\/32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4","original_name":"32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4"}]
             * addr : 北京市海淀区大有庄南上坡29号楼(国际关系学院西南)
             * play_time : 39
             * assist : 3
             * img : upload/20200331081258Nl5Ch.jpg
             * tourist_name : xu
             */

            private int id;
            private String created_at;
            private int status;
            private int tourist_id;
            private int nav_id;
            private String nav_name;
            private String desc;
            private String link;
            private String addr;
            private int play_time;
            private int assist;
            private String img;
            private String tourist_name;

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

            public String getTourist_name() {
                return tourist_name;
            }

            public void setTourist_name(String tourist_name) {
                this.tourist_name = tourist_name;
            }
        }
    }
}
