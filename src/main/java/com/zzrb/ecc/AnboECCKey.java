package com.zzrb.ecc;

import com.alibaba.fastjson.JSONObject;
import com.zzrb.util.ECCUtil;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import java.security.*;
import java.util.Base64;

public class AnboECCKey{

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static String generateKeyPair() throws Exception{
        KeyPair keyPair = initKey();

        JSONObject jsonObjectPriv  = new JSONObject();
        jsonObjectPriv.put("type","anbo/PrivKeyECC");
        jsonObjectPriv.put("value",getPrivateKey(keyPair));

        JSONObject jsonObjectPub  = new JSONObject();
        jsonObjectPub.put("type","anbo/PubKeyECC");
        jsonObjectPub.put("value",getPublicKey(keyPair));

        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("priv_key",jsonObjectPriv);
        jsonObject.put("pub_key",jsonObjectPub);
        return jsonObject.toString();
    }

    // 公私钥生成
    private static KeyPair initKey() throws Exception {
        return initKey(new SecureRandom().generateSeed(8));
    }

    private static KeyPair initKey(byte[] seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(ECCUtil.EC,ECCUtil.BC);
        // 初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed);
        keygen.initialize(ECCUtil.KEYSIZE, secureRandom);
        return keygen.genKeyPair();
    }

    //获取公钥(Base64编码)
    private static String getPublicKey(KeyPair keyPair){
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return new String(Base64.getEncoder().encode(bytes));
    }

    //获取私钥(Base64编码)
    private static String getPrivateKey(KeyPair keyPair){
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return new String(Base64.getEncoder().encode(bytes));
    }
}
