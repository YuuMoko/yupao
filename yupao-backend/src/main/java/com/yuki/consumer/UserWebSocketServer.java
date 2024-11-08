package com.yuki.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.usercenter.model.domain.Chat;
import com.yuki.usercenter.model.domain.User;
import com.yuki.usercenter.service.ChatService;
import com.yuki.usercenter.service.MessageService;
import com.yuki.usercenter.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/user/chat/{idA}/{idB}") // idA 为建立起连接的用户
public class UserWebSocketServer {

    final public static ConcurrentHashMap<Long, UserWebSocketServer> users = new ConcurrentHashMap<>(); // 线程安全哈希表

    private Session session = null;
    private User loginUser = null;
    private Chat chat = null;
    @Resource
    private ChatService chatService;
    @Resource
    private MessageService messageService;
    @Resource
    private UserService userService;
    // @todo 先用id传吧，不考虑安全问题了，等做出来了再改成安全的


    public Chat getChat(String idA, String idB) {
        QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAId", idA);
        queryWrapper.eq("userBId", idB);
        List<Chat> chatList = chatService.list(queryWrapper);
        if (!chatList.isEmpty()) {
            return chatList.get(0);
        }
        Chat chat = new Chat();
        chat.setUserAId(Long.parseLong(idA));
        chat.setUserBId(Long.parseLong(idB));
        chatService.save(chat);
        chatList = chatService.list(queryWrapper);
        return chatList.get(0);
    }

    // 建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("idA") String idA, @PathParam("idB") String idB) {
        System.out.println("connected!");
        this.session = session;
        this.loginUser = userService.getById(idA);
        users.put(loginUser.getId(), this);
        if (Long.parseLong(idA) > Long.parseLong(idB)) {
            String tmp = idA;
            idA = idB;
            idB = tmp;
        }
        this.chat = getChat(idA, idB); // 获取当前两个用户的聊天室
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnected!");
        if (this.loginUser != null) {
            users.remove(loginUser.getId());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) { // 拿到当前的登录用户，和对应的chat表，把消息发送给所以建立连接的用户
        System.out.println("received message: " + message);
        Chat chat = this.chat;
        if (users.get(chat.getUserAId()) != null) {
            users.get(chat.getUserAId()).sendMessage(message);
        }
        if (users.get(chat.getUserBId()) != null) {
            users.get(chat.getUserBId()).sendMessage(message);
        }
    }


    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
