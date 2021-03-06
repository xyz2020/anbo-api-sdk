package com.zzrb.ecc;

import com.zzrb.util.ECCCryptUtil;
import com.zzrb.util.ECCUtil;
import com.zzrb.util.FileUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AnboECCEncrypt {

    private static final String fileName = "anbo_pub_key.json";
    private static final String pubKey = "pub_key";
    private static final String value = "value";

    private static String anbo_pub_key;
    static {
        try {
            anbo_pub_key = FileUtil.getKeyStrByResources(fileName,pubKey,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //使用sdk中默认平台公钥加密
    public String encrypt(String data) throws Exception {
        byte[] bytes = ECCCryptUtil.publicEncrypt(ECCUtil.string2Bytes(data),anbo_pub_key);
        return Base64.getEncoder().encodeToString(bytes);
    }

}
