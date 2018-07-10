package com.zp.neihan.home.ui.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zp.neihan.R;
import com.zp.neihan.base.BaseMVPActivity;
import com.zp.neihan.base.BasePresenter;
import com.zp.neihan.home.ui.fragment.MainPageFrament;
import com.zp.neihan.home.ui.fragment.MyAccountFragment;
import com.zp.neihan.utils.CommonString;
import com.zp.neihan.videopage.ui.fragment.NeiHanShiPingFragment;
import com.zp.neihan.home.ui.fragment.NeiHanTuPianFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMVPActivity {
    @BindView(R.id.fragment_content)
    FrameLayout fragmentContent;
    @BindView(R.id.hotBtn)
    ImageView hotBtn;
    @BindView(R.id.productBtn)
    ImageView productBtn;
    @BindView(R.id.myBtn)
    ImageView myBtn;
    @BindView(R.id.findBtn)
    ImageView findBtn;
    @BindView(R.id.rlayout_hot)
    RelativeLayout rlayout_hot;
    @BindView(R.id.rlayout_product)
    RelativeLayout rlayout_product;
    @BindView(R.id.rlayout_find)
    RelativeLayout rlayout_find;
    @BindView(R.id.rlayout_mine)
    RelativeLayout rlayout_mine;
    private MainPageFrament mainPageFrament;
    private NeiHanTuPianFragment neiHanTuPianFragment;
    private NeiHanShiPingFragment neiHanShiPingFragment;
    private MyAccountFragment myAccountFragment;

    private FragmentManager fragmentManager;
    public static MainActivity mainActivityInstance = null;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mainActivityInstance != null) {
            mainActivityInstance = null;
        }
    }

    @Override
    protected void initView() {
        mainActivityInstance = this;
        common_top.setVisibility(View.GONE);
        initFirstFragment();
    }

    @Override
    protected void initLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter() {

        };
    }

    //解决重叠
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //如果用以下这种做法则不保存状态，再次进来的话会显示默认的tab
        // super.onSaveInstanceState(outState);
    }


    @OnClick({R.id.rlayout_hot, R.id.rlayout_product, R.id.rlayout_find, R.id.rlayout_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlayout_hot:

                clickMainPageFragment();
                break;
            case R.id.rlayout_product:

                clickNeiHanTuPianFragment();
                break;
            case R.id.rlayout_find:

                clickNeiHanShiPingFragment();
                break;
            case R.id.rlayout_mine:

                clickMyAccountFragment();
                break;
        }
    }

    // 到热门推荐
    private void clickMainPageFragment() {
        onTabSelect(1);
        FragmentTransaction ft = restoreState();
        if (mainPageFrament == null) {
            mainPageFrament = MainPageFrament.newInstance();
            ft.add(R.id.fragment_content, mainPageFrament);
        }
        ft.show(mainPageFrament).commit();

    }


    // 到产品列表
    private void clickNeiHanTuPianFragment() {
        onTabSelect(2);
        FragmentTransaction ft = restoreState();
        if (neiHanTuPianFragment == null) {
            neiHanTuPianFragment = NeiHanTuPianFragment.newInstance();
            ft.add(R.id.fragment_content, neiHanTuPianFragment);
        }
        ft.show(neiHanTuPianFragment).commit();

    }

    // 到发现界面
    private void clickNeiHanShiPingFragment() {
        onTabSelect(3);
        FragmentTransaction ft = restoreState();
        if (neiHanShiPingFragment == null) {
            neiHanShiPingFragment = NeiHanShiPingFragment.newInstance();
            ft.add(R.id.fragment_content, neiHanShiPingFragment);
        }
        ft.show(neiHanShiPingFragment).commit();

    }

    // 到我的界面
    private void clickMyAccountFragment() {
        onTabSelect(4);
        FragmentTransaction ft = restoreState();
        if (myAccountFragment == null) {
            myAccountFragment = MyAccountFragment.newInstance();
            ft.add(R.id.fragment_content, myAccountFragment);
        }
        ft.show(myAccountFragment).commit();

    }

    private void initFirstFragment() {
        onTabSelect(1);
        fragmentManager = getSupportFragmentManager();
        mainPageFrament = MainPageFrament.newInstance();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_content, mainPageFrament).commit();
    }

    private FragmentTransaction restoreState() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (mainPageFrament != null) {
            ft.hide(mainPageFrament);
        }

        if (neiHanTuPianFragment != null) {
            ft.hide(neiHanTuPianFragment);
        }
        if (neiHanShiPingFragment != null) {
            ft.hide(neiHanShiPingFragment);
        }
        if (myAccountFragment != null) {
            ft.hide(myAccountFragment);
        }


        return ft;
    }

    /**
     * @param tabIndex 当前选择第几页
     */
    private void onTabSelect(int tabIndex) {
        switch (tabIndex) {
            case 1:
                hotBtn.setSelected(true);
                productBtn.setSelected(false);
                findBtn.setSelected(false);
                myBtn.setSelected(false);
                break;
            case 2:
                hotBtn.setSelected(false);
                productBtn.setSelected(true);
                findBtn.setSelected(false);
                myBtn.setSelected(false);
                break;
            case 3:
                hotBtn.setSelected(false);
                productBtn.setSelected(false);
                findBtn.setSelected(true);
                myBtn.setSelected(false);
                break;
            case 4:
                hotBtn.setSelected(false);
                productBtn.setSelected(false);
                findBtn.setSelected(false);
                myBtn.setSelected(true);
                break;
        }
    }


    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 绑定物理返回键

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // creatDialog();
            // 如果两次按键时间间隔大于2000毫秒，则不退出
            // ActivityManager.getInstance().cleanAllActivity();

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                if (CommonString.isLandScape) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    // 记录exitTime
                    exitTime = System.currentTimeMillis();
                }

            } else {
                // 否则退出程序
                exitApp();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
       /* if (downLoding) {
            cleanErrorApk();
            mNotificationManager.cancel(1);
        }*/
        finish();
        // ActivityManager.getInstance().exit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        if (CommonString.isLandScape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }
}
