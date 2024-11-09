package com.yuki.usercenter.mapper;

import com.yuki.usercenter.model.domain.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 游玄
* @description 针对表【chat(聊天室表)】的数据库操作Mapper
* @createDate 2024-11-07 18:17:29
* @Entity com.yuki.usercenter.model.domain.Chat
*/
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {

}




