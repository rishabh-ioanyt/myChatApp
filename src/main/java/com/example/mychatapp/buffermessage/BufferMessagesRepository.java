package com.example.mychatapp.buffermessage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BufferMessagesRepository extends JpaRepository<BufferMessages, Long> {
}