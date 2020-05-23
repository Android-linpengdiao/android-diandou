package com.diandou.model;

import java.util.List;

public class NoticeData {

    /**
     * code : 200
     * msg : 成功
     * data : [{"id":2,"created_at":"2020-03-26 11:03:04","updated_at":"2020-03-26 11:03:04","title":"绩效考核中如何正确获取工作量数据","desc":"U点逗是为儿童提供优质的视频内容分享平台，在平台上可以分享你所学的知识，分享你原创的故事。我们致力于让全世界看见每个儿童视频里面的微笑。"}]
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
         * id : 2
         * created_at : 2020-03-26 11:03:04
         * updated_at : 2020-03-26 11:03:04
         * title : 绩效考核中如何正确获取工作量数据
         * desc : U点逗是为儿童提供优质的视频内容分享平台，在平台上可以分享你所学的知识，分享你原创的故事。我们致力于让全世界看见每个儿童视频里面的微笑。
         */

        private int id;
        private String created_at;
        private String updated_at;
        private String title;
        private String desc;

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
    }
}
