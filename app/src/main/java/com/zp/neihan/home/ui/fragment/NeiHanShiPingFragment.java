package com.zp.neihan.home.ui.fragment;

import android.os.Bundle;

import com.zp.neihan.base.BaseFragment_NetworkState;
import com.zp.neihan.base.BaseMVPFragment;
import com.zp.neihan.base.BasePresenter;
import com.zp.neihan.R;

/**
 * @author ZP
 * @CreatedDate 2018/7/5
 */
public class NeiHanShiPingFragment extends BaseFragment_NetworkState {
    public static NeiHanShiPingFragment newInstance() {
        return new NeiHanShiPingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_neihan_shiping;
    }

    @Override
    public void initView() {
        showContentView();
    }

    @Override
    public void initLogic(Bundle savedInstanceState) {

    }

    @Override
    public void initEvent() {

    }
}
