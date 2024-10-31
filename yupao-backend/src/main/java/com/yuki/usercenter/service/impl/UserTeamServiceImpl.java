package com.yuki.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yuki.usercenter.mapper.UserTeamMapper;
import com.yuki.usercenter.model.domain.UserTeam;
import com.yuki.usercenter.service.UserTeamService;
import org.springframework.stereotype.Service;

/**
* @author 游玄
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-10-20 15:07:21
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService {

}




