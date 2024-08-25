package com.example.encryptedMessagingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.Encoder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;


@lombok.Data
@NoArgsConstructor
@Entity
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uniqueId;

    @NotBlank(message= "Please Enter Value")
    private String email;
    private String name;

    @Column(name = "publicKey", length = 2048)
    private String publicKey;


}
