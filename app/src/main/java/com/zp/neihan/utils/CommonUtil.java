package com.zp.neihan.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.TextView;


import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类,公共方法;
 *
 * @author zlq
 * @createdTime 2014-7-28下午5:25:53
 */
public class CommonUtil {

    private Resources resource;

    private static String TAG = "CommonUtil";



    /**
     * 判断object是否为null或者为"" true:没有数据;<br>
     * true:没有数据;<br>
     * false:有数据;
     *
     * @param obj Object
     * @return boolean
     */
    public static boolean isNullAndEmpty(Object obj) {
        if (obj != null && !"".equals(obj.toString())
                && !"null".equalsIgnoreCase((obj.toString()))) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNullAndEmpty2(Object obj) {
        if (obj != null && !"".equals(obj.toString())
                && !"null".equalsIgnoreCase(obj.toString())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断数组是否为null或者有没有数据;<br>
     * true:没有数据;<br>
     * false:有数据;
     *
     * @param objs 数组
     * @return boolean
     */
    public static boolean isNullAndEmpty(Object[] objs) {
        if (objs != null && objs.length > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * List集合里面是否有数据<br>
     * true:没有数据;<br>
     * false:有数据;
     *
     * @param list List集合
     * @return boolean
     */
    public static boolean isNullAndEmpty(Collection<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 对以分隔符来作为间隔拼装字符串的.进行分割.转换为数组;
     *
     * @param strs      以分隔符进行拼接的字符串,如 abc,cdd,fff,ddd
     * @param separated 拼装的分隔符;
     * @return String[]
     */
    public static String[] splitString(String str, String separated) {
        String[] strs = null;
        if (!isNullAndEmpty(str)) {
            if (str.endsWith(separated)) {
                str = str.substring(0, str.length() - 1);
            }
            strs = str.split(separated);
        }
        return strs;
    }

    /**
     * 对两个数进行四则运算; <strong>对于除法的运算,保留4位小数;</strong><br>
     * result = num1+num2;<br>
     * result = num1-num2;<br>
     * result = num1*num2;<br>
     * result = num1/num2;<br>
     *
     * @param num1       式子中的第一个数;
     * @param num2       式子中的第二个数;
     * @param calcSymbol 运算符号 "+" "-" "*" "/"
     * @return BigDecimal
     * @throws Exception 计算异常;
     */
    public static BigDecimal calcNumber(Object num1, Object num2,
                                        String calcSymbol) throws Exception {
        if (!isNullAndEmpty(num1) && !isNullAndEmpty(num2)) {
            BigDecimal decimal = new BigDecimal(num1.toString());
            BigDecimal decima2 = new BigDecimal(num2.toString());
            if ("+".equals(calcSymbol)) {
                return decimal.add(decima2);
            } else if ("-".equals(calcSymbol)) {
                return decimal.subtract(decima2);
            } else if ("*".equals(calcSymbol)) {
                return decimal.multiply(decima2);
            } else if ("/".equals(calcSymbol)) {
                if (!num2.equals("0")) {
                    return decimal.divide(decima2, 4,
                            BigDecimal.ROUND_HALF_DOWN);
                } else {
                    throw new Exception();
                }
            } else {
                return null;
            }
        }
        return null;

    }

    /**
     * 把大的数字用中文的单位来替代<br>
     * 返回的中文单位有:<b>万</b>、<b>亿</b> 不足<b>万</b>的,直接返回原数据;
     *
     * @param num1 数值
     * @return 中文单位的表式
     */
    public static String getChineseNumber(Double num1) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        if (num1 >= 100000000) {
            return (format.format(((num1 / 100000000))) + "亿");
        } /*
         * else if (num1 >= 10000000) { return (format.format((num1 / 10000000))
		 * + "千万"); } else if (num1 >= 1000000) { return (format.format((num1 /
		 * 1000000)) + "百万"); } else if (num1 >= 100000) { return
		 * (format.format((num1 / 100000)) + "十万"); }
		 */ else if (num1 >= 10000) {
            return (format.format((num1 / 10000)) + "万");
        } else {
            return (num1.intValue() + "");
        }
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param String s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int getStringLength(String s) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            } else {
                // 其他字符长度为1
                valueLength += 1;
            }
        }
        // 进位取整
        return valueLength;
    }

    /**
     * 将json转换成Map格式;
     *
     * @param json JSONObject
     * @return Map&lt;String,Object&gt;
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonParseToMap(JSONObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!isNullAndEmpty(json)) {
                Iterator<String> iterator = json.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    map.put(key, json.get(key));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return map;
    }

    /**
     * 将json转换成Map格式;
     *
     * @param json JSONObject
     * @return Map&lt;String,Object&gt;
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> jsonParseToMap(String json) {
        Map<String, String> map = new HashMap<String, String>();
        try {

            if (!isNullAndEmpty(json)) {
                JSONObject obj = new JSONObject(json);
                Iterator<String> iterator = obj.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    map.put(key, obj.get(key).toString());
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return map;
    }

    /**
     * 将bundle转换成Map格式;
     *
     * @param bundle Bundle
     * @return Map&lt;String,Object&gt;
     * @throws JSONException
     */
    public static Map<String, Object> bundleParseToMap(Bundle bundle) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!isNullAndEmpty(bundle)) {
                Set<String> keys = bundle.keySet();
                for (String key : keys) {
                    map.put(key, bundle.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return map;
    }

    /**
     * 获取EditText文本框上的长度;
     *
     * @param editText EditText
     * @return length
     */
    public static int getEditTextLength(EditText editText) {
        if (isNullAndEmpty(editText))
            return 0;
        return editText.getText().length();
    }


    /**
     * 把Map转换为Message
     *
     * @param values Map&lt;String,Object&gt;
     * @return Message
     */
    public static Message getMessage(Map<String, Object> values) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        if (!isNullAndEmpty(values)) {
            Iterator<String> iterator = values.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                bundle.putString(key, values.get(key).toString());
            }
        }
        message.setData(bundle);
        return message;
    }

    /**
     * Fragment页面切换;
     *
     * @param frgActivity    FragmentActivity
     * @param fragment       Fragment 要切换到的目标Fragment,切换到该Fragment;
     * @param frameLayoutId  frameLayoutId FrameLayout布局的id;
     * @param mapValues      Map&lt;String,Object&gt;切换页面所传带的参数;
     * @param bundle         Bundle形式传递参数;
     * @param addToBackStack 是否缓存上一个页面,如果是,当按下手机上的返回键时,会后退到上一个页面;
     */
    private static void changeFragment(FragmentActivity frgActivity,
                                       Fragment fragment, int frameLayoutId,
                                       Map<String, Object> mapValues, Bundle bundle, boolean addToBackStack) {
        if (mapValues != null && mapValues.size() > 0) {
            Set<String> strs = mapValues.keySet();
            if (bundle == null) {
                bundle = new Bundle();
            }
            for (String str : strs) {
                bundle.putString(str, mapValues.get(str).toString());
            }
        }
        FragmentTransaction frgTransaction;
        FragmentManager frgManager;
        frgManager = frgActivity.getSupportFragmentManager();
        frgTransaction = frgManager.beginTransaction();
        frgTransaction.replace(frameLayoutId, fragment);
        fragment.setArguments(bundle);
        if (addToBackStack) {
            frgTransaction.addToBackStack(null);
        }
        frgTransaction.commit();
    }

    /**
     * 无参数的Fragment页面切换;
     *
     * @param frgActivity    FragmentActivity
     * @param fragment       Fragment 要切换到的目标Fragment,切换到该Fragment;
     * @param frameLayoutId  frameLayoutId FrameLayout布局的id;
     * @param addToBackStack 是否缓存上一个页面,如果是,当按下手机上的返回键时,会后退到上一个页面;
     */
    public static void changeFragmentView(FragmentActivity frgActivity,
                                          Fragment fragment, int frameLayoutId, boolean addToBackStack) {
        changeFragment(frgActivity, fragment, frameLayoutId, null, null,
                addToBackStack);
    }

    /**
     * 参数形式为:Map&lt;String,Object&gt; 的Fragment页面切换;
     *
     * @param frgActivity    FragmentActivity
     * @param fragment       Fragment 要切换到的目标Fragment,切换到该Fragment;
     * @param frameLayoutId  frameLayoutId FrameLayout布局的id;
     * @param mapValues      Map&lt;String,Object&gt;切换页面所传带的参数;
     * @param addToBackStack 是否缓存上一个页面,如果是,当按下手机上的返回键时,会后退到上一个页面;
     */
    public static void changeFragmentView(FragmentActivity frgActivity,
                                          Fragment fragment, int frameLayoutId,
                                          Map<String, Object> mapValues, boolean addToBackStack) {
        changeFragment(frgActivity, fragment, frameLayoutId, mapValues, null,
                addToBackStack);
    }

    /**
     * 参数形式为:Bundle的Fragment页面切换;
     *
     * @param frgActivity    FragmentActivity
     * @param fragment       Fragment 要切换到的目标Fragment,切换到该Fragment;
     * @param frameLayoutId  frameLayoutId FrameLayout布局的id;
     * @param bundle         Bundle形式传递参数;
     * @param addToBackStack 是否缓存上一个页面,如果是,当按下手机上的返回键时,会后退到上一个页面;
     */
    public static void changeFragmentView(FragmentActivity frgActivity,
                                          Fragment fragment, int frameLayoutId, Bundle bundle,
                                          boolean addToBackStack) {
        changeFragment(frgActivity, fragment, frameLayoutId, null, bundle,
                addToBackStack);
    }

    /**
     * 参数形式以:Map&lt;String,Object&gt;和Bundle的Fragment页面切换;
     *
     * @param frgActivity    FragmentActivity
     * @param fragment       Fragment 要切换到的目标Fragment,切换到该Fragment;
     * @param frameLayoutId  frameLayoutId FrameLayout布局的id;
     * @param mapValues      Map&lt;String,Object&gt;切换页面所传带的参数;
     * @param bundle         Bundle形式传递参数;
     * @param addToBackStack 是否缓存上一个页面,如果是,当按下手机上的返回键时,会后退到上一个页面;
     */
    public static void changeFragmentView(FragmentActivity frgActivity,
                                          Fragment fragment, int frameLayoutId,
                                          Map<String, Object> mapValues, Bundle bundle, boolean addToBackStack) {
        changeFragment(frgActivity, fragment, frameLayoutId, mapValues, bundle,
                addToBackStack);
    }

    /**
     * 如果访问数据库失败!则调用此方法;
     *
     * @param values Map
     */
    public static Map<String, Object> errorMap() {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("end", "error");
        values.put("message", "访问异常!");
        return values;
    }

    /**
     * 改变字体颜色,可改变部分字体颜色;
     *
     * @param text  字符串
     * @param start 想要改变字符串的起始位置(包括)
     * @param end   想要改变字符串的结束位置(不包括)
     * @param color 颜色 Color.RED
     * @return SpannableStringBuilder 可以直接TextView.setText();赋值
     */
    public static SpannableStringBuilder setFontColor(String text, int start,
                                                      int end, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        // //设置指定位置文字的颜色
        style.setSpan(new ForegroundColorSpan(color), start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        /*
         * // 设置指定位置textview的背景颜色 style.setSpan(new
		 * BackgroundColorSpan(Color.RED), 2,
		 * 5,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		 */
        return (style);
    }

    /**
     * 改变字体颜色;
     *
     * @param text  字符串
     * @param color 颜色 例如 Color.RED
     * @return SpannableStringBuilder SpannableStringBuilder
     */
    public static SpannableStringBuilder setFontColor(String text, int color) {
        return setFontColor(text, 0, text.length(), color);
    }

    /**
     * 获取登录识别码;
     *
     * @param context Context 上下文
     * @return 返回登陆识别码,""空字符串代表没登陆,或登录失效;
     */
    public static String getauthorization(Context context) {
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences("sp_xiaocaimi", Context.MODE_PRIVATE);
        return sp.getString("sp_xiaocaimi", "");
    }

    /**
     * 清空登录识别码;使登录失效;
     *
     * @param context Context 上下文
     */
    public static void cleanauthorization(Context context) {
        SharedPreferences sp = context.getSharedPreferences("sp_xiaocaimi",
                Context.MODE_PRIVATE);
        Editor ed = sp.edit();
        ed.putString("sp_xiaocaimi", "");
        ed.commit();
    }

    /**
     * 此方法只针对从接口返回的MoneyProject的JSON格式;
     *
     * @param moneyProjectJSON MoneyProject的JSON格式;
     * @param leftTopImg       左上角的图片
     * @param attentionImg     关注图片
     * @return Map&lt;String,Object&gt;
     * @throws Exception
     */
    public static Map<String, Object> packMapFromJSON(
            JSONObject moneyProjectJSON, int leftTopImg, int attentionImg)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // JSONObject mp = new JSONObject(json.get("moneyProject").toString());
        JSONObject user = new JSONObject(moneyProjectJSON.get("user")
                .toString());
        JSONObject leadInvestors = new JSONObject(moneyProjectJSON
                .getJSONObject("leadInvestor").toString());
        if (moneyProjectJSON != null) {
            DecimalFormat dformat = new DecimalFormat("0.00");
            NumberFormat numFormat = NumberFormat.getInstance();
            numFormat.setMaximumFractionDigits(2);
            map.put("id", moneyProjectJSON.getString("id"));
            map.put("productImg", leftTopImg);// R.drawable.logo_recommend
            map.put("attention", attentionImg);// R.id.mp_attention
            map.put("title", moneyProjectJSON.getString("title"));
            map.put("yyzt", moneyProjectJSON.getString("yyzt"));
            map.put("recommendTime",
                    moneyProjectJSON.getString("recommendTime"));
            map.put("recommend", "团长（" + user.get("nickNames") + "）发言："
                    + moneyProjectJSON.getString("recommend"));
            map.put("isRecommend", moneyProjectJSON.get("isRecommend"));
            // String hasCopies =
            // QwyUtil.calcNumber(json.getString("hasCopies"), 1,
            // "/").toString();
            map.put("hasCopies",
                    "额度剩余 " + moneyProjectJSON.getString("hasCopies") + " 元");

            // 理财产品的基本信息;
            String yzqx = moneyProjectJSON.getString("yzqx");
            map.put("earnings",
                    "预期收益：" + moneyProjectJSON.getString("earnings") + "%（"
                            + yzqx + "）");
            Double temp = CommonUtil.calcNumber(
                    moneyProjectJSON.getString("financingAmount"), 100, "/")
                    .doubleValue();
            String financingAmout = numFormat.format(temp);
            map.put("financingAmount", "投资额度：￥" + financingAmout);
            if (user != null) {
                map.put("nicknames", "领  投  人：" + user.get("nickNames"));

            }
            map.put("zskz", "止损控制：承诺在" + moneyProjectJSON.getString("stops")
                    + "%以内");
            Double nowPrice = CommonUtil.calcNumber(
                    moneyProjectJSON.getString("nowPrice"), 100, "/")
                    .doubleValue();
            map.put("nowPrice", "当前价值：" + dformat.format(nowPrice) + "元每份 ↑");
            map.put("prices", "购买价格：1.0元每份");

            // 领投人信息;
            if (leadInvestors != null) {
                map.put("allCount",
                        "总发起次数：" + leadInvestors.getString("allCount") + " 次");
                Double averageEarnings = CommonUtil.calcNumber(
                        leadInvestors.getString("averageEarnings"), 100, "/")
                        .doubleValue();
                map.put("averageEarnings",
                        "平均盈利：￥" + dformat.format(averageEarnings));

                Double totalRevenue = CommonUtil.calcNumber(
                        leadInvestors.getString("totalRevenue"), 100, "/")
                        .doubleValue();
                map.put("totalRevenue", "总体盈利：￥" + dformat.format(totalRevenue));
            }

            // 投资计划
            map.put("zjtx", "资金投向：" + moneyProjectJSON.getString("zjtx"));
            map.put("financingAmount2", "总  金  额：￥" + financingAmout);
            map.put("yzqx", "运作期限：" + yzqx);
            map.put("fxkz", "风险控制：保证本金" + moneyProjectJSON.getString("fxkz"));

            map.put("noCopies", moneyProjectJSON.getString("noCopies"));
            map.put("status", moneyProjectJSON.getString("status"));

            // 募集天数
            String dayNum = "";
            Object raisingTheNumberOfDays = moneyProjectJSON
                    .get("raisingTheNumberOfDays");
            if (!isNullAndEmpty(raisingTheNumberOfDays)
                    && !"null".equalsIgnoreCase(raisingTheNumberOfDays
                    .toString())) {
                int hour = isNullAndEmpty(raisingTheNumberOfDays) ? 0 : Integer
                        .parseInt(raisingTheNumberOfDays.toString());
                int day = hour / 24;
                hour = hour % 24;
                if (day > 0) {
                    dayNum = "募集天数：" + day + "天" + hour + "小时";
                } else {
                    dayNum = "募集天数：" + hour + "小时";
                }
            }
            map.put("raisingTheNumberOfDays", dayNum);

            // 理财产品的总收益
            Object cost = moneyProjectJSON.get("actualEarningsCost");
            double actualEarningsCost = 0.0;
            if (!isNullAndEmpty(cost)
                    && !"null".equalsIgnoreCase(cost.toString())) {

                actualEarningsCost = calcNumber(
                        isNullAndEmpty(cost) ? 0 : cost, 100, "/")
                        .doubleValue();
            }
            map.put("actualEarningsCost", dformat.format(actualEarningsCost));
        }
        return map;
    }

    /**
     * 此方法只针对从接口返回的Map为以下格式; {end:(ok|error),list:[moneyProject对象数组]},且填充的是
     * MoneyProject;<br>
     * 直接处理接口返回的Map数据,并返回List&lt;Map&lt;String, Object&gt;&gt;<br>
     * 可直接填充BaseAdapter的List&lt;? extends Map&lt;String, ?&gt;&gt;
     *
     * @param maps         接口返回的Map,格式如下:<br>
     *                     {end:(ok|error),list:[moneyProject对象数组]}
     * @param leftTopImg   左上角的图片
     * @param attentionImg 关注图片
     * @return List&lt;Map&lt;String, Object&gt;&gt; 返回的是填充ListView的List集合;
     */
    public static List<Map<String, Object>> packMapFromInterfaceDate(
            Map<String, Object> maps, int leftTopImg, int attentionImg) {
        List<Map<String, Object>> listViewMap = new ArrayList<Map<String, Object>>();
        if (maps != null) {
            String isOk = maps.get("end").toString();
            if ("ok".equalsIgnoreCase(isOk)) {
                String li = maps.get("list").toString();
                try {
                    JSONArray jsons = new JSONArray(li);
                    if (jsons != null && jsons.length() > 0) {
                        for (int i = 0; i < jsons.length(); i++) {
                            try {
                                JSONObject json = new JSONObject(jsons.get(i)
                                        .toString());
                                Map<String, Object> map = packMapFromJSON(json,
                                        leftTopImg, attentionImg);
                                listViewMap.add(map);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        return listViewMap;
    }

    /**
     * 将map对象转化成json字符串
     *
     * @param Map <String, Object> values
     * @return
     */
    public static String simpleMapToJsonStr(Map<String, Object> values) {
        if (values == null || values.isEmpty()) {
            return "null";
        }
        String jsonStr = "{";
        Set<?> keySet = values.keySet();
        for (Object key : keySet) {
            jsonStr += "\"" + key + "\":\"" + values.get(key) + "\",";
        }
        jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
        jsonStr += "}";
        return jsonStr;
    }

    public static String map2JSON(Map map) {
        Iterator iter = map.entrySet().iterator();
        JSONObject holder = new JSONObject();

        while (iter.hasNext()) {
            Map.Entry pairs = (Map.Entry) iter.next();
            String key = (String) pairs.getKey();
            Map m = (Map) pairs.getValue();
            JSONObject data = new JSONObject();

            try {
                Iterator iter2 = m.entrySet().iterator();
                while (iter2.hasNext()) {
                    Map.Entry pairs2 = (Map.Entry) iter2.next();
                    data.put((String) pairs2.getKey(),
                            (String) pairs2.getValue());
                }
                holder.put(key, data);
            } catch (JSONException e) {
                Log.e("Transforming", "There was an error packaging JSON", e);
            }
        }

        return holder.toString();
    }

    // {"pass":"4355","name":"12342","wang":"fsf"}
    public Map getData(String str) {
        String sb = str.substring(1, str.length() - 1);
        String[] name = sb.split("\\\",\\\"");
        String[] nn = null;
        Map map = new HashMap();
        for (int i = 0; i < name.length; i++) {
            nn = name[i].split("\\\":\\\"");
            map.put(nn[0], nn[1]);
        }
        return map;
    }

    /**
     * 验证是否是手机号码
     *
     * @param 是手机号码返回true
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String p_phone = "^[1][345789][0-9]{9}$";
        Pattern pattern_1 = Pattern.compile(p_phone);
        Matcher m_phone = pattern_1.matcher(mobiles);
        return m_phone.matches();

    }

    /**
     * 长度转MB
     *
     * @param size
     * @return
     */
    public static String convertFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }



    /**
     * 返回当前页面名称
     */
    public static String getCurrentPageName(Context context) {

        ActivityManager activityManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        String pageName = activityManager.getRunningTasks(1).get(0).topActivity
                .getClassName();
        String[] name = pageName.split("\\.");
        return name[name.length - 1];

    }

    /**
     * 整数去小数点后面的00 例如：（12.00=12）
     * （* 此方法只适合整数去尾）
     *
     * @param value 值
     */
    public static String integerFilter(String value) {
        // char charAt = annual.charAt(annual.indexOf(".") + 1);
        value = CommonUtil.doubleFilter(value, 2).toString();
        int index = value.indexOf(".");
        if (index != -1) {

            String subDecimal = value.substring(index + 1);
            if ("00".equals(subDecimal))
                value = value.substring(0, index);
        }

        return value;
    }

    /**
     * 小数保留几位小数 例如保留2位：（12.9999=12.99）
     * （* 此方法只适合小数去尾，直接去尾不是四舍五入再去尾）
     *
     * @param value                值
     * @param baoliu_jiwei_xiaoshu 保留几位小数
     */
    public static BigDecimal doubleFilter(String value, int baoliu_jiwei_xiaoshu) {
        //BigDecimal bd = new BigDecimal(value);
        //如果value是 double类型    value=3.98;  实际 bd=3.979999999;
        //如果value是 String类型    value=3.98;  实际 bd=3.98;
        BigDecimal bd = new BigDecimal(value);

        return bd.setScale(baoliu_jiwei_xiaoshu, BigDecimal.ROUND_DOWN);
    }

    /**
     * 小数保留几位小数 例如保留2位：（12.9999=12.99）
     * （* 此方法只适合小数去尾，直接去尾不是四舍五入再去尾）
     *
     * @param value 值
     * @param
     */
    public static String doubleFormat(String value) {
        String result = "";
        int index = value.indexOf(".");
        if (index != -1) {

            result = value.substring(0, index);
        }
        return result;
    }

    /**
     * 将分转化为元
     *
     * @param value 钱(单位:分)
     */
    public static String change2Money(long value) {

        String money = "";

        try {

            String decimal = CommonUtil.doubleFilter(CommonUtil.calcNumber(value,
                    "0.01", "*").toString(), 2).toString();
            //int index = decimal.indexOf(".");

            String subDecimal = decimal.substring(decimal.indexOf(".") + 1);
            if ("00".equals(subDecimal))
                money = decimal.substring(0, decimal.indexOf("."));
            else
                money = decimal;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return money;
    }

    /**
     * 将分转化为元
     *
     * @param value 钱(单位:分)
     */
    public static String change2Money(double value) {

        String money = "";

        try {

            String decimal = CommonUtil.calcNumber(value,
                    "0.01", "*").toString();
            money = CommonUtil.doubleFilter(decimal, 2).toString();

            int index = money.indexOf(".");
            String temp = money.substring(index + 1, money.length());
            if ("00".equals(temp)) {

                money = money.substring(0, index);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return money;
    }

    /**
     * 将元转化为分
     *
     * @param money 钱(单位:元)
     */
    public static String change2MoneyFen(String money) {

        String result = "";

        try {
            String decimal = CommonUtil.calcNumber(money,
                    "100", "*").toString();
            int index = decimal.indexOf(".");
            if (index != -1) {

                String subDecimal = decimal.substring(decimal.indexOf(".") + 1);
                if ("00".equals(subDecimal))
                    result = decimal.substring(0, decimal.indexOf("."));
                else
                    result = decimal;
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * textView 根据最大宽度，设置textSize
     *
     * @param tv
     * @param maxWidth
     * @param text
     */
    public static void adjustTvTextSize(TextView tv, int maxWidth, String text) {
        int avaiWidth = maxWidth - tv.getPaddingLeft() - tv.getPaddingRight() - 10;

        if (avaiWidth <= 0) {
            return;
        }

        TextPaint textPaintClone = new TextPaint(tv.getPaint());
        // note that Paint text size works in px not sp
        float trySize = textPaintClone.getTextSize();
        // Log.d("xxx", "修改前的TextView的文本的Size是--->>>"+trySize);
        while (textPaintClone.measureText(text) > avaiWidth) {
            trySize--;
            textPaintClone.setTextSize(trySize);
        }

        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        //  Log.d("xxx", "修改后的TextView的文本的Size是--->>>"+trySize);
    }

    /**
     * 获取chanel
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {


        ApplicationInfo appInfo = null;
        String channel = "0";
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);

            Object obj = appInfo.metaData.get("CHANNEL");
            channel = obj.toString();
        } catch (Exception e) {

        }
        return channel;
    }


    /**
     * 整数去小数点后面的00 例如：（12.00=12）
     * （* 此方法只适合整数去尾）
     *
     * @param value 值
     */
    public static String integerFilter2(String value) {
        // char charAt = annual.charAt(annual.indexOf(".") + 1);
        if ('0' == (value.charAt(value.indexOf(".") + 2)))
            return value.substring(0, value.indexOf("."));
        else
            return value;
    }

    /**
     * 金额
     * 1 分专元
     * 2 保留2位小数
     * 3 去尾00
     *
     * @param value
     * @return
     */
    public static String ValueFilter(String value) {

        return change2Money(Double.parseDouble(value));
    }
}
