package com.zzrb.util;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    // 创建rsa公私钥对
    public static KeyPair initKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        return generator.generateKeyPair();
    }

    public static String generateKeyPair() throws Exception {
        KeyPair keyPair = initKeyPair();

        JSONObject jsonObjectPriv  = new JSONObject();
        jsonObjectPriv.put("type","anbo/PrivKeyRSA");
        jsonObjectPriv.put("value",getPrivateKey(keyPair));

        JSONObject jsonObjectPub  = new JSONObject();
        jsonObjectPub.put("type","anbo/PubKeyRSA");
        jsonObjectPub.put("value",getPublicKey(keyPair));

        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("priv_key",jsonObjectPriv);
        jsonObject.put("pub_key",jsonObjectPub);
        return jsonObject.toString();
    }

    //获取公钥(Base64编码)
    private static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return new String(Base64.getEncoder().encode(bytes));
    }

    //获取私钥(Base64编码)
    private static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey =  keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return new String(Base64.getEncoder().encode(bytes));
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static RSAPublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = Base64.getDecoder().decode(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static RSAPrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = Base64.getDecoder().decode(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

}
