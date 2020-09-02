package com.zzrb.util;

import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ECCCryptUtil {

    public static String encrypt(String data, String publicKeyStr) throws Exception {
        byte[] bytes = publicEncrypt(ECCUtil.string2Bytes(data),publicKeyStr);
        String eStr = Base64.getEncoder().encodeToString(bytes);
        return ECCUtil.strConvertBase(eStr);
    }

    public static String decrypt(String data, String privateKeyStr) throws Exception {
        String dstr = ECCUtil.baseConvertStr(data);
        byte[] bytes = Base64.getDecoder().decode(ECCUtil.string2Bytes(dstr));
        return new String(privateDecrypt(bytes,privateKeyStr));
    }


    //公钥加密
    public static byte[] publicEncrypt(byte[] data, String publicKeyStr) throws Exception{
        ECPublicKey publicKey = ECCUtil.string2PublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(ECCUtil.ECIES, ECCUtil.BC);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] data, String privateKeyStr) throws Exception{
        ECPrivateKey privateKey = ECCUtil.string2PrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(ECCUtil.ECIES,ECCUtil.BC);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
}
