package com.zp.neihan.home.contract;

import android.content.Context;

import com.zp.neihan.base.BaseMVPView;
import com.zp.neihan.base.BasePresenter;
import com.zp.neihan.home.model.MainPageModel;
import com.zp.neihan.splash.contract.SplashContract;
import com.zp.neihan.splash.model.SplashModel;

/**
 * @author ZP
 * @CreatedDate 2018/7/6
 */
public interface MainPageContract {
    public static class MainPagePresenter extends BasePresenter<MainPageContract.MainPageView> {
        public void getUserInfo(String uid, Context context) {
            /**
             * TODO 1 调用 Model获取数据
             */
            MainPageModel splashModel = new MainPageModel();
            /**
             * 模拟通过用户Id查询用户信息
             */
            String userInfo = splashModel.selectUserInfoById(uid);
            /**
             * TODO 2 填充 数据
             */
            getView().setDataToView(userInfo);
        }

    }

    public interface MainPageView extends BaseMVPView {
        void setDataToView(String userInfo);
    }

}
