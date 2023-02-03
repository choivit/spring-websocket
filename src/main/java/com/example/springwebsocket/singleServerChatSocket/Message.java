package com.example.springwebsocket.singleServerChatSocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// {"type":"", "sender":"me", "receiver":"타겟 세션아이디", "data":"test..."}

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message { // 클라이언트 소켓 통신에서 사용하는 메시지 스펙
    private String type;
    private String sender;
    private String receiver;
    private String data;

    public void setSender(String sender) {this.sender = sender; }

    public void newConnect() {
        this.type = "new";
    }

    public void closeConnect() {
        this.type = "close";
    }
}
