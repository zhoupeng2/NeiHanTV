package com.zp.neihan.videopage.utils;

import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.zp.neihan.videopage.entity.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2018/4/19.
 */

public class DataUtils {

    public static final String VIDEO_URL_01 = "http://jiajunhui.cn/video/kaipao.mp4";
    public static final String VIDEO_URL_02 = "http://jiajunhui.cn/video/kongchengji.mp4";
    public static final String VIDEO_URL_03 = "http://jiajunhui.cn/video/allsharestar.mp4";
    public static final String VIDEO_URL_04 = "http://jiajunhui.cn/video/edwin_rolling_in_the_deep.flv";
    public static final String VIDEO_URL_05 = "http://jiajunhui.cn/video/crystalliz.flv";
    public static final String VIDEO_URL_06 = "http://jiajunhui.cn/video/big_buck_bunny.mp4";
    public static final String VIDEO_URL_07 = "http://jiajunhui.cn/video/trailer.mp4";

    public static String[] urls = new String[]{
            VIDEO_URL_01,
            VIDEO_URL_02,
            VIDEO_URL_03,
            VIDEO_URL_04,
            VIDEO_URL_05,
            VIDEO_URL_06,
            VIDEO_URL_07,
    };

    public static List<VideoItem> getRemoteVideoItems(){
        VideoItem item;
        List<VideoItem> items = new ArrayList<>();
        int len = urls.length;
        for(int i=0;i<len;i++){
            item = new VideoItem();
            item.setPath(urls[i]);
            item.setDisplayName(urls[i]);
            items.add(item);
        }
        return items;
    }

    public static List<VideoBean> transList(List<VideoItem> items){
        List<VideoBean> videoList = new ArrayList<>();
        if(items!=null){
            VideoBean bean;
            for(VideoItem item:items){
                bean = new VideoBean(item.getDisplayName(), null, item.getPath());
                videoList.add(bean);
            }
        }
        return videoList;
    }

    public static List<VideoBean> getVideoList() {
        List<VideoBean> videoList = new ArrayList<>();

        videoList.add(new VideoBean(
                "各位观众，我就是赌 …… …… 圣",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/0/4/e4c8836bfe154d76a808da38d0733304.jpg",
                "https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo-transcode/26745284_d941eab8771bc5a82a8aa0fb0db90c34_3.mp4"));

        videoList.add(new VideoBean(
                "电瓶车被偷了，哈哈",
                "http://open-image.nosdn.127.net/57baaaeaad4e4fda8bdaceafdb9d45c2.jpg",
                "https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo/27_c9d3f9b019fd8640bae6c18fdbc2457d.mp4"));
        videoList.add(new VideoBean(
                "打工是不可能打工的",
                "http://open-image.nosdn.127.net/57baaaeaad4e4fda8bdaceafdb9d45c2.jpg",
                "https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-movideo/2368587_5c8cee447f57ba3cf2a6c357b2421287_cadc4aba9aa0.mp4"));
        videoList.add(new VideoBean(
                "嘿嘿嘿",
                "http://open-image.nosdn.127.net/57baaaeaad4e4fda8bdaceafdb9d45c2.jpg",
                "https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo-transcode/8181270_10b023211c10e2f7d4c618581fb1bda3_0.mp4"));
        videoList.add(new VideoBean(
                "这锁啦吹得很6",
                "http://open-image.nosdn.127.net/57baaaeaad4e4fda8bdaceafdb9d45c2.jpg",
                "https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo-transcode/23491834_1a2e3cbdb5b0fbbb4000740001abccde_2.mp4"));
        videoList.add(new VideoBean(
                "我现在没心情听什么狗屁浪漫爱情故事",
                "http://open-image.nosdn.127.net/57baaaeaad4e4fda8bdaceafdb9d45c2.jpg",
                "https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo-transcode/848365_82ef193e183641a87a04a399d7ec2377_3.mp4"));

        videoList.add(new VideoBean(
                "不想从被子里出来",
                "http://open-image.nosdn.127.net/57baaaeaad4e4fda8bdaceafdb9d45c2.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2018/01/12/SD70VQJ74_sd.mp4"));
        videoList.add(new VideoBean(
                "你欠缺的也许并不是能力",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/b/a/c36e048e284c459686133e66a79e2eba.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2016/06/22/SBP8G92E3_hd.mp4"));

        videoList.add(new VideoBean(
                "不耐烦的中国人?",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/e/9/ac655948c705413b8a63a7aaefd4cde9.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2017/05/31/SCKR8V6E9_hd.mp4"));

        videoList.add(new VideoBean(
                "神奇的珊瑚",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2016/11/e/4/75bc6c5227314e63bbfd5d9f0c5c28e4.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2016/01/11/SBC46Q9DV_hd.mp4"));

        videoList.add(new VideoBean(
                "怎样经营你的人脉",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2018/3/b/c/9d451a2da3cf42b0a049ba3e249222bc.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2018/04/19/SDEQS1GO6_hd.mp4"));

        videoList.add(new VideoBean(
                "怎么才能不畏将来",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2018/1/c/8/1aec3637270f465faae52713a7c191c8.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2018/01/25/SD82Q0AQE_hd.mp4"));

        videoList.add(new VideoBean(
                "音乐和艺术如何改变世界",
                "http://open-image.nosdn.127.net/image/snapshot_movie/2017/12/2/8/f30dd5f2f09c405c98e7eb6c06c89928.jpg",
                "https://mov.bn.netease.com/open-movie/nos/mp4/2017/12/04/SD3SUEFFQ_hd.mp4"));

        return videoList;
    }


}
