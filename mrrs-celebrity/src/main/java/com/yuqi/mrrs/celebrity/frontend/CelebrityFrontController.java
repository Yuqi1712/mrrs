package com.yuqi.mrrs.celebrity.frontend;

import com.yuqi.mrrs.celebrity.convert.CelebrityConvert;
import com.yuqi.mrrs.celebrity.dto.CelebrityDto;
import com.yuqi.mrrs.celebrity.dto.CelebrityListDto;
import com.yuqi.mrrs.celebrity.dto.HotCelebrityDto;
import com.yuqi.mrrs.celebrity.entity.CelebrityEntity;
import com.yuqi.mrrs.celebrity.service.CelebrityService;
import com.yuqi.mrrs.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frontend/celebrity")
public class CelebrityFrontController {
    @Autowired
    private CelebrityService celebrityService;

    @Autowired
    private CelebrityConvert celebrityConvert;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${mrrs.celebrity.hot.redis.prefix}")
    String prefix;

    @Value("${mrrs.celebrity.hot.redis.key}")
    String key;

    @Value("${mrrs.celebrity.hot.redis.TTL}")
    Long TTL;

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CelebrityEntity celebrity = celebrityService.getById(id);
        CelebrityDto dto = celebrityConvert.entity2Dto(celebrity);
        stringRedisTemplate.opsForZSet().incrementScore(prefix+"::"+key,dto.getId()+"_"+dto.getNameCn(),1l);
        return R.ok().put("celebrity", dto);
    }

    /**
     * 获取今日最热top5名人
     */
    @GetMapping("/getTodayHotCelebrity")
    public R getTodayHotCelebrity(){
        List<HotCelebrityDto> list= celebrityService.getTodayHotCelebrity();
        return R.ok().put("list", list);
    }

    @PostMapping("/getCelebrityList")
    public R getCelebrityList(@RequestBody List<CelebrityListDto> celebrityListDto){
        List<CelebrityListDto> list = celebrityService.getCelebrityList(celebrityListDto);
        return R.ok().put("list",list);
    }
}
