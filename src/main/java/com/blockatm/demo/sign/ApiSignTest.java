package com.blockatm.demo.sign;

import java.security.KeyPair;

/**
 * @description test your signature by run main function
 * @date 2020-10-08 23:05
 */
public class ApiSignTest {


    public static void main(String[] args) throws Exception {
        // create Key Pair
        KeyPair keyPair = ECDSAUtils.generateKeyPair();
        // get the private key,the key stored in your server
        String privateKey = ECDSAUtils.encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println("private Key:"+privateKey);
        // get the public key,the key stored in blockATM server
        String publicKey = ECDSAUtils.encodeToString(keyPair.getPublic().getEncoded());
        System.out.println("ppublic Key:"+publicKey);

        System.out.println("--------------------");
        //the request time
        long time = 1696947336603l;

        // ---------  get sign message from object start  -----------
        DemoObj obj = new DemoObj();
        String signMeesage = SignatureUtils.getSignStr(obj, time);
        System.out.println("sign object message:"+signMeesage);
        String signature = ECDSAUtils.sign(signMeesage, privateKey);
        System.out.println("signature:"+signature);
        boolean checkResult = SignatureUtils.checkSignature(obj, time, signature, publicKey);
        System.out.println("object checkResult:"+checkResult);
        // ---------  get sign message from object end -----------
        System.out.println("--------------------");

        // ---------  get sign message from query params start  -----------
        String queryParams = "orderNo=123&chainId=5";
        signMeesage = SignatureUtils.getSignStr(queryParams, time);
        System.out.println("sign query params message:"+signMeesage);
        signature = ECDSAUtils.sign(signMeesage, privateKey);
        System.out.println("signature:"+signature);
        checkResult = SignatureUtils.checkSignature(queryParams,time,signature,publicKey);
        System.out.println("query params checkResult:"+checkResult);
        // ---------  get sign message from query params end -----------
    }


}
