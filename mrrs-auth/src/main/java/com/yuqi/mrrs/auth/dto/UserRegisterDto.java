package com.yuqi.mrrs.auth.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 *用户注册Dto
 */
@Data
public class UserRegisterDto {
    /**
     * 用户账号
     */
    @NotBlank
    private String userAccount;
    /**
     * 用户密码
     */
    @NotBlank
    private String userPassword;
    /**
     * 用户名称
     */
    @NotBlank
    private String userName;
    /**
     * 用户邮箱
     */
    @Email
    private String userEmail;
    /**
     * 邮箱验证码
     */
    @NotBlank
    private String validCode;
}
