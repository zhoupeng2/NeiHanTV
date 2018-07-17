package com.zp.neihan.splash.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zp.neihan.R;
import com.zp.neihan.base.MyApplication;
import com.zp.neihan.home.ui.activity.MainActivity;
import com.zp.neihan.utils.CommonUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ZhouPeng
 * @Copyright HangZhou XCM Technology Co., Ltd.
 * @CreatedDate 2018/7/4
 */
public class SplashActivity extends AppCompatActivity {
    private Unbinder bind;

    @BindView(R.id.img_splash_advertising)
    ImageView imgSplashAdvertising;
    @BindView(R.id.img_splash_defult)
    ImageView imgSplashDefult;

    private WeakReference<ImageView> ivDefultPicWeakReference;
    private WeakReference<ImageView> imgSplashAdvertisingWeakReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bind = ButterKnife.bind(this);

        RequestOptions glideOptins = new RequestOptions()
                .placeholder(R.drawable.ic_splash)    //加载成功之前占位图
                .error(R.mipmap.ic_launcher)    //加载错误之后的错误图
                .centerCrop();
        /**
         * 活动图
         */
        Glide.with(this)
                .load("http://ojyz0c8un.bkt.clouddn.com/b_1.jpg")
                .apply(glideOptins)
                .into(imgSplashAdvertising);


        ivDefultPicWeakReference = new WeakReference<ImageView>(imgSplashDefult);
        imgSplashAdvertisingWeakReference= new WeakReference<ImageView>(imgSplashAdvertising);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ivDefultPicWeakReference != null) {
                    ivDefultPicWeakReference.get().setVisibility(View.GONE);
                    imgSplashAdvertisingWeakReference.get().setVisibility(View.VISIBLE);
                }
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                  toMainActivity();
            }
        }, 3500);

    }

    private void toMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
