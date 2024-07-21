package com.fastcampus.nextcoursechatservice.domain;

import com.fastcampus.nextchatservice.domain.chat.Chat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private StringRedisTemplate stringRedisTemplate;
    @Mock
    private HashOperations<String, Object, Object> hashOperations;
    @Mock
    private RedisMessageListenerContainer redisContainer;

    private ManagedChannel channel;

    @BeforeEach
    public void setup() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
    }

    @AfterEach
    public void teardown() {
        channel.shutdown();
    }

    @Test
    public void testSendMessage() {
        Chat.ChatMessage message = Chat.ChatMessage.newBuilder()
                .setCourseId("course1")
                .setMessageId("msg1")
                .setUserId("user1")
                .setContent("Hello")
                .setTimestamp(System.currentTimeMillis())
                .build();

        Chat.SendMessageRequest request = Chat.SendMessageRequest.newBuilder()
                .setMessage(message)
                .build();

        StreamObserver<Chat.SendMessageResponse> responseObserver = mock(StreamObserver.class);

        chatService.sendMessage(request, responseObserver);

        ArgumentCaptor<Chat.SendMessageResponse> responseCaptor = ArgumentCaptor.forClass(Chat.SendMessageResponse.class);
        verify(responseObserver).onNext(responseCaptor.capture());
        verify(responseObserver).onCompleted();

        Chat.SendMessageResponse response = responseCaptor.getValue();
        assertTrue(response.getSuccess());
        assertEquals(response.getError(), "");
    }

    @Test
    public void testSubscribeMessages() {
        Chat.SubscribeMessagesRequest request = Chat.SubscribeMessagesRequest.newBuilder()
                .setCourseId("course1")
                .build();

        StreamObserver<Chat.ChatMessage> responseObserver = mock(StreamObserver.class);

        chatService.subscribeMessages(request, responseObserver);

        String channelName = "course:course1";
        ArgumentCaptor<MessageListenerAdapter> listenerCaptor = ArgumentCaptor.forClass(MessageListenerAdapter.class);
        verify(redisContainer).addMessageListener(listenerCaptor.capture(), eq(new ChannelTopic(channelName)));

        MessageListenerAdapter listener = listenerCaptor.getValue();
        assertNotNull(listener);

        String testMessage = "user1::Hello";
        Message redisMessage = new Message() {
            @Override
            public byte[] getBody() {
                return testMessage.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public byte[] getChannel() {
                return new byte[0];
            }
        };

        listener.onMessage(redisMessage, null);


        ArgumentCaptor<Chat.ChatMessage> messageCaptor = ArgumentCaptor.forClass(Chat.ChatMessage.class);
        verify(responseObserver).onNext(messageCaptor.capture());

        Chat.ChatMessage chatMessage = messageCaptor.getValue();
        assertEquals("user1", chatMessage.getUserId());
        assertEquals("Hello", chatMessage.getContent());
        assertEquals("course1", chatMessage.getCourseId());
    }
}