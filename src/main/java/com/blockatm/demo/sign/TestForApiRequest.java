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
        // create Key Pair
        KeyPair keyPair = ECDSAUtils.generateKeyPair();
        // get the private key,the key stored in your server
        String privateKey = ECDSAUtils.encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println("private Key:"+privateKey);
        // get the public key,the key stored in blockATM server
        String publicKey = ECDSAUtils.encodeToString(keyPair.getPublic().getEncoded());
        System.out.println("ppublic Key:"+publicKey);

        System.out.println("--------------------");

        // ---------  get key Pair end  -----------





        // ---------  send a request to blockATM for query contract info start -----------

        privateKey = "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCAaXAqWZaVeWG4zQWjAT+RqFUwMKfLIAOQQqi6a3JObrg==";
        String path = "/api/v1/contract/contractAddtress";
        String queryParams = "chainId=5";
        //you can find the apiKey in BlockAT M setting
        String apiKey = "414555CB-2569-4015-9F67-D0219A4E0D3E";
        //the request time
        long time = System.currentTimeMillis();
        String waitSignString = SignatureUtils.getWaitSignString(queryParams, time);
        System.out.println("wait sign message:"+waitSignString);
        String signature = ECDSAUtils.sign(waitSignString,privateKey);
        String response = HttpUtil.createGet(BLOCKATM_API_URL + path)
                .header(HEADER_REQUEST_APIKey,apiKey)
                .header(HEADER_SIGNATURE, signature)
                .header(HEADER_REQUEST_TIME, time + "")
                .header(HEADER_RECV_WINDOW, "5000").form("chainId", 5).execute().body();


        System.out.println(response);



        // ---------  send a request to blockATM  end -----------

    }


}
