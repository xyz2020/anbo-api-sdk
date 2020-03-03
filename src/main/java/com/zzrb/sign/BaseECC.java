package com.zzrb.sign;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BaseECC {
    public static final String EC = "EC";    //密码原理
    public static final String SIGNALGORITHM = "SHA256withECDSA";    //签名算法
    public static final int KEYSIZE = 256; //大小

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = Base64.getDecoder().decode(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(EC);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = Base64.getDecoder().decode(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(EC);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    // 源数据的map,排序，拼接字符串：k1=v1k2=v2k3=v3,返回byte数组
    public static byte[] dataMap2byte(Map<String, String> data){
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(data);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        return basestring.toString().getBytes();
    }
}
