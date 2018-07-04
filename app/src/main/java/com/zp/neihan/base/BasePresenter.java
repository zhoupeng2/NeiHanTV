package com.zp.neihan.base;

import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by zp on 2017/9/22.
 * copy  安卓源码设计模式
 */

public abstract class BasePresenter<T> {
    /**
     * 当内存不足释放内存
     */
    protected WeakReference<T> mViewRef; // view 的弱引用

    /**
     * bind p with v
     *
     * @param view
     */
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
            Log.i("BasePresenter", "MVP模式已经GC...防止内存泄漏");
        }
    }

    /**
     * 获取view的方法
     *
     * @return 当前关联的view
     */
    public T getView() {
        return mViewRef.get();
    }

    /**
     * 判断 Iview是否为空
     * @return view(一般被Activity实现)
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

}
