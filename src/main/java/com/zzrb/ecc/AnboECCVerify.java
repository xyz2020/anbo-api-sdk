package com.zzrb.ecc;

import com.zzrb.util.ECCSignUtil;
import com.zzrb.util.FileUtil;

import java.util.Map;

public class AnboECCVerify{

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

    // 使用sdk默认的平台公钥验签
    public boolean verify(Map<String,String> data, String sign) throws Exception{
        return ECCSignUtil.verify(anbo_pub_key,data,sign);
    }
}
