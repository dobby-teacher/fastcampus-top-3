package com.fastcampus.nextcoursechatservice.domain;

import chat.Chat;
import chat.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.nio.charset.StandardCharsets;

@GrpcService
@Slf4j
public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {

    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisMessageListenerContainer redisContainer;
    public ChatService(RedisTemplate<String, String> redisTemplate, StringRedisTemplate stringRedisTemplate, RedisMessageListenerContainer redisContainer) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisContainer = redisContainer;
    }

    @Override
    public void sendMessage(Chat.SendMessageRequest request, StreamObserver<Chat.SendMessageResponse> responseObserver) {
        Chat.ChatMessage message = request.getMessage();
        String redisKey = "course:" + message.getCourseId() + ":message:" + message.getMessageId();
        String channel = "course:" + message.getCourseId();

        try {
            redisTemplate.opsForHash().put(redisKey, "user_id", message.getUserId());
            redisTemplate.opsForHash().put(redisKey, "content", message.getContent());
            redisTemplate.opsForHash().put(redisKey, "timestamp", String.valueOf(message.getTimestamp()));

            // Broadcast message
            stringRedisTemplate.convertAndSend(channel, message.getUserId() + "::" + message.getContent());

            Chat.SendMessageResponse response = Chat.SendMessageResponse.newBuilder()
                    .setSuccess(true)
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            Chat.SendMessageResponse response = Chat.SendMessageResponse.newBuilder()
                    .setSuccess(false)
                    .setError("Failed to send message: " + e.getMessage())
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void subscribeMessages(Chat.SubscribeMessagesRequest request, StreamObserver<Chat.ChatMessage> responseObserver) {
        MessageListenerAdapter messageListener = null;

        try {
            String channel = "course:" + request.getCourseId();
            messageListener = new MessageListenerAdapter((MessageListener) (message, pattern) -> {
                // Redis로부터 받은 메시지의 바디를 문자열로 변환
                String messageContent = new String(message.getBody(), StandardCharsets.UTF_8);
                String[] splitMessage = messageContent.split("::");
                Chat.ChatMessage chatMessage = Chat.ChatMessage.newBuilder()
                        .setContent(splitMessage[1])
                        .setUserId(splitMessage[0])
                        .setCourseId(request.getCourseId())
                        .build();
                responseObserver.onNext(chatMessage);
            });

            redisContainer.addMessageListener(messageListener, new ChannelTopic(channel));
        } catch (Exception e) {
            log.error("subscribeMessages error : ", e);
            if (messageListener != null) {
                redisContainer.removeMessageListener(messageListener);
            }
        }

    }

}
