package com.zzrb.ecc;

import com.alibaba.fastjson.JSONObject;
import com.zzrb.util.ECCUtil;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Signature;
import java.util.Base64;
import java.util.Map;

public class AnboECCVerify{

    private static final String fileName = "/anbo_pub_key.json";
    private static final String pubKey = "pub_key";
    private static final String value = "value";

    private static String anbo_pub_key;
    static {
        anbo_pub_key = getPubKeyStrByResources();
    }

    //从classpath中获取anbo_pub_key.json文件
    private static String getPubKeyStrByResources(){
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
        return JSONObject.parseObject(jsonObject.get(pubKey).toString()).get(value).toString();
    }

    // 使用sdk默认的平台公钥验签
    public boolean verify(Map<String,String> data, String sign) throws Exception{
        ECPublicKey publicKey = ECCUtil.string2PublicKey(anbo_pub_key);
        byte[] signBytes = Base64.getDecoder().decode(sign);
        byte[] dataBytes = ECCUtil.dataMap2byte(data);
        return verify(publicKey,dataBytes,signBytes);
    }

    // 验证签名，传入公钥
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
