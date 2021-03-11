package com.yuqi.mrrs.subject.feign;

import com.yuqi.mrrs.common.utils.R;
import com.yuqi.mrrs.subject.dto.UserDto;
import com.yuqi.mrrs.subject.feign.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "mrrs-auth",fallback = UserFallback.class)
public interface UserFeign {

    @RequestMapping("frontend/user/info/{id}")
    public UserDto info(@PathVariable("id") Long id);
}
