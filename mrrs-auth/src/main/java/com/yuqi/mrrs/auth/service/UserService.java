package com.yuqi.mrrs.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqi.mrrs.auth.dto.UserInfoDto;
import com.yuqi.mrrs.auth.dto.UserLoginDto;
import com.yuqi.mrrs.auth.dto.UserRegisterDto;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.auth.entity.UserEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.Map;

/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-02-21 15:45:41
 */
@Validated
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(UserRegisterDto dto);

    void sendValidCode(@Email String email);

    String login(UserLoginDto dto);

    UserInfoDto getUserInfo(String token);

}

