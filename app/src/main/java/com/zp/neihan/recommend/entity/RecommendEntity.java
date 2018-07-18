package com.zp.neihan.recommend.entity;

import com.zp.neihan.videopage.entity.VideoBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZP
 * @CreatedDate 2018/7/13
 */
public class RecommendEntity {
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

    /**
     * 视频相关
     */
    public String displayName;
    public String cover;
    public String path;

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

}
