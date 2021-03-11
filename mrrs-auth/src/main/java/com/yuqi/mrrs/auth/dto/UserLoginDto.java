package com.yuqi.mrrs.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDto {
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
}
