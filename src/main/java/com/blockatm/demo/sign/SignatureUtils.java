package com.blockatm.demo.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author john
 * @description check signatrue demo util
 * @date 2023/10/2 下午4:04
 */
public class SignatureUtils {

    /**
     * get wait sign string
     * @param obj  the request obj
     * @param time request header BlockATM-Request-Time
     * @return
     */
    public static String getWaitSignString(Object obj, long time){
        if(obj == null){
            return appendTime(null,time);
        }
        if(obj instanceof String){
            return appendTime((String) obj,time);
        }
        String jsonStr = JSON.toJSONString(obj);
        String waitSinStr = getWaitSignStr(jsonStr);
        return appendTime(waitSinStr,time);
    }




    public static String appendTime(String message,long time){
        if(message == null || "".equals(message)){
            return "time="+time;
        }
        return new StringBuilder(message).append("&time=").append(time).toString();
    }

    /**
     * Concatenate the properties of an JSON by using Key=Value pairs
     * @param jsonStr
     * @return
     */
    private static String getWaitSignStr(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        StringBuilder result = new StringBuilder();
        sortJsonObject(jsonObject, result);
        return result.toString();
    }

    private static void sortJsonObject(JSONObject jsonObject, StringBuilder result) {
        List<String> keys = new ArrayList<>(jsonObject.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            Object value = jsonObject.get(key);
            if(value == null){
                continue;
            }
            if (value instanceof JSONObject) {
                sortJsonObject((JSONObject) value, result);
            } else if (value instanceof JSONArray) {
                sortJsonArray((JSONArray) value, result);
            } else {
                if (result.length() > 0) {
                    result.append("&");
                }
                result.append(key).append("=").append(value);
            }
        }
    }

    private static void sortJsonArray(JSONArray jsonArray, StringBuilder result) {
        for (int i = 0; i < jsonArray.size(); i++) {
            Object value = jsonArray.get(i);
            if(value == null){
                continue;
            }
            if (value instanceof JSONObject) {
                sortJsonObject((JSONObject) value, result);
            } else if (value instanceof JSONArray) {
                sortJsonArray((JSONArray) value, result);
            } else {
                if (result.length() > 0) {
                    result.append("&");
                }
                result.append(value);
            }
        }
    }


}
