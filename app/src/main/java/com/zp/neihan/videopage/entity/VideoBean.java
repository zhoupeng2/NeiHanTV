package com.zp.neihan.videopage.entity;

import java.io.Serializable;
import java.util.List;

public class VideoBean implements Serializable {

    public String displayName;
    public String cover;
    public String path;

    /**
     * 投稿人 名字
     */
    private String contributorName;

    /**
     * 投稿 文字内容
     */

    private String contributeText;
    /**
     * 投稿 类型
     * 0: 纯文字  1: 图片  2：视频
     */
    private int contributeType;
    /**
     * 投稿的所有图片
     */
    private List<String> imageUrls;

    public VideoBean() {

    }

    public VideoBean(String displayName, String cover, String path) {
        this.displayName = displayName;
        this.cover = cover;
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public String getContributeText() {
        return contributeText;
    }

    public void setContributeText(String contributeText) {
        this.contributeText = contributeText;
    }

    public int getContributeType() {
        return contributeType;
    }

    public void setContributeType(int contributeType) {
        this.contributeType = contributeType;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
