package com.example.springwebsocket.chatSocketWithStomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * /sub/channel/12345      - 구독(channelId:12345)
     * /pub/hello              - 메시지 발행
     */

    @MessageMapping("/hello") // 클라이언트에서, /pub/hello로 메시지를 발행.
    public void message(Message message) {
        // 메세지에 정의된 채널아이디에 메세지 발송.
        // /sub/channel/채널아이디 에 구독중인 클라이언트에게 메세지를 보냄.
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message);
    }

}
