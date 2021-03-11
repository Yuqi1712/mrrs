package com.yuqi.mrrs.auth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Yuqi
 * @email ${email}
 * @date 2021-02-21 15:45:41
 */
@Data
@TableName("mrrs_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId
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
