package com.zp.neihan.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;


import com.zp.neihan.R;
import com.zp.neihan.base.utils.NetworkStateView;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 *
 * @author ZHT (GitHub)
 * @Copyright HangZhou XCM Technology Co., Ltd.
 * @CreatedDate 2017/04/18
 */
public abstract class BaseFragment_NetworkState extends Fragment implements NetworkStateView.OnRefreshListener {

    public View mView, childView;

    private Unbinder unbinder;


    private NetworkStateView networkStateView;

    public String okHttpTag = "okHttpTag" + hashCode();
    protected Context context;
    protected SharedPreferences sp;
    /**
     * 加载
     */
    private long startTime;
    private CountDownTimer countDownTimer;
    /**
     * 公共 沉浸式布局 View
     */
    public View chenjin_view, background_if_is_white_view;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_base, container, false);
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (null != parent) {
            parent.removeView(mView);
        }

        addChildView(inflater);

        context = getActivity();
        sp = context
                .getSharedPreferences("sp_xiaocaimi", Context.MODE_PRIVATE);

        unbinder = ButterKnife.bind(this, mView);
        // 加载中...
        showLoadingView();
        //计算加载时间
        startTime = System.currentTimeMillis();
        initView();
        initLogic(savedInstanceState);
        initEvent();
        return mView;
    }

    /**
     * 初始化默认布局的View
     *
     * @return ContentView
     */
    protected abstract int getLayoutId();

    /**
     * 子类复写初始化界面布局
     */
    public abstract void initView();

    /**
     * 子类复写初始化界面逻辑
     */
    public abstract void initLogic(Bundle savedInstanceState);

    /**
     * 子类复写初始化界面事件
     */
    public abstract void initEvent();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 添加子Fragment的布局文件
     *
     * @param inflater
     */
    private void addChildView(LayoutInflater inflater) {
        networkStateView = (NetworkStateView) mView.findViewById(R.id.nsv_state_view);
        FrameLayout container = (FrameLayout) mView.findViewById(R.id.fl_fragment_child_container);
        childView = inflater.inflate(getLayoutId(), null);
        container.addView(childView, 0);
    }


    /**
     * 显示加载中的布局
     */
    public void showLoadingView() {
        networkStateView.showLoading();
    }

    /**
     * 显示加载完成后的布局(即子类Activity的布局)
     */
    public void showContentView() {
        networkStateView.showSuccess();
    }

    /**
     * 界面至少处于加载中 loadingTime毫秒
     * 进入Fragment第一步  showLoadingview（）；
     * 最后一步  showContentView（）；
     *
     * @param 视图至少加载时间
     */
    public void customShowCotentView(int loadingTime, final ShowFinishListenr showFinishListenr) {
        long endTime = System.currentTimeMillis();
        long dTime = endTime - startTime;
        if (dTime < loadingTime) {
            countDownTimer = new CountDownTimer(800 - dTime, 800 - dTime) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    showContentView();
                    showFinishListenr.showfinish();
                }
            };
            countDownTimer.start();
        } else {
            showContentView();
            showFinishListenr.showfinish();
        }
    }

    /**
     * 界面至少处于加载中 loadingTime毫秒
     * 进入Fragment第一步  showLoadingview（）；
     * 最后一步  showContentView（）；
     *
     * @param 视图至少加载时间
     */
    public void customShowCotentView(int loadingTime) {

        long endTime = System.currentTimeMillis();
        long dTime = endTime - startTime;
        if (dTime < loadingTime) {
            countDownTimer = new CountDownTimer(800 - dTime, 800 - dTime) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    showContentView();
                }
            };
            countDownTimer.start();
        } else {
            showContentView();
        }
    }

    /**
     * 显示没有网络的布局
     */
    public void showNoNetworkView() {
        networkStateView.showNoNetwork();
        networkStateView.setOnRefreshListener(this);
    }

    /**
     * 显示没有数据的布局
     */
    public void showEmptyView() {
        networkStateView.showEmpty();
        networkStateView.setOnRefreshListener(this);
    }

    /**
     * 显示数据错误，网络错误等布局
     */
    public void showErrorView() {
        networkStateView.showError();
        networkStateView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        onNetworkViewRefresh();
    }

    /**
     * 重新请求网络
     */
    public void onNetworkViewRefresh() {

    }

    public interface ShowFinishListenr {
        void showfinish();
    }

    /**
     * 调整沉浸式菜单的title
     */
    public void dealStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**
             * 将状态栏设置为沉浸式
             */
            Window window = getActivity().getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //公共 沉浸式布局 View
            chenjin_view = childView.findViewById(R.id.chenjin_view);
            //公共 沉浸式布局 View
            background_if_is_white_view = childView.findViewById(R.id.background_if_is_white_view);
            /**
             * 将系统状态栏的高度赋值给 占位 View
             */
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = chenjin_view.getLayoutParams();
            lp.height = statusBarHeight;
            chenjin_view.setLayoutParams(lp);
        }
    }

    /**
     * 获得系统状态栏的高度
     *
     * @return 系统状态栏的高度
     */
    public int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 设置沉浸式布局后，如果导航栏是白色背景，就调用此方法
     */
    public void setBackgroundIfIsWhiteView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            /**
             * 将系统状态栏的高度赋值给 占位 View
             */
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = background_if_is_white_view.getLayoutParams();
            lp.height = statusBarHeight;
            background_if_is_white_view.setLayoutParams(lp);
            background_if_is_white_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

