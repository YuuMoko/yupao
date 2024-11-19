package com.yuki.usercenter.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.yuki.usercenter.model.domain.*;
import com.yuki.usercenter.service.ChatService;
import com.yuki.usercenter.service.MessageService;
import com.yuki.usercenter.service.UserService;
import com.yuki.usercenter.service.UserTeamService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@ServerEndpoint("/websocket/team/chat/{userId}/{teamId}")
public class TeamWebSocketServer {

    final public static ConcurrentHashMap<Long, TeamWebSocketServer> users = new ConcurrentHashMap<>(); // 线程安全哈希表
    private Session session = null;
    private User loginUser = null;
    private Chat chat = null;
    private Long teamId;

    public static UserService userService;
    public static MessageService messageService;
    public static ChatService chatService;
    public static UserTeamService userTeamService;

    @Resource
    public void setUserService(UserService userService) {TeamWebSocketServer.userService = userService;}
    @Resource
    public void setChatService(ChatService chatService) { TeamWebSocketServer.chatService = chatService;}
    @Resource
    public void setMessageService(MessageService messageService) { TeamWebSocketServer.messageService = messageService;}
    @Resource
    public void setUserTeamService(UserTeamService userTeamService) { TeamWebSocketServer.userTeamService = userTeamService;}


    @OnOpen
    public void onOpen(Session session, @PathParam("teamId") long teamId, @PathParam("userId") long userId) {
        this.session = session;
        this.teamId = teamId;
        this.loginUser = userService.getById(userId);
        this.chat = getChat(teamId);
        users.put(loginUser.getId(), this); // 将当前用户放到用户池里
    }

    public Chat getChat(long teamId) { // 获取到当前队伍对应的聊天室，没有就创建一个
        QueryWrapper<Chat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teamId", teamId);
        List<Chat> list = chatService.list(queryWrapper);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        Chat chat = new Chat();
        chat.setTeamId(teamId);
        chatService.save(chat);
        list = chatService.list(queryWrapper);
        return list.get(0);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws JSONException {
        // 获取当前聊天室  this.chat 已获得

        // 将当前message从JSON转换成Java字符串
        Gson gson = new Gson();
        message = gson.fromJson(message, String.class);
        Message msg = new Message();
        msg.setContent(message);
        msg.setUserId(loginUser.getId());
        msg.setChatId(this.chat.getId());
        // 将消息存储到消息表
        messageService.save(msg);

        // 将消息存储成JSON，以便进行传输
        JSONObject resp = new JSONObject();
        resp.put("message", msg.getContent());
        resp.put("userId", loginUser.getId());
        resp.put("msgId", msg.getId());

        // 获取到在当前队伍的所以用户的Id表
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teamId", this.teamId);
        List<Long> userIdList = userTeamService.list(queryWrapper).stream().map(UserTeam::getUserId).collect(Collectors.toList());

        // 将消息发送给所有在当前队伍，并且在当前聊天室的用户
        for (Long userId : userIdList) {
            if (users.get(userId) != null) {
                users.get(userId).sendMessage(resp.toString());
            }
        }
    }
    @OnClose
    public void onClose(Session session) {
        if (this.loginUser != null) {
            users.remove(loginUser.getId());
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
