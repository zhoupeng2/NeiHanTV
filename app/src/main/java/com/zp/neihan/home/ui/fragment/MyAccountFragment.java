package com.zp.neihan.home.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.zp.neihan.base.BaseFragment_NetworkState;
import com.zp.neihan.base.BaseMVPFragment;
import com.zp.neihan.base.BasePresenter;
import com.zp.neihan.R;

/**
 * @author ZP
 * @CreatedDate 2018/7/5
 */
public class MyAccountFragment extends BaseFragment_NetworkState {
    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_account;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("MyAccountFragment","MyAccountFragment ----onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("MyAccountFragment","MyAccountFragment ----onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MyAccountFragment","MyAccountFragment ----onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MyAccountFragment","MyAccountFragment ----onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MyAccountFragment","MyAccountFragment ----onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("MyAccountFragment","MyAccountFragment ----onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyAccountFragment","MyAccountFragment ----onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("MyAccountFragment","MyAccountFragment ----onDetach");
    }
}
