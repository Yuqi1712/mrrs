package com.yuqi.mrrs.subject.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户密码
     */
    private String userPassword;
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
    /**
     * 乐观锁版本
     */
    private Integer version;
    /**
     * 是否被删除: 0-未删除,1-删除
     */
    private Boolean deleted;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
