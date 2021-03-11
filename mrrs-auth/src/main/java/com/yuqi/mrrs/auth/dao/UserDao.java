package com.yuqi.mrrs.auth.dao;

import com.yuqi.mrrs.auth.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Yuqi
 * @email ${email}
 * @date 2021-02-21 15:45:41
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    public int checkUserAccount(String account);

    public int checkUserEmail(String email);
	
}
