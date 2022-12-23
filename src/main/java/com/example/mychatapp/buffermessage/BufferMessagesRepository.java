package com.example.mychatapp.buffermessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository @Transactional
public interface BufferMessagesRepository extends JpaRepository<BufferMessages, Long> {

    long countAllByReceivedByUsername(String username);

    List<BufferMessages> findAllByReceivedByUsername(String username);

    void deleteAllByReceivedByUsername(String username);

    void deleteByReceivedByUsername(String username);
}