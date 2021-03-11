package com.yuqi.mrrs.subject.feign.fallback;

import com.yuqi.mrrs.common.utils.R;
import com.yuqi.mrrs.subject.dto.UserDto;
import com.yuqi.mrrs.subject.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFallback implements UserFeign {
    @Override
    public UserDto info(Long id) {
        log.error("远程调用user信息失败");
        return null;
    }
}
