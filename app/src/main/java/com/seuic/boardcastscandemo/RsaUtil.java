package com.seuic.boardcastscandemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: 嘉阳
 * @Date: 2021/4/7
 * @Describe: 加解密 工具类
 */
public class RsaUtil {

    public static void main(String[] args) throws Exception {
        // 公钥加密
        String test = "qwe123";
        String s = RsaUtil.encryptByPublicKey(publicKey, test);
        System.out.println(s);
        // 私钥解密
        System.out.println(RsaUtil.decryptByPrivateKey(s));
    }


    /**
     * 私钥解密
     *
     * @param text 待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String text) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(spec);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 私钥加密
     *
     * @param text 待加密的文本
     * @return
     */
    public static String encryptByPrivateKey(String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 公钥解密
     *
     * @param publicKeyString 公钥
     * @param text            待解密的文本
     * @return
     */
    public static String decryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 公钥加密
     *
     * @param publicKeyString 公钥
     * @param text            待加密的文本
     * @return
     */
    public static String encryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    @Data
    @AllArgsConstructor
    static class RsaKeyPair {

        private String publicKey;

        private String privateKey;
    }

    public static final String RSA = "RSA";

    public static final String privateKey = "MIICdAIBADANBgkqhkiG9w0BAQEFAASCAl4wggJaAgEAAoGBAIp8lRo7sjhUBOhqWB2IkBus+6b+4zj" +
            "/AiGoF8dgpZ0DaY5le+GMLawApOQ31W2mZThiYRXMSJtV3gIoyBEL4/RWwm0x0ADbS4ek83v758NSKm/" +
            "CCw0k/bv+et9A6+ocFddIVJCScfk35FDeEiZs5G8WA/Eb6xGRFn6adFC+PyI/AgMBAAECfwW+7iuXK+KSMhCi/" +
            "vcYIiDMe0OcA5obhN9x6YqoR3USw+2MVjadrxATBJriJN0im40OXmCd6MYoRm6t6Ipd0VWjI0BIaAZUqjbqk078SX" +
            "8rwANIy9HU5OuOTdja15tgYo+k42ueizj2NbfFR6MdDAgxTeh+VH42i8NCskzCauECQQDK4wDv2eG+fpY1QLWPB6l" +
            "HYYyVxZzfFul5+milsEEMbgmv62A1tqZh2ZaoXW23kQP3EV8COJNVXHcStD+7wTgPAkEArr2inEs1FUyBERjWOBZFEd02X8a" +
            "DqpltH/4+AyDllLwygyfML/75IkIAYWfMisfFGhkuGSqtEmaU/QIH+avC0QJBALsmp2iZDdoRHzJEXLZxlL9ZxrsprMlFc12n" +
            "lfyxF3AF2T3D293wfI5qjsUEWf8o8AVqqr97vLzTLKu+RcRGOhUCQEaSR+4fYZzsfOPOyBV2yQZh9OfRYLOxqyTUpW7WBmJ/b1" +
            "cQ56Iqs6tmRIkSRsV10OCtcrSvS562100nWNk4yUECQDxNmKunlwcDjPm2WSxKrzVnEAH8foqBIhTQHK7XBac/B5pzOwQ5MiiB96" +
            "Yx/CvI6ZytdSgBlewyMxK9rsE16zY=";
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKfJUaO7I4VAToalgdiJAbrPum/uM4/wIhqBfHYKWdA2mOZXvhjC2sAKTkN9VtpmU4YmEVzEibVd4CKMgRC+P0VsJtMdAA20uHpPN7++fDUipvwgsNJP27/nrfQOvqHBXXSFSQknH5N+RQ3hImbORvFgPxG+sRkRZ+mnRQvj8iPwIDAQAB";

}
