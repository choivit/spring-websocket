package com.example.springwebsocket.wsWithStompAddRabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController { // 메세지 발행을 위한 클래스

    private final SimpMessageSendingOperations messageSendingOperations;

    /*
        /pub/hello              메시지 발행
        /topic/channelId        구독
     */

    @MessageMapping("/hello")
    public void newUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("username", message.getSender());
        messageSendingOperations.convertAndSend("/topic/" + message.getChannelId(), message);
    }
}
