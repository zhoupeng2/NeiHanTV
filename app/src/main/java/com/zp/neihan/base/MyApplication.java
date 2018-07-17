package com.zp.neihan.base;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.request.RequestOptions;
import com.kk.taurus.ijkplayer.IjkPlayer;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.kk.taurus.playerbase.entity.DecoderPlan;
import com.zp.neihan.R;

/**
 * @CreatedDate 2018/7/3
 */
public class MyApplication extends Application {
    /**
     * 设置默认解码器
     */
    public static final int PLAN_ID_IJK = 1;

    private static MyApplication instance;
    public static Context sContext;
    public static boolean ignoreMobile;


    public static MyApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        instance = this;



        //...
        //如果您想使用默认的网络状态事件生产者，请添加此行配置。
        //并需要添加权限 android.permission.ACCESS_NETWORK_STATE
        PlayerConfig.setUseDefaultNetworkEventProducer(true);


        PlayerConfig.addDecoderPlan(new DecoderPlan(PLAN_ID_IJK, IjkPlayer.class.getName(), "IjkPlayer"));
        PlayerConfig.setDefaultPlanId(PLAN_ID_IJK);
        //初始化库
        PlayerLibrary.init(this);
    }
}
