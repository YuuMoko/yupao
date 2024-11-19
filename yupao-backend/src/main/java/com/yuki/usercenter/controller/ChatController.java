package com.yuki.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuki.usercenter.common.BaseResponse;
import com.yuki.usercenter.common.ErrorCode;
import com.yuki.usercenter.common.ResultUtils;
import com.yuki.usercenter.expection.BusinessException;
import com.yuki.usercenter.mapper.TeamMapper;
import com.yuki.usercenter.mapper.UserTeamMapper;
import com.yuki.usercenter.model.domain.*;
import com.yuki.usercenter.model.dto.ChatMessageQuery;
import com.yuki.usercenter.model.vo.MessageVO;
import com.yuki.usercenter.service.ChatService;
import com.yuki.usercenter.service.MessageService;
import com.yuki.usercenter.service.UserService;
import com.yuki.usercenter.service.UserTeamService;
import org.apache.commons.math3.ml.neuralnet.twod.util.QuantizationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private UserService userService;

    @Resource
    private ChatService chatService;

    @Resource
    private MessageService messageService;

    @Resource
    private UserTeamService userTeamService;

    @GetMapping("/get/user/byid")
    public BaseResponse<User> getUserById(@RequestParam("id") int id) {
        User user = userService.getById(id);
        user = userService.getSafetyUser(user);
        return ResultUtils.success(user);
    }

    @GetMapping("/get/message")
    public BaseResponse<List<MessageVO>> getMessage(ChatMessageQuery chatMessageQuery) {
        if (chatMessageQuery.getTeamId() == null) {
            Long idA = chatMessageQuery.getIdA();
            Long idB = chatMessageQuery.getIdB();
            if (idA == null || idB == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "idA 或者 idB 为空");
            }
            Long minMessageId = chatMessageQuery.getMinMessageId();

            if (idA <= 0 || idB <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            if (idA > idB) {
                long temp = idA;
                idA = idB;
                idB = temp;
            }
            QueryWrapper<Chat> chatQueryWrapper = new QueryWrapper<>();
            chatQueryWrapper.eq("userAId", idA);
            chatQueryWrapper.eq("userBId", idB);
            Chat chat = chatService.list(chatQueryWrapper).get(0);
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id"); // 根据message的id从大到小排序
            queryWrapper.eq("chatId", chat.getId());
            if (minMessageId != null) {
                queryWrapper.lt("id", minMessageId);
            }
            return getListBaseResponse(queryWrapper);
        }
        else {
            Long teamId = chatMessageQuery.getTeamId();
            QueryWrapper<Chat> chatQueryWrapper = new QueryWrapper<>();
            chatQueryWrapper.eq("teamId", teamId);
            Chat chat = chatService.list(chatQueryWrapper).get(0);
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id"); // 降序，越新的在越前面
            queryWrapper.eq("chatId", chat.getId());
            return getListBaseResponse(queryWrapper);
        }

    }

    /**
     * 通过teamId获取到当前队伍所有用户的头像 Map<Long, String>
     * @param teamId
     * @return
     */
    @GetMapping("/get/team/avatar")
    public BaseResponse<Map<Long, String>> getUserAvatar(@RequestParam("teamId") long teamId) {
        // 获取当前队伍对应的所有userTeamList
        Map<Long, String> avatarMap = new HashMap<>();
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teamId", teamId);
        List<UserTeam> userTeamList = userTeamService.list(queryWrapper);

        List<Long> userIdList = userTeamList.stream().map(UserTeam::getUserId).collect(Collectors.toList());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id", userIdList);
        List<User> userList = userService.list(userQueryWrapper);
        for (User user : userList) {
            avatarMap.put(user.getId(), user.getAvatarUrl());
        }
        return ResultUtils.success(avatarMap);
    }

    private BaseResponse<List<MessageVO>> getListBaseResponse(QueryWrapper<Message> queryWrapper) {
        List<Message> result = messageService.list(queryWrapper);
        List<MessageVO> messageVoList = new ArrayList<>();
        for (Message message : result) {
            MessageVO messageVO = new MessageVO();
            messageVO.setMessageId(message.getId());
            messageVO.setUserId(message.getUserId());
            messageVO.setMessage(message.getContent());
            messageVoList.add(messageVO);
        }
        return ResultUtils.success(messageVoList);
    }
}
