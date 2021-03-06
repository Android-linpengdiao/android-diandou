package com.diandou.model;

import java.io.Serializable;
import java.util.List;

public class BannerData implements Serializable {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":1,"created_at":"2019-09-23 23:40:00","updated_at":"2020-03-27 17:46:16","title":"欢迎来到U点逗","desc":"<p>欢迎来到U点逗的大家庭里。<\/p>\r\n<p>希望每一个视频都给你们带来欢乐。<\/p>\r\n<p>在大家庭里能找到你们想要的视频内容，<\/p>\r\n<p>如：想要看儿童讲完整一个自编的故事，<\/p>\r\n<p>&nbsp; &nbsp; &nbsp; 想看儿童每天学习的成果，<\/p>\r\n<p>&nbsp; &nbsp; &nbsp; 想看儿童讲的每一个旅游景点历史，<\/p>\r\n<p>&nbsp; &nbsp; &nbsp; 想看儿童说出的心里话，<\/p>\r\n<p>在这里都可以找到你们想要的视频。<\/p>","img":"banners/March2020/U77FPc3zu4KtZpVHal77.jpg","href":"https://www.baidu.com","type":1,"content_id":null,"link_type":2}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * created_at : 2019-09-23 23:40:00
         * updated_at : 2020-03-27 17:46:16
         * title : 欢迎来到U点逗
         * desc : <p>欢迎来到U点逗的大家庭里。</p>
         <p>希望每一个视频都给你们带来欢乐。</p>
         <p>在大家庭里能找到你们想要的视频内容，</p>
         <p>如：想要看儿童讲完整一个自编的故事，</p>
         <p>&nbsp; &nbsp; &nbsp; 想看儿童每天学习的成果，</p>
         <p>&nbsp; &nbsp; &nbsp; 想看儿童讲的每一个旅游景点历史，</p>
         <p>&nbsp; &nbsp; &nbsp; 想看儿童说出的心里话，</p>
         <p>在这里都可以找到你们想要的视频。</p>
         * img : banners/March2020/U77FPc3zu4KtZpVHal77.jpg
         * href : https://www.baidu.com
         * type : 1
         * content_id : null
         * link_type : 2
         */

        private int id;
        private String created_at;
        private String updated_at;
        private String title;
        private String desc;
        private String img;
        private String href;
        private int type;
        private int content_id;
        private int link_type;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getContent_id() {
            return content_id;
        }

        public void setContent_id(int content_id) {
            this.content_id = content_id;
        }

        public int getLink_type() {
            return link_type;
        }

        public void setLink_type(int link_type) {
            this.link_type = link_type;
        }
    }
}
