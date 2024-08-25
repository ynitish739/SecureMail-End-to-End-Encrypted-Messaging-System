package com.example.encryptedMessagingSystem.Service;

import com.example.encryptedMessagingSystem.Repository.DataRepository;
import com.example.encryptedMessagingSystem.entity.UserData;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@org.springframework.stereotype.Service
public class Serviceimpl {
    @Autowired
    private EmailService emailService;
    @Autowired
    private DataRepository dataRepository;
    @Autowired
    private KeyGenration keyGenration;


    public void createUser(String email, String name) {
        try {
            KeyPair keyPair = keyGenration.generateKeyPair();

            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            UserData data = new UserData();
            data.setEmail(email);
            data.setName(name);
            data.setPublicKey(publicKey);
            dataRepository.save(data);
            System.out.println("Data saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<UserData> showDataAll() {
        List<UserData> user=dataRepository.findAll();
        return user;
    }

    public String writeEncryptMessage(String tempid,String messageToencrypt) {
        UserData user = dataRepository.findById(Long.parseLong(tempid)).orElse(null);

        if (user != null) {
            try {

                String publicKey = user.getPublicKey();

                String encryptedMessage = keyGenration.encryptMessage(messageToencrypt, publicKey);


                emailService.sendEmail(user.getEmail(), "Encrypted Message", encryptedMessage);

                return "Email sent successfully!";
            } catch (Exception e) {
                throw new RuntimeException("Failed to process encryption: " + e.getMessage(), e);
            }
        } else {
            return "User not found.";
        }

    }
}
