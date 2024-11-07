package com.yuki.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuki.usercenter.model.domain.Chat;
import com.yuki.usercenter.service.ChatService;
import com.yuki.usercenter.mapper.ChatMapper;
import org.springframework.stereotype.Service;

/**
* @author 游玄
* @description 针对表【chat(聊天室表)】的数据库操作Service实现
* @createDate 2024-11-07 18:17:29
*/
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat>
    implements ChatService{

}




