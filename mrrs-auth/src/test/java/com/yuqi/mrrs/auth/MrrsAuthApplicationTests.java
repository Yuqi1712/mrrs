package com.yuqi.mrrs.auth;

import com.yuqi.mrrs.auth.dto.UserRegisterDto;
import com.yuqi.mrrs.auth.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MrrsAuthApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
      int loop=10;
      while (loop-->0){
          int userAccount = (int) ((Math.random() * 9 + 1) * 100000000);
          UserRegisterDto dto = new UserRegisterDto();
          dto.setUserAccount(String.valueOf(userAccount));
          dto.setUserEmail(userAccount+"@qq.com");
          dto.setUserPassword("123456");
          dto.setUserName("Robot"+loop);
          userService.register(dto);
      }
    }



}
