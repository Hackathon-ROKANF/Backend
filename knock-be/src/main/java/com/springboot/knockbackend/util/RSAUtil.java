package com.springboot.knockbackend.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAUtil {

    private static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    public String encryptPassword(String password, String publicKeyPem) {
        try {
            // PEM 형식의 공개키를 PublicKey 객체로 변환
            String publicKeyContent = publicKeyPem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(publicKeyContent);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(spec);

            // RSA 암호화
            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes("UTF-8"));

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("RSA 암호화 실패: " + e.getMessage(), e);
        }
    }
}
