package com.diandou.manager;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/27.
 */

public class ShareEntity implements Serializable {

    public String webpageUrl;
    public String title;
    public String desc;
    public String thumbnailUrl;

    public ShareEntity(String webpageUrl, String title, String desc, String thumbnailUrl) {
        this.webpageUrl = webpageUrl;
        this.title = title;
        this.desc = desc;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getWebpageUrl() {
        return webpageUrl;
    }

    public void setWebpageUrl(String webpageUrl) {
        this.webpageUrl = webpageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
