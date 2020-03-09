package com.zzrb.ecc;

import com.zzrb.util.ECCCryptUtil;
import com.zzrb.util.FileUtil;

import java.util.Base64;

public class AnboECCDecrypt {

    private static final String fileName = "key_pair.json";
    private static final String privkey = "priv_key";
    private static final String value = "value";

    private static String priv_key;
    static {
        priv_key = FileUtil.getKeyStrByResources(fileName,privkey,value);
    }

    public String decrypt(String data) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(data);
        return new String(ECCCryptUtil.privateDecrypt(bytes,priv_key));
    }

}
