package com.zzrb.ecc;

import com.alibaba.fastjson.JSONObject;
import com.zzrb.util.ECCUtil;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

public class AnboECCCrypt {

    private static String anbo_pub_key;
    static {
        anbo_pub_key = getPubKeyStrByResources();
    }

    //从classpath中获取anbo_pub_key.json文件
    private static String getPubKeyStrByResources(){
        InputStream is = AnboECCSign.class.getClass().getResourceAsStream("/anbo_pub_key.json");
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
        return JSONObject.parseObject(jsonObject.get("pub_key").toString()).get("value").toString();
    }

    //使用sdk中默认平台公钥加密
    public String encrypt(String data) throws Exception {
        byte[] bytes = publicEncrypt(data.getBytes(),anbo_pub_key);
        return Base64.getEncoder().encodeToString(bytes);

    }

    public String decrypt(String data, String privateKeyStr) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(data);
        return new String(privateDecrypt(bytes,privateKeyStr));
    }

    //公钥加密
    private byte[] publicEncrypt(byte[] data, String publicKeyStr) throws Exception{
        ECPublicKey publicKey = ECCUtil.string2PublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(ECCUtil.ECIES, ECCUtil.BC);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(data);
        return bytes;
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
