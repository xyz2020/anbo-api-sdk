package com.zzrb.util;

import org.bouncycastle.jcajce.provider.asymmetric.RSA;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSACryptUtil {

    public static String encrypt(String plainText, String publicKeyStr) throws Exception {
        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
        return encrypt(plainText,publicKey);
    }

    public static String decrypt(String cipherText, String privateKeyStr) throws Exception{
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
        return decrypt(cipherText, privateKey);
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(RSAUtil.UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), RSAUtil.UTF_8);
    }
}
