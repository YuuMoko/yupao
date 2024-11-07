package com.yuki.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuki.usercenter.model.domain.Message;
import com.yuki.usercenter.service.MessageService;
import com.yuki.usercenter.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 游玄
* @description 针对表【message】的数据库操作Service实现
* @createDate 2024-11-07 18:17:31
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




