package com.yuki.usercenter.mapper;

import com.yuki.usercenter.model.domain.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 游玄
* @description 针对表【message】的数据库操作Mapper
* @createDate 2024-11-07 18:17:31
* @Entity com.yuki.usercenter.model.domain.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}




