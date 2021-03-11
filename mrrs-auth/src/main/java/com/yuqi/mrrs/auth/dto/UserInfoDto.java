package com.yuqi.mrrs.auth.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoDto {

    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户年龄
     */
    private Integer userAge;
    /**
     * MAN-男，WOMAN-女
     */
    private String userGender;
    /**
     * 用户头像url
     */
    private String userIcon;
    /**
     * 用户邮箱
     */
    private String userEmail;
}
