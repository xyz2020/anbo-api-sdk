package com.zzrb.ecc;

import com.alibaba.fastjson.JSONObject;
import com.zzrb.util.ECCUtil;

import java.security.*;
import java.util.Base64;

public class AnboECCKey{

    public static String generateKeyPair() throws Exception{
        KeyPair keyPair = initKey();
        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("priv_key",getPrivateKey(keyPair));
        jsonObject.put("pub_key",getPublicKey(keyPair));
        return jsonObject.toString();
    }

    // 公私钥生成
    private static KeyPair initKey() throws Exception {
        return initKey(ECCUtil.KEYSIZE, new SecureRandom().generateSeed(8));
    }

    private static KeyPair initKey(int keySize, byte[] seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(ECCUtil.EC);
        // 初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed);
        keygen.initialize(keySize, secureRandom);
        KeyPair keys = keygen.genKeyPair();
        return keys;
    }

    //获取公钥(Base64编码)
    private static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return new String(Base64.getEncoder().encode(bytes));
    }

    //获取私钥(Base64编码)
    private static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return new String(Base64.getEncoder().encode(bytes));
    }
}
