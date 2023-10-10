/**
 *
 */
package com.blockatm.demo.sign;




import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author john
 * @description ECDSA util
 * @date 2023/10/2 下午4:04
 */
public class ECDSAUtils {


    public static final String KEY_ALGORITHM = "EC";
    public static final String SIGN_ALGORITHM = "SHA256withECDSA";

    public static PublicKey stringToPublicKey(String publicKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    public static boolean verify(String data, String signature, PublicKey publicKey) throws Exception {
        Signature verifySignature = Signature.getInstance(SIGN_ALGORITHM);
        verifySignature.initVerify(publicKey);
        verifySignature.update(data.getBytes(StandardCharsets.UTF_8));
        return verifySignature.verify(decodeFromString(signature));
    }

    public static boolean verify(String data, String signature, String publicKey) throws Exception {
        return verify( data,  signature,  stringToPublicKey(publicKey));
    }


    public static byte[] decodeFromString(String str) {
        return Base64.getDecoder().decode(str);
    }


}
