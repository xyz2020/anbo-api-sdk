package com.zzrb.ecc;

import com.alibaba.fastjson.JSONObject;
import com.zzrb.util.ECCUtil;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

public class AnboECCEncrypt {

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

    //使用sdk中默认平台公钥加密
    public String encrypt(String data) throws Exception {
        byte[] bytes = publicEncrypt(data.getBytes(),anbo_pub_key);
        return Base64.getEncoder().encodeToString(bytes);

    }


    //公钥加密
    private byte[] publicEncrypt(byte[] data, String publicKeyStr) throws Exception{
        ECPublicKey publicKey = ECCUtil.string2PublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(ECCUtil.ECIES, ECCUtil.BC);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(data);
        return bytes;
    }

}
