package com.example.springwebsocket.wsWithStompAddRabbitMQ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// {"type":"", "sender":"me", "channelId":"choi", "data":"test..."}

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message { // 클라이언트 소켓 통신에서 사용하는 메시지 스펙
    private String type;
    private String sender;
    private String channelId; // 같은 메세지를 공유한 채널
    private String data;

    public void setSender(String sender) {this.sender = sender; }

    public void newConnect() {
        this.type = "new";
    }

    public void closeConnect() {
        this.type = "close";
    }
}
