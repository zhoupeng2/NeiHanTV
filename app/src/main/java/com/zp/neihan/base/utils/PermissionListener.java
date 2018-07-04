package com.zp.neihan.base.utils;

import java.util.List;

/**
 * Created by ZHT on 2017/4/19.
 * 权限回调接口
 */

public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> deniedPermissions);

    /**
     * 不再询问
     */
    void onAskNoMore();
}
