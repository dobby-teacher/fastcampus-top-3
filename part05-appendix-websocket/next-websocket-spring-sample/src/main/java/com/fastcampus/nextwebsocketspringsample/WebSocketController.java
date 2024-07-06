package com.fastcampus.nextwebsocketspringsample;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/send") // /app/send로 메시지가 오면 처리
    @SendTo("/topic/messages") // 처리된 메시지를 /topic/messages로 브로드캐스트
    public String processMessageFromClient(String message) throws Exception {
        return "echo : " + message;
    }
}
