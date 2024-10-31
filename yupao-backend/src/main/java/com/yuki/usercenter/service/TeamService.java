package com.yuki.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuki.usercenter.model.domain.Team;
import com.yuki.usercenter.model.domain.User;
import com.yuki.usercenter.model.dto.TeamQuery;
import com.yuki.usercenter.model.request.TeamJoinRequest;
import com.yuki.usercenter.model.request.TeamQuitRequest;
import com.yuki.usercenter.model.request.TeamUpdateRequest;
import com.yuki.usercenter.model.vo.TeamUserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* @author YuuMoko
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-10-20 15:05:05
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     * @param teamQuery
     * @param isAdmin
     * @return
     */

    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍信息
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除队伍
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);
}
