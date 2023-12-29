package com.blockatm.demo.sign;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @description test your signature by run main function
 * @date 2020-10-08 23:05
 */
public class TestForWebHook {

    // demo test private key
    private static String privateKey = "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCAaXAqWZaVeWG4zQWjAT+RqFUwMKfLIAOQQqi6a3JObrg==";
    // demo test public key
    private static String publickKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEsjqDDEF5uuVoAFCxftskpMszLQMro888rFlrowcTzVibc4J2PgDywFZYXc6x21yHcenJ2bCZ/03XkMrbKqqEwA==";
    //  get from request header  BlockATM-Request-Time
    private static Long time = 1696947336603l;



    // your webhook URL that set in blockATM,please distinguish  different environments
    public static final String YOUR_WEBHOOK_URL = "https://backstage-b2b.ufcfan.org/api/v1/webhook/callback";


    public static final String HEADER_SIGNATURE = "BlockATM-Signature-V1";

    public static final String HEADER_REQUEST_TIME = "BlockATM-Request-Time";




    /**
     * webhook回调请求头签名
     */
    public static final String HEADER_EVENT= "BlockATM-Event";

    /**
     * webhook 充值事件
     */
    public static final String WEBHOOK_EVENT_PAYMENT = "Payment";


    /**
     * webhook 代付事件
     */
    public static final String WEBHOOK_EVENT_PAYOUT = "Payout";

    public static void main(String[] args) throws Exception {
    // -----------  payment webhook test begin -------------------
        DemoObj obj = new DemoObj();
        String waitSignStr = SignatureUtils.getWaitSignString(obj, time);
        // you can get from request header BlockATM-Signature-V1
        String signature = ECDSAUtils.sign(waitSignStr, privateKey);

        boolean checkResult = ECDSAUtils.verify(waitSignStr, signature, publickKey);

        System.out.println("wait sign string: " + waitSignStr);
        System.out.println("signature: " + signature);
        System.out.println("check signature result: " + checkResult);

        String result = HttpUtil.createPost(YOUR_WEBHOOK_URL).header(HEADER_SIGNATURE, signature)
                .header(HEADER_REQUEST_TIME, time.toString())
                .header(HEADER_EVENT, WEBHOOK_EVENT_PAYMENT)
                .body(JSON.toJSONString(obj))
                .execute().body();
        System.out.println("Payment webhook result :" +result);
        // -----------  payment webhook test end -------------------


        // -----------  pyaout webhook test begin -------------------
        String testMsg = "{\"blockId\":\"10168195\",\"chainId\":\"5\",\"contractAddress\":\"0x57609702e66d6dee9d1f3a9fab376b95b9ec9e02\",\"detailList\":[{\"amount\":\"1.00000000\",\"orderNo\":\"TX1\",\"symbol\":\"USDT\",\"toAddress\":\"0x2d7ff2dc166ae09542c749be052028e43825cde7\",\"tokenAddress\":\"0x92efdfa35c75b259375ebe0f84ee1d95db0489b6\"},{\"amount\":\"1.00000000\",\"orderNo\":\"TX2\",\"symbol\":\"USDC\",\"toAddress\":\"0x2d7ff2dc166ae09542c749be052028e43825cde7\",\"tokenAddress\":\"0x2f96275bbb4a54714ef0251226c42811fb9f98aa\"}],\"fromAddress\":\"0x8e5df55ac224db7424fa8536eda9356f44474936\",\"merchantId\":20,\"network\":\"ETH\",\"txId\":\"0xa85f090dc5e722177ddc2cd2d4d87467820635cf736ec1a5910997118e3d3cb6\"}";
        waitSignStr = SignatureUtils.getWaitSignString(JSONObject.parseObject(testMsg, JSONObject.class), time);
        // you can get from request header BlockATM-Signature-V1
        signature = ECDSAUtils.sign(waitSignStr, privateKey);
        checkResult = ECDSAUtils.verify(waitSignStr, signature, publickKey);

        System.out.println("wait sign string: " + waitSignStr);
        System.out.println("signature: " + signature);
        System.out.println("check signature result: " + checkResult);

        result = HttpUtil.createPost(YOUR_WEBHOOK_URL).header(HEADER_SIGNATURE, signature)
                .header(HEADER_REQUEST_TIME, time.toString())
                .header(HEADER_EVENT, WEBHOOK_EVENT_PAYOUT)
                .body(testMsg)
                .execute().body();
        System.out.println("payout webhook result :" +result);
        // -----------  payment webhook test end -------------------


    }


}
