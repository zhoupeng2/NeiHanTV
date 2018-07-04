package okhttp.callback;

import android.util.Log;


import com.zp.neihan.utils.CommonUtil;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * Created by wjh on 16/6/4.
 * 
 * 		
 * 
 */
public abstract class MapCallback extends Callback<Map<String, Object>> {

	private Map<String, Object> response;

	@Override
	public void onBefore(Request request) {
		// TODO Auto-generated method stub
		onPrepare();

	}

	@Override
	public Map<String, Object> parseNetworkResponse(Response response)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = CommonUtil.jsonParseToMap(new JSONObject(
				response.body().string()));
		return map;
	}

	@Override
	public void onResponse(Map<String, Object> response) {
		// TODO Auto-generated method stub
		this.response = response;
		Log.d("xxx",response+"");
		String end = (String) response.get("end");
		String message = (String) response.get("message");

		if ("ok".equals(end)) {
			OKResult(response);
		} else if ("error".equals(end)) {
			ErrorResult(message);
		} else if ("noData".equals(end)) {
			noDataResult(message);
		} else if ("noLogin".equals(end)) {
			noLoginResult(message);
		} else
			ErrorResult(message);
	}

	@Override
	public void onError(Call call, Exception e) {
		// TODO Auto-generated method stub
		ErrorResult("网络或服务器异常");
	}

	@Override
	public void onAfter() {
		// TODO Auto-generated method stub
		onComplete(response);
	}

	public abstract void onPrepare();

	public abstract void OKResult(Map<String, Object> map);

	public abstract void ErrorResult(String message);

	public abstract void noDataResult(String message);

	public abstract void noLoginResult(String message);

	public abstract void onComplete(Map<String, Object> map);
}