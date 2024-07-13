package com.fastcampus.nextwebsocketspringgraphqlsample.repository;

import com.fastcampus.nextwebsocketspringgraphqlsample.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
