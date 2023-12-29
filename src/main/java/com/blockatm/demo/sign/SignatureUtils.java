package com.blockatm.demo.sign;

import com.alibaba.fastjson.JSON;
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
            return time+"";
        }
        if(obj instanceof String){
            return obj+"&time="+time;
        }
        String jsonStr = JSON.toJSONString(obj);
        String waitSinStr = getWaitSignStr(jsonStr);
        return waitSinStr+"&time="+time;
    }




    public static String joinSignStrAndTime(String message,Long time){
        if(message == null || "".equals(message)){
            return time.toString();
        }
        return new StringBuilder(message).append("&time=").append(time).toString();
    }

    /**
     * Concatenate the properties of an JSON by using Key=Value pairs
     * @param jsonStr
     * @return
     */
    private static String getWaitSignStr(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr, JSONObject.class);
        List<String> keys = new ArrayList<>(jsonObject.keySet());
        Collections.sort(keys);
        StringBuilder result = new StringBuilder();
        for (String key : keys) {
            String value = jsonObject.get(key).toString();
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(key).append("=").append(value);
        }
        return result.toString();
    }


}
