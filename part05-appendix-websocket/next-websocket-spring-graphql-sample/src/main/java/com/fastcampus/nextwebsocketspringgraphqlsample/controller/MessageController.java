package com.fastcampus.nextwebsocketspringgraphqlsample.controller;

import com.fastcampus.nextwebsocketspringgraphqlsample.entity.Message;
import com.fastcampus.nextwebsocketspringgraphqlsample.entity.MessageEvent;
import com.fastcampus.nextwebsocketspringgraphqlsample.repository.MessageRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MessageController {

    private final MessageRepository messageRepository;

    private final ApplicationEventPublisher publisher;

    public MessageController(MessageRepository messageRepository, ApplicationEventPublisher publisher) {
        this.messageRepository = messageRepository;
        this.publisher = publisher;
    }

    @QueryMapping
    public List<Message> messages() {
        return messageRepository.findAll();
    }

    @MutationMapping
    public Message postMessage(@Argument String content, @Argument String sender) {
        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        messageRepository.save(message);
        publisher.publishEvent(new MessageEvent(this, message));
        return message;
    }
}



