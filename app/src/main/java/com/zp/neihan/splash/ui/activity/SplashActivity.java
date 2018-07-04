package com.zp.neihan.splash.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zp.neihan.R;
import com.zp.neihan.base.BaseMVPActivity;
import com.zp.neihan.splash.contract.SplashContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ZhouPeng
 * @Copyright HangZhou XCM Technology Co., Ltd.
 * @CreatedDate 2018/7/4
 */
public class SplashActivity extends BaseMVPActivity<SplashContract.SplashView, SplashContract.SplashPresenter> implements SplashContract.SplashView {

    @BindView(R.id.txt_splash)
    TextView txtSplash;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        showLoadingView();
        mPresenter.getUserInfo("9527");
        customShowCotentView(1500);
    }

    @Override
    protected void initLogic(Bundle savedInstanceState) {


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected SplashContract.SplashPresenter createPresenter() {
        return new SplashContract.SplashPresenter();
    }

    @Override
    public void setDataToView(String uid) {
        txtSplash.setText(uid);
    }

}
