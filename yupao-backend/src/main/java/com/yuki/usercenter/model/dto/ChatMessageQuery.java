package com.yuki.usercenter.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMessageQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idA; // 用户A的ID
    private Long idB; // 用户B的ID
    private Long minMessageId; // 最小的Message的ID
    private Long teamId; // 当前队伍的ID
}
