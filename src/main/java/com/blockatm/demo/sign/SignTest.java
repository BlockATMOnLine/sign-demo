package com.blockatm.demo.sign;

/**
 * @description test your signature by run main function
 * @date 2020-10-08 23:05
 */
public class SignTest {

    // demo test private key
    private static String privateKey = "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCAaXAqWZaVeWG4zQWjAT+RqFUwMKfLIAOQQqi6a3JObrg==";
    // demo test public key
    private static String publickKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEsjqDDEF5uuVoAFCxftskpMszLQMro888rFlrowcTzVibc4J2PgDywFZYXc6x21yHcenJ2bCZ/03XkMrbKqqEwA==";
    //  get from request header  BlockATM-Request-Time
    private static long time = 1696947336603l;
    // get from request header BlockATM-Signature-V1
    private static String signature = "MEYCIQDHxQ0IhgUNbRqTKbU71fBkp+lAJlMXEQYt6mDQfWRY7gIhAMWIpVoG6qBhgIPi30x30wLlAaxyhptZfm6nMRz75VxA";

    public static void main(String[] args) throws Exception {
        DemoObj obj = new DemoObj();
        String signStr = SignatureUtils.getSignStr(obj, time);
        boolean checkResult = SignatureUtils.checkSignature(obj, time, signature, publickKey);
        System.out.println("your wait check sign string: " + signStr);
        System.out.println("signature check result: " + checkResult);
    }


}
