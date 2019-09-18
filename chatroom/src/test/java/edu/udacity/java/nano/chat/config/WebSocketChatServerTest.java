package edu.udacity.java.nano.chat.config;

import com.alibaba.fastjson.JSON;
import edu.udacity.java.nano.chat.controller.WebSocketChatServer;
import edu.udacity.java.nano.chat.model.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class WebSocketChatServerTest {
    @InjectMocks
    @Spy
    WebSocketChatServer webSocketChatServer = new WebSocketChatServer();

    @Mock
    Session session;

    @Mock
    Session secondSession;

    @Mock
    RemoteEndpoint.Basic endpoint;

    @Captor
    ArgumentCaptor<String> msg;

    private List<Session> sessionList = new ArrayList<>();
    private static final String SESSION_1 = "session1";
    private static final String SESSION_2 = "session2";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        openOneSession(session, SESSION_1);
    }

    @After
    public void tearDown() {
        sessionList.forEach(session -> webSocketChatServer.onClose(session));
    }

    @Test
    public void testUserEnter() throws IOException {
        webSocketChatServer.onOpen(session);
        verify(session.getBasicRemote()).sendText(msg.capture());
        assertEquals(Message.Type.ENTER.name(), JSON.parseObject(msg.getValue()).getString("type"));
        assertEquals(1, JSON.parseObject(msg.getValue()).getIntValue("onlineCount"));
    }

    @Test
    public void testUserSpeak() throws IOException {
        webSocketChatServer.onOpen(session);
        webSocketChatServer.onMessage(session, JSON.toJSONString(getMessage()));
        verify(session.getBasicRemote(), times(2)).sendText(msg.capture());
        assertEquals(Message.Type.SPEAK.name(), JSON.parseObject(msg.getValue()).getString("type"));
        assertEquals(getMessage().getMsg(), JSON.parseObject(msg.getValue()).getString("msg"));
        assertEquals(getMessage().getUsername(), JSON.parseObject(msg.getValue()).getString("username"));
        assertEquals(1, JSON.parseObject(msg.getValue()).getIntValue("onlineCount"));
    }

    @Test
    public void testUserLeave() throws IOException {
        openOneSession(secondSession, SESSION_2);
        webSocketChatServer.onOpen(session);
        webSocketChatServer.onOpen(secondSession);
        webSocketChatServer.onClose(secondSession);
        verify(session.getBasicRemote(), atLeast(3)).sendText(msg.capture());
        assertEquals(Message.Type.LEAVE.name(), JSON.parseObject(msg.getValue()).getString("type"));
        assertEquals(1, JSON.parseObject(msg.getValue()).getIntValue("onlineCount"));
    }

    private Message getMessage() {
        return new Message(Message.Type.SPEAK, "Tom", "hello", 1);
    }

    private void openOneSession(Session session, String id) {
        when(session.getId()).thenReturn(id);
        when(session.getBasicRemote()).thenReturn(endpoint);
        sessionList.add(session);
    }
}
