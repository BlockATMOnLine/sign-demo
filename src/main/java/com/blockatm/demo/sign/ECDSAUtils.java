package com.blockatm.demo.sign;


import javafx.util.Pair;

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
    public static final int KEY_SIZE = 256;

    private static String CHARSET = "UTF-8";


    /**
     * create Key pair whith EC
     * @return left is publicKey ,right is private Key
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException  e) {
            throw e;
        }
    }


    public static PublicKey stringToPublicKey(String publicKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey stringToPrivateKey(String privateKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    public static boolean verify(String data, String signature, PublicKey publicKey) throws Exception {
        Signature verifySignature = Signature.getInstance(SIGN_ALGORITHM);
        verifySignature.initVerify(publicKey);
        verifySignature.update(data.getBytes(StandardCharsets.UTF_8));
        return verifySignature.verify(decodeFromString(signature));
    }

    public static boolean verify(String data, String signature, String publicKey) throws Exception {
        return verify(data, signature, stringToPublicKey(publicKey));
    }

    public static String encodeToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String sign(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return encodeToString(signature.sign());
    }

    public static String sign(String data, PrivateKey privateKey) throws Exception {
        return sign(data.getBytes(CHARSET), privateKey);
    }

    public static String sign(String data, String privateKey) throws Exception {
        return sign(data.getBytes(CHARSET), stringToPrivateKey(privateKey));
    }




    public static byte[] decodeFromString(String str) {
        return Base64.getDecoder().decode(str);
    }


}
