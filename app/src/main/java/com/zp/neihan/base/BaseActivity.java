package com.zp.neihan.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.zp.neihan.R;
import com.zp.neihan.base.utils.ActivityUtil;
import com.zp.neihan.base.utils.PermissionListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/16.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;

    private static PermissionListener mPermissionListener;
    private static final int CODE_REQUEST_PERMISSION = 1;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId();
        /**
         * ButterKnife 使用前绑定，记得在onDestroy()销毁
         */
        unbinder = ButterKnife.bind(this);
        context = this;
        sp = context
                .getSharedPreferences("sp_xiaocaimi", Context.MODE_PRIVATE);
        ActivityUtil.getManager().addActivity(this);
        initView();
        initLogic(savedInstanceState);
        initEvent();
    }
    protected abstract void setLayoutId();
    /**
     * 子类复写初始化界面
     */
    protected abstract void initView();
    protected abstract void initLogic(Bundle savedInstanceState);
    /**
     * 子类复写初始化事件
     */
    protected abstract void initEvent();

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
            Window window =this.getWindow();
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
    }

    /**
     * 打开 手机设置---手机应用信息
     *
     * @param permission 权限名字
     */
    public void OpenAPPSetting(String permission) {
        //用户点击了禁止以后不再询问,下次打开的时候跳转到设置界面，让用户手动打开权限
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
        //   startActivityForResult(intent,code);
        Toast.makeText(context, "APP缺少必要权限,请点击\"设置\"-\"权限\"打开 \"" + permission + "\" 权限", Toast.LENGTH_SHORT).show();
    }
}
