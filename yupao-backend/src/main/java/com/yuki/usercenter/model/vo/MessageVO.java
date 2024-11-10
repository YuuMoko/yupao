package com.yuki.usercenter.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *     userId: number,
 *     messageId: number,
 *     message: string,
 */
@Data
public class MessageVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long messageId;
    private String message;
}
