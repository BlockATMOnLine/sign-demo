package com.blockatm.demo.sign;

import java.lang.reflect.Field;
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
     * get sign string
     *
     * @param obj  the request obj
     * @param time request header BlockATM-Request-Time
     * @return
     */
    public static String getSignStr(Object obj, long time) {
        if(obj instanceof String){
            return obj + "&time=" + time;
        }
        String s = concatenateProperties(obj);
        return s + "&time=" + time;
    }



    /**
     * check sign from request header
     *
     * @param data      request data obj
     * @param time      request header BlockATM-Request-Time
     * @param signature request header BlockATM-Signature-V1
     * @param publicKey your public Key
     * @return
     * @throws Exception
     */
    public static boolean checkSignature(Object data, long time, String signature, String publicKey) throws Exception {
        String signStr = getSignStr(data, time);
        return ECDSAUtils.verify(signStr, signature, publicKey);
    }


    /**
     * Concatenate the properties of an Object by using Key=Value pairs
     *
     * @param obj
     * @return
     */
    public static String concatenateProperties(Object obj) {
        if(obj == null){
            return "";
        }
        List<String> propertyList = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                // check field not null
                if (value != null) {
                    String key = field.getName();
                    String property = key + "=" + value.toString();
                    propertyList.add(property);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // sort property by assci
        Collections.sort(propertyList);
        // connect with &
        String concatenatedProperties = String.join("&", propertyList);
        return concatenatedProperties;
    }


}
