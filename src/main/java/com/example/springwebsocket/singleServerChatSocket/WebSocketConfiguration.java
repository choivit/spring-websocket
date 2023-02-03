package com.example.springwebsocket.singleServerChatSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // 웹소켓 서버를 사용하도록 정의하는 것.
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(signalingSocketHandler(), "/room") // 웹소켓 서버의 엔드포인트
                .setAllowedOrigins("*"); // 클라이언트에서 웹소켓 요청 시 모든 요청을 수용한다는 뜻(CORS)
                                         // 실제 서비스 서버에서는 환경에 맞게 변경해야 함.
    }

    @Bean
    public WebSocketHandler signalingSocketHandler() {
        System.out.println("나만의 웹소켓 핸들러 생성 >>> ");
        return new WebSocketHandler(); // 만든 클래스로 웹소켓 핸들러로 정의
    }
}
