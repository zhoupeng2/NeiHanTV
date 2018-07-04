package com.zp.neihan.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.zp.neihan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zlq on 10/8/13.
 */
public class ZlqUtils {

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static void wrrapViewHeight(View v,int height){

		v.getLayoutParams().height = height;
		v.requestLayout();
	}


	/**
	 * int转ip格式
	 * 
	 * @param i
	 * @return
	 */
	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	/**
	 * 是否为手机号码
	 * 
	 * @param mobiles
	 *            验证的号码
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		String p_phone = "^[1][35789][0-9]{9}$";
		Pattern pattern_1 = Pattern.compile(p_phone);
		Matcher m_phone = pattern_1.matcher(mobiles);
		return m_phone.matches();

	}

	public static void covertdata(char data[], int len) {
		// 高低位对调
		for (int i = 0; i < len / 2; i++) {
			data[i] += data[len - 1 - i];
			data[len - 1 - i] = (char) (data[i] - data[len - 1 - i]);
			data[i] = (char) (data[i] - data[len - 1 - i]);
		}
	}

	/**
	 * 用于大数相乘
	 * 
	 * @param a
	 * @param alen
	 * @param b
	 * @param blen
	 * @return
	 */
	public static String multiply(char a[], int alen, char b[], int blen) {
		String result = "";
		// 两数乘积位数不会超过乘数位数和+ 3位
		int csize = alen + blen + 3;
		// 开辟乘积数组
		int[] c = new int[csize];
		// 乘积数组填充0
		for (int ii = 0; ii < csize; ii++) {
			c[ii] = 0;
		}
		// 对齐逐位相乘
		for (int j = 0; j < blen; j++) {
			for (int i = 0; i < alen; i++) {
				c[i + j] += Integer.parseInt(String.valueOf(a[i]))
						* Integer.parseInt(String.valueOf(b[j]));
			}
		}
		int m = 0;
		// 进位处理
		for (m = 0; m < csize; m++) {
			int carry = c[m] / 10;
			c[m] = c[m] % 10;
			if (carry > 0)
				c[m + 1] += carry;
		}
		// 找到最高位
		for (m = csize - 1; m >= 0;) {
			if (c[m] > 0)
				break;
			m--;
		}
		for (int n = 0; n <= m; n++) {
			result += c[m - n] + "";
		}
		return result;
	}

	/**
	 * 网络是否可用
	 */
	public static boolean checkNetworkState(Context context) {
		boolean flag = false;
		// 得到网络连接信息
		ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		return flag;
	}

	/**
	 * 日期转换
	 * 
	 * @param insertTime
	 * @return
	 */
	public static String getFormatTime(Long insertTime) {
		Date date = new Date(insertTime);
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ss.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return ss.format(date);

	}

	/**
	 * 日期转换
	 * 
	 * @param insertTime
	 * @return
	 */
	public static String getFormatTime(String insertTime) {
		Date date = new Date(Long.parseLong(insertTime));
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ss.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return ss.format(date);

	}


	public static int checkUrl(final String url) {

		URL address_url;
		try {
			address_url = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) address_url
					.openConnection();

			connection.setConnectTimeout(8000);
			connection.setRequestMethod("GET");
			int response_code = connection.getResponseCode();
			return response_code;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}



	/**
	 * 动态设置ListView的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	/**
	 * 动态设置ListView的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(Context context,ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		try {
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = (ViewGroup) listAdapter.getView(i, null,
						listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		} catch (Exception e) {

			for (int i = 0; i < listAdapter.getCount(); i++) {
				
				totalHeight += ZlqUtils.dip2px(context, 80);
			}
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 动态设置ListView的高度
	 * 
	 * @param gridView
	 */
	public static void setListViewHeightBasedOnChildren(GridView gridView) {
		if (gridView == null)
			return;
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int count = listAdapter.getCount();

		int totalHeight;
		View listItem = listAdapter.getView(0, null, gridView);
		listItem.measure(0, 0);
		totalHeight = listItem.getMeasuredHeight();

		if (count % 2 == 0)
			totalHeight = count / 2 * totalHeight;
		else
			totalHeight = count / 2 * totalHeight + totalHeight;
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + ((listAdapter.getCount() / 2 - 1));
		gridView.setLayoutParams(params);
	}



	/**
	 * 
	 * @param number
	 *            号码
	 * @return XXX****XXXX
	 */
	public static String secretNumber(String number) {

		String secretNumber = number.substring(0, 3).concat("****")
				.concat(number.substring(7, number.length()));
		return secretNumber;
	}



	/**
	 * 计算状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 测量View的宽高
	 *
	 * @param view View
	 */
	public static void measureWidthAndHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
	}

}
