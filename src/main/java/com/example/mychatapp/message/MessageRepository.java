package com.example.mychatapp.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {

    List<Messages> findAllByReceivedBy_UsernameAndSendBy_Username(String receiver , String sender);
}
