package com.yuqi.mrrs.auth.feign;

import io.renren.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mrrs-subject")
public interface SubjectFeign {

    @GetMapping("reviewHistory/addUser2ReviewHistory")
    public R addUser2ReviewHistory(@RequestParam Long userId);
}
