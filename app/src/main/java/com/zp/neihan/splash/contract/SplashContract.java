package com.zp.neihan.splash.contract;

import com.zp.neihan.base.BaseMVPView;
import com.zp.neihan.base.BasePresenter;
import com.zp.neihan.splash.model.SplashModel;

/**
 * @author ZhouPeng
 * @Copyright HangZhou XCM Technology Co., Ltd.
 * @CreatedDate 2018/7/4
 */
public interface SplashContract {
    public static class SplashPresenter extends BasePresenter<SplashView> {
        public void getUserInfo(String uid) {
            /**
             * TODO 1 调用 Model获取数据
             */
            SplashModel splashModel = new SplashModel();
            /**
             * 模拟通过用户Id查询用户信息
             */
            String userInfo = splashModel.getUserInfo(uid);
            /**
             * TODO 2 填充 数据
             */
            getView().setDataToView(userInfo);
        }

    }

    public interface SplashView extends BaseMVPView {
        void setDataToView(String uid);
    }

}
