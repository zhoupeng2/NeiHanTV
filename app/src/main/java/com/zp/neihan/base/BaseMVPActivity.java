package com.zp.neihan.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zp.neihan.R;
import com.zp.neihan.base.utils.ActivityUtil;
import com.zp.neihan.base.utils.NetworkStateView;
import com.zp.neihan.base.utils.PermissionListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Activity基类
 *
 * @author ZHT (GitHub)
 * @Copyright HangZhou XCM Technology Co., Ltd.
 * @CreatedDate 2017/04/18
 */
public abstract class BaseMVPActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements NetworkStateView.OnRefreshListener {
    protected T mPresenter;//Presenter对象

    private View childView;
    private Unbinder unbinder;

    /**
     * 导航栏
     */
    public RelativeLayout common_top;
    /**
     * 网络加载视图
     */
    private NetworkStateView networkStateView;

    private static PermissionListener mPermissionListener;
    private static final int CODE_REQUEST_PERMISSION = 1;

    /**
     * 延迟加载
     */
    private long startTime;
    private CountDownTimer countDownTimer;

    protected Context context;
    /**
     * 存储一般的信息（轻量级的存储）
     */
    protected SharedPreferences sp;

    /**
     * 公共 沉浸式布局 View
     */
    public View chenjin_view, background_if_is_white_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 初始化布局
         */
        setContentView(setLayoutId());
        /**
         * ButterKnife 使用前绑定，记得在onDestroy()销毁
         */
        unbinder = ButterKnife.bind(this);
        startTime = System.currentTimeMillis();
        context = this;
        sp = context
                .getSharedPreferences("sp_xiaocaimi", Context.MODE_PRIVATE);
        ActivityUtil.getManager().addActivity(this);
        common_top = (RelativeLayout) findViewById(R.id.common_top);

        //创建Presenter
        mPresenter = createPresenter();
        //内存泄漏
        //关联View
        mPresenter.attachView((V) this);

        initView();
        initLogic(savedInstanceState);
        initEvent();
    }

    @SuppressLint("InflateParams")
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        //设置填充activity_base布局
        super.setContentView(view);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setFitsSystemWindows(true);
        }

        //加载子类Activity的布局
        initDefaultView(layoutResID);
    }

    /**
     * 初始化默认布局的View
     *
     * @param layoutResId 子View的布局id
     */
    private void initDefaultView(int layoutResId) {
        networkStateView = (NetworkStateView) findViewById(R.id.nsv_state_view);
        FrameLayout container = (FrameLayout) findViewById(R.id.fl_activity_child_container);
        childView = LayoutInflater.from(this).inflate(layoutResId, null);
        container.addView(childView, 0);
    }

    /**
     * 初始化默认布局的View
     *
     * @return ContentView
     */
    protected abstract int setLayoutId();

    /**
     * 子类复写初始化界面布局
     */
    protected abstract void initView();

    /**
     * 子类复写初始化界面逻辑
     */
    protected abstract void initLogic(Bundle savedInstanceState);

    /**
     * 子类复写初始化事件
     */
    protected abstract void initEvent();

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
    public void customShowCotentView(int loadingTime) {

        long endTime = System.currentTimeMillis();
        long dTime = endTime - startTime;
        if (dTime < loadingTime) {
            countDownTimer = new CountDownTimer(loadingTime - dTime, loadingTime - dTime) {
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


    /**
     * 申请权限
     *
     * @param permissions 需要申请的权限(数组)
     * @param listener    权限回调接口
     */
    public static void requestPermissions(String[] permissions, PermissionListener listener) {
        Activity activity = ActivityUtil.getManager().currentActivity();
        if (null == activity) {
            return;
        }

        mPermissionListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            //权限没有授权
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), CODE_REQUEST_PERMISSION);
        } else {
            mPermissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_REQUEST_PERMISSION:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int result = grantResults[i];
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        boolean isSecondRequest = ActivityCompat
                                .shouldShowRequestPermissionRationale(this,
                                        permissions[0]);
                        if (isSecondRequest) {
                            mPermissionListener.onDenied(deniedPermissions);
                        } else {
                            mPermissionListener.onAskNoMore();
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 调整沉浸式菜单的title
     */
    public void dealStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**
             * 将状态栏设置为沉浸式
             */
            Window window = this.getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //公共 沉浸式布局 View
            chenjin_view = findViewById(R.id.chenjin_view);
            //公共 沉浸式布局 View
            background_if_is_white_view = findViewById(R.id.background_if_is_white_view);
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
     * (为什么导航栏是白色背景就调用此方法呢？)
     * 因为 导航栏的系统文字颜色是白色，白色文字在白色背景上看不清楚
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityUtil.getManager().finishActivity(this);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        mPresenter.detachView();
    }
}
