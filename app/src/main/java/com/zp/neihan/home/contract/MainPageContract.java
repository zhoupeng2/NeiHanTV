package com.zp.neihan.home.contract;

import android.content.Context;

import com.google.gson.Gson;
import com.zp.neihan.base.BaseMVPView;
import com.zp.neihan.base.BasePresenter;
import com.zp.neihan.home.entity.JianDanComment;
import com.zp.neihan.home.model.MainPageModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp.interfaces.ResultCallBack;

/**
 * @author ZP
 * @CreatedDate 2018/7/6
 */
public interface MainPageContract {
    /**
     * TODO 1 调用 Model获取数据
     */
    MainPageModel mainPageModel = new MainPageModel();

    public static class MainPagePresenter extends BasePresenter<MainPageContract.MainPageView> {
        private ArrayList<JianDanComment> jianDanCommentList;

        public void getUserInfo(String uid, Context context) {

            /**
             * 模拟通过用户Id查询用户信息
             */
            ArrayList<String> userInfoList = mainPageModel.selectUserInfoById(uid);
            /**
             * TODO 2 填充 数据
             */
            getView().setDataToView(userInfoList);
        }

        /**
         * 数据来自于 煎蛋
         *
         * @param pageIndex 第几页
         */
        public void selectDataFromJianDan(int pageIndex) {

            mainPageModel.selectDataFromJianDan(pageIndex, new ResultCallBack() {
                @Override
                public void onPrepare() {

                }

                @Override
                public void onComplete(Map<String, Object> map) {

                }

                @Override
                public void okResult(Map<String, Object> map) {
                    try {

                        Object comments = map.get("comments");
                        if (comments != null) {
                            JSONArray jsons = new JSONArray(comments.toString());
                            jianDanCommentList = new ArrayList<>();
                            Gson gs = new Gson();
                            if (jsons != null && jsons.length() > 0) {
                                for (int i = 0; i < jsons.length(); i++) {
                                    JSONObject json = new JSONObject(jsons.get(i)
                                            .toString());
                                    JianDanComment coupon = gs.fromJson(
                                            json.toString(), JianDanComment.class);
                                    jianDanCommentList.add(coupon);
                                }
                            }
                        }
                        getView().okResult(jianDanCommentList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void noLoginResult(String message) {

                }

                @Override
                public void noDataResult(String message) {

                }

                @Override
                public void errorResult(String message) {
                    getView().errorResult(message);
                }
            });
        }


    }

    public interface MainPageView extends BaseMVPView {
        void setDataToView(ArrayList<String> userInfo);

        void okResult(ArrayList<JianDanComment> jianDanList);

        void errorResult(String message);
    }

}
