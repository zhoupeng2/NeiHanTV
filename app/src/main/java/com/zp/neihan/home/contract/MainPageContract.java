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


        /**
         * 数据来自于 煎蛋
         *
         * @param pageIndex 第几页
         */
        public void selectDataFromJianDan(int pageIndex,boolean isFristRefresh) {
            if (pageIndex == 1 && isFristRefresh!=true) {
                jianDanCommentList.clear();
                jianDanCommentList = null;
            }
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
                            if(jianDanCommentList==null){
                                jianDanCommentList = new ArrayList<>();
                            }

                            Gson gs = new Gson();
                            if (jsons != null && jsons.length() > 0) {
                                for (int i = 0; i < jsons.length(); i++) {
                                    JSONObject json = new JSONObject(jsons.get(i)
                                            .toString());
                                    JianDanComment jianDanComment = gs.fromJson(
                                            json.toString(), JianDanComment.class);
                                        jianDanCommentList.add(jianDanComment);

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

        void okResult(ArrayList<JianDanComment> jianDanList);

        void errorResult(String message);
    }

}
