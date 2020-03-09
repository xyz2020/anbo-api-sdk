package com.zzrb.ecc;

import com.zzrb.util.ECCSignUtil;
import com.zzrb.util.FileUtil;

import java.util.*;

public class AnboECCSign{

    //私钥文件
    private static final String fileName = "key_pair.json";
    private static final String privKey = "priv_key";
    private static final String value = "value";

    //私钥文件中私钥
    private static String privateKeyStr;

    static {
        privateKeyStr = FileUtil.getKeyStrByResources(fileName,privKey,value);
    }

    //执行签名
    public String sign(Map<String,String> data) throws Exception {
        return ECCSignUtil.sign(privateKeyStr,data);
    }
}
