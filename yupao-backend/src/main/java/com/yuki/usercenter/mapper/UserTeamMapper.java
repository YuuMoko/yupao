package com.yuki.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.usercenter.model.domain.UserTeam;
import org.apache.ibatis.annotations.Mapper;


/**
* @author 游玄
* @description 针对表【user_team(用户队伍关系)】的数据库操作Mapper
* @createDate 2024-10-20 15:07:21
* @Entity generator.domain.UserTeam
*/
@Mapper
public interface UserTeamMapper extends BaseMapper<UserTeam> {

}




