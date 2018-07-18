package com.zp.neihan.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
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
 * Created by Administrator on 2017/9/22.
 */

public abstract class BaseMVPFragment<V, T extends BasePresenter<V>> extends Fragment implements NetworkStateView.OnRefreshListener {
    protected T mPresenter;//Presenter对象


    private Unbinder unbinder;

    public View rootView, childView;
    private NetworkStateView networkStateView;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_base, container, false);
        ViewGroup parent = (ViewGroup) rootView.getParent();
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        if (null != parent) {
            parent.removeView(rootView);
        }

        addChildView(inflater);

        context = getActivity();
        sp = context
                .getSharedPreferences("sp_xiaocaimi", Context.MODE_PRIVATE);
        unbinder = ButterKnife.bind(this, rootView);
        // 加载中...
        showLoadingView();
        //计算加载时间
        startTime = System.currentTimeMillis();
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
    public void customShowCotentView(int loadingTime, final BaseFragment_NetworkState.ShowFinishListenr showFinishListenr) {
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
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
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

    /**
     * 添加子Fragment的布局文件
     *
     * @param inflater
     */
    private void addChildView(LayoutInflater inflater) {
        networkStateView = (NetworkStateView) rootView.findViewById(R.id.nsv_state_view);
        FrameLayout container = (FrameLayout) rootView.findViewById(R.id.fl_fragment_child_container);
        childView = inflater.inflate(setContentViewId(), null);
        container.addView(childView, 0);
    }
}
