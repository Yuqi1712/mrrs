package com.yuqi.mrrs.auth.frontend;

import com.yuqi.mrrs.auth.dto.UserInfoDto;
import com.yuqi.mrrs.auth.dto.UserLoginDto;
import com.yuqi.mrrs.auth.dto.UserRegisterDto;
import com.yuqi.mrrs.auth.entity.UserEntity;
import com.yuqi.mrrs.auth.service.UserService;
import com.yuqi.yuqi.annotation.TokenRequired;
import io.renren.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController("frontUserController")
@RequestMapping("/frontend/user")
public class UserController {


    @Autowired
    UserService userService;

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public UserEntity info(@PathVariable("id") Long id){
       return userService.getById(id);
    }

    @PostMapping("/register")
    public R register(@RequestBody @Validated UserRegisterDto dto){
        userService.register(dto);
        return R.ok();
    }


    @GetMapping("/sendValidCode")
    public R sendValidCode(@RequestParam  String email){
        userService.sendValidCode(email);
        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody @Validated UserLoginDto dto){
        String token = userService.login(dto);
        return R.ok().put("token",token);
    }

    @TokenRequired
    @GetMapping("/getUserInfo")
    public R getUserInfo(@RequestParam String token ){
        UserInfoDto dto = userService.getUserInfo(token);
        return R.ok().put("userInfo",dto);
    }

}
