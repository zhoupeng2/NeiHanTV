package com.zp.neihan.utils;

import android.util.Log;

public class AvoidDoubleClickUtil {

	private static long lastClickTime;

	/**
	 * 只允许点击一次，连续点击无效
	 * @return
	 */
	public synchronized static boolean isDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
