package com.zzrb.ecc;

import com.alibaba.fastjson.JSONObject;
import com.zzrb.util.ECCUtil;
import org.bouncycastle.jce.interfaces.ECPrivateKey;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

public class AnboECCDecrypt {

    private static final String fileName = "key_pair.json";
    private static final String privkey = "priv_key";
    private static final String value = "value";

    private static String priv_key;
    static {
        priv_key = getPubKeyStrByResources();
    }

    //从classpath中获取anbo_pub_key.json文件
    private static String getPubKeyStrByResources(){
        InputStream is = AnboECCDecrypt.class.getClassLoader().getResourceAsStream(fileName);
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
        return JSONObject.parseObject(jsonObject.get(privkey).toString()).get(value).toString();
    }


    public String decrypt(String data) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(data);
        return new String(privateDecrypt(bytes,priv_key));
    }

    public String decrypt(String data, String privateKeyStr) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(data);
        return new String(privateDecrypt(bytes,privateKeyStr));
    }

    //私钥解密
    private byte[] privateDecrypt(byte[] data, String privateKeyStr) throws Exception{
        ECPrivateKey privateKey = ECCUtil.string2PrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(ECCUtil.ECIES,ECCUtil.BC);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(data);
        return bytes;
    }
}
