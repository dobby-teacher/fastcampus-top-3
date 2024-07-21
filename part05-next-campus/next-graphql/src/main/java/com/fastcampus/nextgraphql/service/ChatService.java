package com.fastcampus.nextgraphql.service;


import com.fastcampus.nextchatservice.domain.chat.Chat;
import com.fastcampus.nextchatservice.domain.chat.ChatServiceGrpc;
import com.fastcampus.nextgraphql.model.ChatMessage;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @GrpcClient("next-chat-service")
    private ChatServiceGrpc.ChatServiceBlockingStub blockingStub;
    @GrpcClient("next-chat-service")
    private ChatServiceGrpc.ChatServiceStub asyncStub;

    public void sendMessage(String courseId, String userId, String content) {
        String messageId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        Chat.ChatMessage grpcMessage = Chat.ChatMessage.newBuilder()
                .setCourseId(courseId)
                .setUserId(userId)
                .setMessageId(messageId)
                .setContent(content)
                .setTimestamp(timestamp)
                .build();

        Chat.SendMessageRequest request = Chat.SendMessageRequest.newBuilder()
                .setMessage(grpcMessage)
                .build();

        Chat.SendMessageResponse response = blockingStub.sendMessage(request);
        if (!response.getSuccess()) {
            throw new RuntimeException("Failed to send message: " + response.getError());
        }
    }

    public Publisher<ChatMessage> messageReceived(String courseId) {
        return Flux.create(sink -> {
            Chat.SubscribeMessagesRequest request = Chat.SubscribeMessagesRequest.newBuilder()
                    .setCourseId(courseId)
                    .build();

            asyncStub.subscribeMessages(request, new StreamObserver<Chat.ChatMessage>() {
                @Override
                public void onNext(Chat.ChatMessage chatMessage) {
                    ChatMessage message = ChatMessage.fromProto(chatMessage);
                    sink.next(message);
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    sink.complete();
                }
            });
        }, FluxSink.OverflowStrategy.BUFFER);
    }

    public List<ChatMessage> getMessages(String courseId) {
        Chat.GetMessagesRequest request = Chat.GetMessagesRequest.newBuilder()
                .setCourseId(courseId)
                .build();

        return blockingStub.getMessages(request).getMessagesList().stream()
                .map(ChatMessage::fromProto)
                .collect(Collectors.toList());
    }
}
