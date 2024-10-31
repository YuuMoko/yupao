package com.yuki.usercenter.service;

import com.yuki.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author YuuMoko
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-09-13 23:36:57
 */
public interface UserService extends IService<User> {

    /**
     * 用户注释
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param user
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     * @param tagList
     * @return
     */
    List<User> searchUsersByTags(List<String> tagList);

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    boolean isAdmin(HttpServletRequest request);

    /**
     *
     * @param user
     * @param loginUser
     * 更新用户信息
     * @return
     */
    Integer updateUser(User user, User loginUser);

    /**
     * 是否为管理员
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);

    /**
     * 通过标签相似度来匹配用户
     * @param num
     * @param loginUser
     * @return
     */
    List<User> matchUsers(long num, User loginUser);
}
