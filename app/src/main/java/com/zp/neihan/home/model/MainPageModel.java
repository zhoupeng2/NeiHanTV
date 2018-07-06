package com.zp.neihan.home.model;

import com.zp.neihan.home.entity.JianDanComment;

import java.util.ArrayList;

import okhttp.OkHttpHelper;
import okhttp.interfaces.ResultCallBack;

/**
 * @author ZP
 * @CreatedDate 2018/7/6
 */
public class MainPageModel {
    private ArrayList<String> userInfoList;
    private ArrayList<JianDanComment> jiandanList;

    public ArrayList<String> selectUserInfoById(String id) {
        userInfoList = new ArrayList<>();
        userInfoList.add("快乐风男");
        userInfoList.add("快乐风男-托儿索");
        userInfoList.add("影流之主-儿童劫");
        userInfoList.add("红领烬");
        userInfoList.add("娃娃鱼");
        userInfoList.add("小学生之手");

        return userInfoList;
    }
    public ArrayList<JianDanComment> selectDataFromJianDan(int pageIndex, ResultCallBack resultCallBack) {
        OkHttpHelper.getDataAsynByGet("http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_duan_comments&page="+pageIndex,resultCallBack);
        return jiandanList;
    }
}
