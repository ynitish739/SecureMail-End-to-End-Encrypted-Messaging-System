package com.example.encryptedMessagingSystem.Controller;

import com.example.encryptedMessagingSystem.Repository.DataRepository;
import com.example.encryptedMessagingSystem.Service.KeyGenration;
import com.example.encryptedMessagingSystem.Service.Serviceimpl;
import com.example.encryptedMessagingSystem.entity.UserData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Base64;
import java.util.List;

import static org.springframework.stereotype.Service.*;

@Controller
public class keyPairController {
    @Autowired
    private KeyGenration keyGenerationService;
    @Autowired
    private Serviceimpl service;
    @GetMapping("/")
    public String index() {
        return "index"; // Main page with buttons
    }

    @GetMapping("/generate-key-pair")
    public String generateKeyPairForm() {
        return "generate-key-pair"; // Form for generating key pair
    }

    @PostMapping("/generate-key-pair")
    public String genrateKeyPair(@Valid @RequestParam String email, @RequestParam String name){
        service.createUser(email, name);
        return "redirect:/download-keys";
    }

    @GetMapping("/download-keys")
    public String downloadKeys(Model model) {
        KeyPair keyPair = keyGenerationService.getCurrentKeyPair();
        if (keyPair != null) {
            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            model.addAttribute("publicKey", publicKey);
            model.addAttribute("privateKey", privateKey);
        }
        return "download-keys";
    }

    @GetMapping("/download-public-key")
    public ResponseEntity<Resource> downloadPublicKey() {
        KeyPair keyPair = keyGenerationService.getCurrentKeyPair();
        if (keyPair != null) {
            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            InputStreamResource file = new InputStreamResource(new ByteArrayInputStream(publicKey.getBytes()));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=public_key.txt")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(file);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/download-private-key")
    public ResponseEntity<Resource> downloadPrivateKey() {
        KeyPair keyPair = keyGenerationService.getCurrentKeyPair();
        if (keyPair != null) {
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            InputStreamResource file = new InputStreamResource(new ByteArrayInputStream(privateKey.getBytes()));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=private_key.txt")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(file);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/show-public-key")
    public String showData(Model model){
        List<UserData> users = service.showDataAll();
        model.addAttribute("users", users);
        return "show-public-key";
    }

    @PostMapping("/encrypt-message")
    public String encryptMessage(@RequestParam("uniqueId") String uniqueId,@RequestParam("message") String message,Model model){

//        service.writeEncryptMessage(uniqueId,message);
        try {
            String resultMessage = service.writeEncryptMessage(uniqueId, message);
            model.addAttribute("message", resultMessage);
        } catch (Exception e) {
            model.addAttribute("message", "Failed to send email: " + e.getMessage());
        }


        return "redirect:/?message=" + URLEncoder.encode(model.getAttribute("message").toString(), StandardCharsets.UTF_8);

    }

    @GetMapping("/decrypt-message")
    public String actualMessage(){
        return "decrypt-message";
    }

    @PostMapping("/decrypt-message")
    public String decryptMessage(@RequestParam("encryptedMessage") String encryptedMessage,
                                 @RequestParam("privateKey") String privateKey,
                                 Model model) {
        try {
            // Decrypt the message using the private key
            String decryptedMessage = keyGenerationService.decryptMessage(encryptedMessage, privateKey);

            // Add the decrypted message to the model
            model.addAttribute("decryptedMessage", decryptedMessage);
        } catch (Exception e) {
            // Handle decryption errors
            model.addAttribute("decryptedMessage", "Failed to decrypt message: " + e.getMessage());
        }

        // Return a view that shows the decrypted message
        return "show-decrypted-message";
    }
}
