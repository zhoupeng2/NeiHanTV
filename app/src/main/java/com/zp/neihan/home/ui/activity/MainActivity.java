package com.zp.neihan.home.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zp.neihan.R;
import com.zp.neihan.base.BaseActivity_NetworkState;

public class MainActivity extends BaseActivity_NetworkState {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        showLoadingView();
        customShowCotentView(1500);
    }

    @Override
    protected void initLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }
}
