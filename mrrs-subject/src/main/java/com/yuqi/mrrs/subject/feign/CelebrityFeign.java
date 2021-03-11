package com.yuqi.mrrs.subject.feign;

import com.yuqi.mrrs.common.utils.R;
import com.yuqi.mrrs.subject.feign.fallback.CelebrityFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient(value = "mrrs-celebrity",fallback = CelebrityFallback.class)
public interface CelebrityFeign {

    @RequestMapping("/celebrity/info/{id}")
    public R info(@PathVariable("id") Long id);
}
