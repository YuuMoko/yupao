package com.yuki.usercenter.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.yuki.usercenter.mapper.UserMapper;
import com.yuki.usercenter.model.domain.Chat;
import com.yuki.usercenter.model.domain.Message;
import com.yuki.usercenter.model.domain.User;
import com.yuki.usercenter.service.ChatService;
import com.yuki.usercenter.service.MessageService;
import com.yuki.usercenter.service.UserService;
import com.yuki.usercenter.service.impl.UserServiceImpl;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import javax.annotation.Resource;
import javax.websocket.*;
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

    public static UserService userService;
    public static MessageService messageService;
    public static ChatService chatService;

    @Autowired
    public void setUserService(UserService userService) {UserWebSocketServer.userService = userService;}
    @Resource
    public void setChatService(ChatService chatService) { UserWebSocketServer.chatService = chatService;}
    @Resource
    public void setMessageService(MessageService messageService) { UserWebSocketServer.messageService = messageService;}


    // @todo 先用id传吧，不考虑安全问题了，等做出来了再改成安全的
    // 建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("idA") String idA, @PathParam("idB") String idB) {
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
        if (this.loginUser != null) {
            users.remove(loginUser.getId());
        }
    }


    public Chat getChat(String idA, String idB) { // 获取到当前两个用户对应的聊天室
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

    @OnMessage
    public void onMessage(String message, Session session) throws JSONException { // 拿到当前的登录用户，和对应的chat表，把消息发送给所有建立连接的用户
        Chat chat = this.chat;
        Gson gson = new Gson();
        message = gson.fromJson(message, String.class);
        // 把发送的消息存储到数据库
        Message msg = new Message();
        msg.setContent(message);
        msg.setUserId(loginUser.getId());
        msg.setChatId(chat.getId());
        messageService.save(msg);

        JSONObject resp = new JSONObject();
        resp.put("message", msg.getContent());
        resp.put("userId", loginUser.getId());
        resp.put("msgId", msg.getId());
        if (users.get(chat.getUserAId()) != null) {
            users.get(chat.getUserAId()).sendMessage(resp.toString());
        }
        if (users.get(chat.getUserBId()) != null) {
            users.get(chat.getUserBId()).sendMessage(resp.toString());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
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
