package com.blockatm.demo.sign;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;

import java.security.KeyPair;

/**
 * @description test your signature by run main function
 * @date 2020-10-08 23:05
 */
public class TestForApiRequest {

    //please distinguish  different environments
    public static final String BLOCKATM_API_URL = "https://backstage-b2b.ufcfan.org";


    public static final String HEADER_SIGNATURE = "BlockATM-Signature-V1";

    public static final String HEADER_REQUEST_TIME = "BlockATM-Request-Time";
    // you can find the api Key in BlockATM
    public static final String HEADER_REQUEST_APIKey = "BlockATM-API-Key";

    public static final String HEADER_RECV_WINDOW = "BlockATM-Rec_Window";


    public static void main(String[] args) throws Exception {
        // ---------  get key Pair start  -----------
        KeyPair keyPair = ECDSAUtils.generateKeyPair();
        String privateKey = ECDSAUtils.encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println("private Key:"+privateKey);
        String publicKey = ECDSAUtils.encodeToString(keyPair.getPublic().getEncoded());
        System.out.println("ppublic Key:"+publicKey);

        System.out.println("--------------------");

        // ---------  get key Pair end  -----------


        // ---------  send a request to blockATM for query contract info start -----------

        privateKey = "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCCk4q7HP8mNm4tl1TuFA20x5d1yeUYSyUHL7+8LHelLtg==";
        String path = "/api/v1/contract/payment";
        String queryParams = "chainId=5";
        String apiKey = "C2B91AF2-EB19-486A-B978-427EB4B1903D";
        long time = System.currentTimeMillis();
        String waitSignString = SignatureUtils.getWaitSignString(queryParams, time);
        System.out.println("wait sign message:"+waitSignString);
        String signature = ECDSAUtils.sign(waitSignString,privateKey);
        String response = HttpUtil.createGet(BLOCKATM_API_URL + path+"?"+queryParams)
                .header(HEADER_REQUEST_APIKey,apiKey)
                .header(HEADER_SIGNATURE, signature)
                .header(HEADER_REQUEST_TIME, time + "")
                .header(HEADER_RECV_WINDOW, "5000").execute().body();
        System.out.println(response);

        // ---------  send a request to blockATM  end -----------

    }


}
