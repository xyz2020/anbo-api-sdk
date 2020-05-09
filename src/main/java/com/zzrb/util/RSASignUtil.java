package com.zzrb.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class RSASignUtil {


    public static String sign(String plainText, String privateKeyStr) throws Exception {
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
        return sign(plainText, privateKey);
    }

    public static boolean verify(String plainText, String signature, String publicKeyStr) throws Exception {
        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
        return verify(plainText, signature, publicKey);
    }

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(RSAUtil.UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(RSAUtil.UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }
}
