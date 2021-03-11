package com.yuqi.mrrs.subject.feign.fallback;

import com.yuqi.mrrs.common.utils.R;
import com.yuqi.mrrs.subject.feign.CelebrityFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CelebrityFallback implements CelebrityFeign {
    @Override
    public R info(Long id) {
      log.error("远程查询celebrity Info失败");
        return null;
    }
}
