package com.fastcampus.nextwebsocketspringgraphqlsample.controller;

import com.fastcampus.nextwebsocketspringgraphqlsample.entity.Message;
import com.fastcampus.nextwebsocketspringgraphqlsample.entity.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;

@Controller
public class SubscriptionController {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
    private final Sinks.Many<Message> sink;

    public SubscriptionController() {
        // 여기서 backpressure 버퍼 설정을 통해 스트림을 제어합니다.
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @EventListener
    public void onMessageEvent(MessageEvent event) {
        Sinks.EmitResult result = sink.tryEmitNext(event.getMessage());
        if (result.isFailure()) {
            // 실패 시 로깅을 수행하고 필요한 경우 복구 메커니즘을 실행할 수 있습니다.
            logger.error("Failed to emit message: {}", result);
        }
    }

    @SubscriptionMapping
    public Flux<Message> messagePosted() {
        return Flux.create(emitter -> {
            Consumer<Message> messageConsumer = message -> {
                if (!emitter.isCancelled()) {
                    emitter.next(message);
                }
            };

            this.sink.asFlux().subscribe(messageConsumer, emitter::error, emitter::complete);
        }, FluxSink.OverflowStrategy.BUFFER);
    }

}


