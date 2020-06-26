package com.diandou.model;

import java.util.List;

public class MineLikeWorkData {

    /**
     * code : 200
     * msg : 成功
     * data : {"current_page":1,"data":[{"id":18,"content_id":25,"tourist_id":2,"created_at":"2020-05-23 08:51:42","updated_at":"2020-05-23 08:51:42","content":{"id":25,"created_at":"2020-03-31 20:12:59","updated_at":"2020-05-23 19:44:46","status":1,"tourist_id":3,"nav_id":6,"nav_name":"音乐","desc":"裤子很好","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\",\"original_name\":\"32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\"}]","tourist_name":"xu","addr":"北京市海淀区大有庄南上坡29号楼(国际关系学院西南)","play_time":39,"assist":3,"img":"upload/20200331081258Nl5Ch.jpg","hot":2,"recommend":2,"is_deleted":2}}],"first_page_url":"http://www.udiandou.com/api/center/assist?page=1","from":1,"last_page":6,"last_page_url":"http://www.udiandou.com/api/center/assist?page=6","next_page_url":"http://www.udiandou.com/api/center/assist?page=2","path":"http://www.udiandou.com/api/center/assist","per_page":"1","prev_page_url":null,"to":1,"total":6}
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
         * data : [{"id":18,"content_id":25,"tourist_id":2,"created_at":"2020-05-23 08:51:42","updated_at":"2020-05-23 08:51:42","content":{"id":25,"created_at":"2020-03-31 20:12:59","updated_at":"2020-05-23 19:44:46","status":1,"tourist_id":3,"nav_id":6,"nav_name":"音乐","desc":"裤子很好","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\",\"original_name\":\"32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\"}]","tourist_name":"xu","addr":"北京市海淀区大有庄南上坡29号楼(国际关系学院西南)","play_time":39,"assist":3,"img":"upload/20200331081258Nl5Ch.jpg","hot":2,"recommend":2,"is_deleted":2}}]
         * first_page_url : http://www.udiandou.com/api/center/assist?page=1
         * from : 1
         * last_page : 6
         * last_page_url : http://www.udiandou.com/api/center/assist?page=6
         * next_page_url : http://www.udiandou.com/api/center/assist?page=2
         * path : http://www.udiandou.com/api/center/assist
         * per_page : 1
         * prev_page_url : null
         * to : 1
         * total : 6
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private String next_page_url;
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

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
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
             * id : 18
             * content_id : 25
             * tourist_id : 2
             * created_at : 2020-05-23 08:51:42
             * updated_at : 2020-05-23 08:51:42
             * content : {"id":25,"created_at":"2020-03-31 20:12:59","updated_at":"2020-05-23 19:44:46","status":1,"tourist_id":3,"nav_id":6,"nav_name":"音乐","desc":"裤子很好","link":"[{\"download_link\":\"https:\\/\\/diandou-test.oss-cn-beijing.aliyuncs.com\\/32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\",\"original_name\":\"32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4\"}]","tourist_name":"xu","addr":"北京市海淀区大有庄南上坡29号楼(国际关系学院西南)","play_time":39,"assist":3,"img":"upload/20200331081258Nl5Ch.jpg","hot":2,"recommend":2,"is_deleted":2}
             */

            private int id;
            private int content_id;
            private int tourist_id;
            private String created_at;
            private String updated_at;
            private ContentBean content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getContent_id() {
                return content_id;
            }

            public void setContent_id(int content_id) {
                this.content_id = content_id;
            }

            public int getTourist_id() {
                return tourist_id;
            }

            public void setTourist_id(int tourist_id) {
                this.tourist_id = tourist_id;
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

            public ContentBean getContent() {
                return content;
            }

            public void setContent(ContentBean content) {
                this.content = content;
            }

            public static class ContentBean {
                /**
                 * id : 25
                 * created_at : 2020-03-31 20:12:59
                 * updated_at : 2020-05-23 19:44:46
                 * status : 1
                 * tourist_id : 3
                 * nav_id : 6
                 * nav_name : 音乐
                 * desc : 裤子很好
                 * link : [{"download_link":"https:\/\/diandou-test.oss-cn-beijing.aliyuncs.com\/32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4","original_name":"32020331201258storageemulated0txcachetxvodcache059162ad8ed5a8cc7d2a5bde17f099d2.mp4"}]
                 * tourist_name : xu
                 * addr : 北京市海淀区大有庄南上坡29号楼(国际关系学院西南)
                 * play_time : 39
                 * assist : 3
                 * img : upload/20200331081258Nl5Ch.jpg
                 * hot : 2
                 * recommend : 2
                 * is_deleted : 2
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
                private int is_deleted;
                private boolean selection = false;

                public boolean isSelection() {
                    return selection;
                }

                public void setSelection(boolean selection) {
                    this.selection = selection;
                }

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

                public int getIs_deleted() {
                    return is_deleted;
                }

                public void setIs_deleted(int is_deleted) {
                    this.is_deleted = is_deleted;
                }
            }
        }
    }
}
