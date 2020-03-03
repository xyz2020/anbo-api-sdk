package com.zzrb.sign;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class AnboECCSign {


    public static final String EC = "EC";    //密码原理
    public static final String SIGNALGORITHM = "SHA256withECDSA";    //签名算法
    public static final int KEYSIZE = 256; //大小

    //私钥文件
    public static final String fileName = "/private_key.json";
    public static final String privKey = "priv_key";

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


    // 公私钥生成
    public KeyPair initKey() throws Exception {
        return initKey(KEYSIZE, new SecureRandom().generateSeed(8));
    }

    public KeyPair initKey(int keySize, byte[] seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(AnboECCSign.EC);
        // 初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed);
        keygen.initialize(keySize, secureRandom);
        KeyPair keys = keygen.genKeyPair();
        return keys;
    }

    //获取公钥(Base64编码)
    public String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return new String(Base64.getEncoder().encode(bytes));
    }

    //获取私钥(Base64编码)
    public String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return new String(Base64.getEncoder().encode(bytes));
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = Base64.getDecoder().decode(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(AnboECCSign.EC);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = Base64.getDecoder().decode(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(AnboECCSign.EC);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    // 源数据的map,排序，拼接字符串：k1=v1k2=v2k3=v3,返回byte数组
    private static byte[] dataMap2byte(Map<String, String> data){
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(data);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        return basestring.toString().getBytes();
    }

    //执行签名
    public String sign(Map<String,String> data) throws Exception {
        PrivateKey privateKey = string2PrivateKey(privateKeyStr);
        byte[] dataBytes = dataMap2byte(data);
        // 2.执行签名[私钥签名]
        Signature signature = Signature.getInstance(AnboECCSign.SIGNALGORITHM);
        signature.initSign(privateKey);
        signature.update(dataBytes);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    // 验证签名
    public static boolean verify(String publKeyStr, Map<String,String> data, String sign) throws Exception {
        PublicKey publicKey = string2PublicKey(publKeyStr);
        byte[] signBytes = Base64.getDecoder().decode(sign);
        byte[] dataBytes = dataMap2byte(data);
        return verify(publicKey,dataBytes,signBytes);
    }
    // 验证签名
    private static boolean verify(PublicKey publicKey, byte[] data, byte[] sign) throws Exception {
        // 3.验证签名[公钥验签]
        Signature signature = Signature.getInstance(AnboECCSign.SIGNALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

}
