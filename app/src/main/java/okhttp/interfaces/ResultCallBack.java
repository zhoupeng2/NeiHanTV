package okhttp.interfaces;

import java.util.Map;

/**
 * 
 * @author wjh
 *			返回结果的callback
 */
public abstract class ResultCallBack {

	public abstract void onPrepare();
	public abstract void onComplete(Map<String,Object> map);
	public abstract void okResult(Map<String,Object> map);
	public abstract void noLoginResult(String message);
	public abstract void noDataResult(String message);
	public abstract void errorResult(String message);
}
