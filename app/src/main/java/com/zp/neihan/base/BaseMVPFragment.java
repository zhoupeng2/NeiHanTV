package com.zp.neihan.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Administrator on 2017/9/22.
 */

public abstract class BaseMVPFragment<V, T extends BasePresenter<V>> extends Fragment {
    protected T mPresenter;//Presenter对象
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setContentViewId(), container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        //创建Presenter
        mPresenter = createPresenter();
        //内存泄漏
        //关联View
        mPresenter.attachView((V) this);
        initView();
        intLogic();
        initEvent();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /**
     * 子类返回view的Resources id
     */
    protected abstract int setContentViewId();

    /**
     * 子类复写初始化界面
     */
    protected abstract void initView();

    /**
     * 子类复写初始化事件
     */
    protected abstract void initEvent();

    /**
     * 子类复写初始化逻辑
     */
    protected abstract void intLogic();

    /**
     * 子类复写初始化Presenter
     */
    protected abstract T createPresenter();
}
