package com.zp.neihan.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 自定义吐司 时间短  不重复
 * @author zp
 *
 */

public class ToastUtil {

    /*cannot be instantiated*/
    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Toast mToast;
    @SuppressWarnings("unused")
    public static void showToast(Context context, CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);// 居中显示
        mToast.show();
    }
}
