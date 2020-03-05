package com.zzrb.ecc;

import com.zzrb.util.ECCUtil;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import java.security.Signature;
import java.util.Base64;
import java.util.Map;

public class AnboECCVerify{

    // 验证签名
    public boolean verify(String publKeyStr, Map<String,String> data, String sign) throws Exception {
        ECPublicKey publicKey = ECCUtil.string2PublicKey(publKeyStr);
        byte[] signBytes = Base64.getDecoder().decode(sign);
        byte[] dataBytes = ECCUtil.dataMap2byte(data);
        return verify(publicKey,dataBytes,signBytes);
    }

    // 验证签名
    private boolean verify(ECPublicKey publicKey, byte[] data, byte[] sign) throws Exception {
        // 3.验证签名[公钥验签]
        Signature signature = Signature.getInstance(ECCUtil.SIGNALGORITHM,ECCUtil.BC);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }
}
