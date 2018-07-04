package okhttp;



import com.zp.neihan.utils.CommonUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp.callback.Callback;
import okhttp.callback.MapCallback;
import okhttp.interfaces.ResultCallBack;
import okhttp3.Response;

/**
 * 
 * @author wjh 请求网络根据需要分为post,get请求
 */

public class OkHttpHelper {

	/**
	 * 
	 * @param url
	 *            地址
	 * @param params
	 *            请求参数
	 * @param resultCallBack
	 *            异步请求回调,返回map集合
	 */
	public static void getDataAsynByPost(String url,
			Map<String, String> params, final ResultCallBack resultCallBack) {

		try {
			OkHttpUtils.post().url(url).params(params).build()
					.execute(new MapCallback() {

						@Override
						public void OKResult(Map<String, Object> map) {
							// TODO Auto-generated method stub
							resultCallBack.okResult(map);
						}

						@Override
						public void noLoginResult(String message) {
							// TODO Auto-generated method stub
							resultCallBack.noLoginResult(message);
						}

						@Override
						public void noDataResult(String message) {
							// TODO Auto-generated method stub
							resultCallBack.noDataResult(message);
						}

						@Override
						public void ErrorResult(String message) {
							// TODO Auto-generated method stub
							resultCallBack.errorResult(message);
						}

						@Override
						public void onPrepare() {
							// TODO Auto-generated method stub
							resultCallBack.onPrepare();
						}

						@Override
						public void onComplete(Map<String, Object> map) {
							// TODO Auto-generated method stub
							resultCallBack.onComplete(map);
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param url
	 *            地址
	 * @param params
	 *            请求参数
	 * @param resultCallBack
	 *            同步请求回调,返回map集合
	 */
	public static void getDataSynByPost(String url, Map<String, String> params,
			final ResultCallBack resultCallBack) {

		try {
			Response response = OkHttpUtils.post().url(url).params(params)
					.build().execute();
			Map<String, Object> map = CommonUtil.jsonParseToMap(new JSONObject(
					response.body().string()));
			String end = (String) map.get("end");
			String message = (String) map.get("message");
			if ("ok".equals(end)) {
				resultCallBack.okResult(map);
			} else if ("error".equals(end)) {
				resultCallBack.errorResult(message);
			} else if ("noData".equals(end)) {
				resultCallBack.noDataResult(message);
			} else if ("noLogin".equals(end))
				resultCallBack.noLoginResult(message);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultCallBack.errorResult("网络或服务器异常");
		}

	}

	public static void getDataAsynByGet(String url,
			final ResultCallBack resultCallBack) {

		try {
			OkHttpUtils.get().url(url).build().execute(new MapCallback() {

				@Override
				public void onPrepare() {
					// TODO Auto-generated method stub
					resultCallBack.onPrepare();
				}

				@Override
				public void OKResult(Map<String, Object> map) {
					// TODO Auto-generated method stub
					resultCallBack.okResult(map);
				}

				@Override
				public void noLoginResult(String message) {
					// TODO Auto-generated method stub
					resultCallBack.noLoginResult(message);
				}

				@Override
				public void noDataResult(String message) {
					// TODO Auto-generated method stub
					resultCallBack.noDataResult(message);
				}

				@Override
				public void ErrorResult(String message) {
					// TODO Auto-generated method stub
					resultCallBack.errorResult(message);
				}

				@Override
				public void onComplete(Map<String, Object> map) {
					// TODO Auto-generated method stub
					resultCallBack.onComplete(map);
				}

			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param callback
	 *            特定的callback
	 */
	public static void getDataAsynByPost(String url,
			Map<String, String> params, Callback callback) {

		try {
			OkHttpUtils.post().url(url).params(params).build()
					.execute(callback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param callback
	 *            特定的callback
	 */
	public static void getDataAsynByPostWithTag(String url,
			Map<String, String> params, String tag, Callback callback) {

		try {
			OkHttpUtils.post().url(url).params(params).tag(tag).build()
					.execute(callback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param url
	 *            异步请求地址
	 * @param callback
	 *            特定的callback
	 */
	public static void getDataAsynByGet(String url, Callback callback) {
		try {
			OkHttpUtils.get().url(url).build().execute(callback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param callback
	 *            特定的callback
	 */
	public static Response getDataSynByGet(String url) {

		Response response = null;
		try {

			response = OkHttpUtils.get().url(url).build().execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param callback
	 * 
	 */
	public static String getDataSynByPost(String url, Map<String, String> params) {

		String result = "";
		try {
			Response response = OkHttpUtils.post().url(url).params(params)
					.build().execute();
			result = response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "";
		}
		return result;
	}
}
