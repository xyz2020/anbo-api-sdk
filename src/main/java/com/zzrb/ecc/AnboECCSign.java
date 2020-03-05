package com.zzrb.ecc;

import com.alibaba.fastjson.JSONObject;
import com.zzrb.util.ECCUtil;
import org.bouncycastle.jce.interfaces.ECPrivateKey;

import java.io.*;
import java.security.*;
import java.util.*;

public class AnboECCSign{

    //私钥文件
    private static final String fileName = "/key_pair.json";
    private static final String privKey = "priv_key";
    private static final String value = "value";

    //私钥文件中私钥
    private static String privateKeyStr;

    static {
        privateKeyStr = getPrivateKeyStrByResources();
    }

    //从classpath中获取private_key.json文件
    private static String getPrivateKeyStrByResources(){
        InputStream is = AnboECCSign.class.getClass().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s="";
        String configContentStr = "";
        try {
            while((s=br.readLine())!=null) {
                configContentStr = configContentStr+s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(configContentStr);
        return JSONObject.parseObject(jsonObject.get(privKey).toString()).get(value).toString();
    }

    //执行签名
    public String sign(Map<String,String> data) throws Exception {
        ECPrivateKey privateKey = ECCUtil.string2PrivateKey(privateKeyStr);
        byte[] dataBytes = ECCUtil.dataMap2byte(data);
        // 2.执行签名[私钥签名]
        Signature signature = Signature.getInstance(ECCUtil.SIGNALGORITHM, ECCUtil.BC);
        signature.initSign(privateKey);
        signature.update(dataBytes);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

}
