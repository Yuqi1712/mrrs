package com.yuqi.mrrs.auth.service.impl;

import com.yuqi.mrrs.auth.dto.UserInfoDto;
import com.yuqi.mrrs.auth.dto.UserLoginDto;
import com.yuqi.mrrs.auth.dto.UserRegisterDto;
import com.yuqi.mrrs.auth.feign.SubjectFeign;
import com.yuqi.mrrs.auth.util.MailUtil;
import com.yuqi.yuqi.annotation.TokenRequired;
import com.yuqi.yuqi.exception.CommonException;
import com.yuqi.yuqi.utils.JwtUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.Query;

import com.yuqi.mrrs.auth.dao.UserDao;
import com.yuqi.mrrs.auth.entity.UserEntity;
import com.yuqi.mrrs.auth.service.UserService;
import org.springframework.validation.annotation.Validated;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;


@Log4j2
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    MailUtil mailUtil;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    SubjectFeign feign;

    @Value("${mrrs.user.redis.registerValidTTL:10}")
    Long registerValidTTL;

    @Value("${mrrs.user.redis.prefix:user}")
    String prefix;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(UserRegisterDto dto) {
        String s = stringRedisTemplate.opsForValue().get(prefix+"::"+dto.getUserEmail());
        if(s==null){
            throw new CommonException("验证码可能已过期，请重新发送");
        }
        if(!s.equals(dto.getValidCode())){
            throw new CommonException("验证码错误，请重新输入");
        }
        if(userDao.checkUserAccount(dto.getUserAccount())>0){
            log.error("该账户已被注册，需更换账号");
            throw new CommonException("该账号已被注册，请重新输入");
        }
        if(userDao.checkUserEmail(dto.getUserEmail())>0){
            log.error("该邮箱已被注册，需更换邮箱");
            throw new CommonException("该邮箱已被注册，请重新输入");
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto,entity);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(dto.getUserPassword());
        entity.setUserPassword(encodePassword);
        this.baseMapper.insert(entity);

        //添加用户至用户影片评价历史表中
        feign.addUser2ReviewHistory(entity.getId());
    }

    @Override
    public void sendValidCode(@Email String email) {
        if(userDao.checkUserEmail(email)>0){
            log.error("邮箱已被注册，请更换邮箱");
            throw new CommonException("邮箱已被注册，请更换邮箱");
        }
        int code = (int) ((Math.random() * 9 + 1) * 100000);

        HashMap<String, Object> params = new HashMap<>();
        params.put("validCode",code);
        mailUtil.SendTemplateMail(email,"MRRS-申请注册验证码",params);

        stringRedisTemplate.opsForValue().set(prefix+"::"+email,String.valueOf(code),registerValidTTL, TimeUnit.MINUTES);

    }

    @Override
    public String login(UserLoginDto dto) {
        UserEntity entity = this.baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("user_account", dto.getUserAccount()).or().eq("user_email", dto.getUserAccount()));
        if(entity==null){
            throw new CommonException("账号不存在，请重新登录");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(dto.getUserPassword(), entity.getUserPassword());
        if(matches==false){
            throw new CommonException("账号或密码错误，请重新登录");
        }

        //登录成功删除redis中的验证码
        stringRedisTemplate.delete(prefix+"::"+entity.getUserEmail());
        return JwtUtil.createJwtById(entity.getId().toString());
    }

    @Override
    public UserInfoDto getUserInfo(String token) {
        String id = JwtUtil.getUserId(token);
        UserInfoDto dto = new UserInfoDto();
        UserEntity entity = this.baseMapper.selectById(Long.parseLong(id));
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

}