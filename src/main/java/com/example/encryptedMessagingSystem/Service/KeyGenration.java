package com.example.encryptedMessagingSystem.Service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class KeyGenration {
     private KeyPair currentKeyPair;




    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            currentKeyPair = keyPairGenerator.generateKeyPair();
            return currentKeyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating key pair", e);
        }
    }

    public KeyPair getCurrentKeyPair() {
        return currentKeyPair;
    }

    public String encryptMessage(String message, String publicKey) throws Exception {

        byte[] decodedKey = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptMessage(String encryptedMessage, String privateKey) throws Exception {

        byte[] decodedKey = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);


        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));


        return new String(decryptedBytes);
    }
}
