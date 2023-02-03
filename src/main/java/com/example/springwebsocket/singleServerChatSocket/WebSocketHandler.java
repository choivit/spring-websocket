package com.example.springwebsocket.singleServerChatSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// STOMP 사용시 따로 Handler를 구현할 필요가 없고
// @MessagingMapping 같은 어노테이션을 사용하여 메세지 발행시 엔드포인트를 별도로 분리해서 관리 가능.
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    // 세션아이디는 Key, 세션은 Value로 저장하는 MAP 자료 구조
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 웹소켓 연결 시
    // 해당 로직을 구현하기 위해서는 기존 접속 사용자의 웹소켓 세션을 전부 관리하고 있어야 함.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println("웹소켓이 최초로 연결되었습니다. >>> sessionInfo: " + session);
        String sessionId = session.getId();
        sessions.put(sessionId, session); // 1) 세션 저장
        System.out.println("세션정보 저장되었습니다. >>> sessionId: " + sessionId + ", session: " + session);

        Message message = Message.builder()
                .sender(sessionId)
                .receiver("all")
                .build();
        System.out.println("메세지 정보 주입되었습니다. >>> messageInfo: " + message);
        message.newConnect();

        sessions.values().forEach(s -> { // 2) 모든 세션에 알림(본인을 제외)
            try {
                if(s.getId().length() == 1 && s.getId()==sessionId) {
                    System.out.println("현재 채팅방에 상대방이 입장하지 않았습니다.");
                }

                if(!s.getId().equals(sessionId)) {
                    s.sendMessage(new TextMessage(Utils.getString(message)));
                    System.out.println(s.getId() + " <<< 사용자에게 알림 완료");
                }
            }
            catch (Exception e) {
                //TODO: throw
            }
        });
    }

    //양방향 데이터 통신
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {

        System.out.println("111");
        Message message = Utils.getObject(textMessage.getPayload());
        System.out.println("222");
        message.setSender(session.getId());
        System.out.println("333");

        WebSocketSession receiver = sessions.get(message.getReceiver()); // 메시지 전달할 타겟 찾기
        System.out.println("444");

        if (receiver != null && receiver.isOpen()) { // 타겟이 있고 연결이 된 상태라면

            receiver.sendMessage(new TextMessage(Utils.getString(message))); // 메세지 전송
        }
    }

    //소켓 연결 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String sessionId = session.getId();

        sessions.remove(sessionId);

        final Message message = new Message();
        message.closeConnect();
        message.setSender(sessionId);

        sessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(Utils.getString(message)));
            } catch (Exception e) {
                //TODO: throw
            }
        });
    }

    //소켓 통신 에러
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //TODO:
    }
}
