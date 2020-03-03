package com.zzrb.sign;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Map;

public class AnboECCVerify extends BaseECC{

    // 验证签名
    public static boolean verify(String publKeyStr, Map<String,String> data, String sign) throws Exception {
        PublicKey publicKey = string2PublicKey(publKeyStr);
        byte[] signBytes = Base64.getDecoder().decode(sign);
        byte[] dataBytes = dataMap2byte(data);
        return verify(publicKey,dataBytes,signBytes);
    }
    // 验证签名
    private static boolean verify(PublicKey publicKey, byte[] data, byte[] sign) throws Exception {
        // 3.验证签名[公钥验签]
        Signature signature = Signature.getInstance(SIGNALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }
}
