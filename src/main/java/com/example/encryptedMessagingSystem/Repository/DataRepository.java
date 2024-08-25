package com.example.encryptedMessagingSystem.Repository;

import com.example.encryptedMessagingSystem.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<UserData,Long> {

}
