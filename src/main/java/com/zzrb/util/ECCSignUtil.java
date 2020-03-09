package com.zzrb.util;

import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import java.security.Signature;
import java.util.Base64;
import java.util.Map;

public class ECCSignUtil {

    //执行签名
    public static String sign(String privKey, Map<String,String> data) throws Exception {
        ECPrivateKey privateKey = ECCUtil.string2PrivateKey(privKey);
        byte[] dataBytes = ECCUtil.dataMap2byte(data);
        // 2.执行签名[私钥签名]
        Signature signature = Signature.getInstance(ECCUtil.SIGNALGORITHM, ECCUtil.BC);
        signature.initSign(privateKey);
        signature.update(dataBytes);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    // 验证签名，传入公钥
    public static boolean verify(String publKeyStr, Map<String,String> data, String sign) throws Exception {
        ECPublicKey publicKey = ECCUtil.string2PublicKey(publKeyStr);
        byte[] signBytes = Base64.getDecoder().decode(sign);
        byte[] dataBytes = ECCUtil.dataMap2byte(data);

        Signature signature = Signature.getInstance(ECCUtil.SIGNALGORITHM,ECCUtil.BC);
        signature.initVerify(publicKey);
        signature.update(dataBytes);
        return signature.verify(signBytes);
    }

}
