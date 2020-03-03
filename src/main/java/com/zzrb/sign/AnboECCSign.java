package com.zzrb.sign;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class AnboECCSign extends BaseECC{

    //私钥文件
    private static final String fileName = "/private_key.json";
    private static final String privKey = "priv_key";

    //私钥文件中私钥
    static String privateKeyStr;

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
        return jsonObject.get(privKey).toString();
    }

    //执行签名
    public String sign(Map<String,String> data) throws Exception {
        PrivateKey privateKey = string2PrivateKey(privateKeyStr);
        byte[] dataBytes = dataMap2byte(data);
        // 2.执行签名[私钥签名]
        Signature signature = Signature.getInstance(SIGNALGORITHM);
        signature.initSign(privateKey);
        signature.update(dataBytes);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

}
