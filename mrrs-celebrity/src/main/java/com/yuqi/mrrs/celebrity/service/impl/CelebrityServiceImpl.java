package com.yuqi.mrrs.celebrity.service.impl;

import com.yuqi.mrrs.celebrity.convert.CelebrityConvert;
import com.yuqi.mrrs.celebrity.dto.CelebrityDto;
import com.yuqi.mrrs.celebrity.dto.CelebrityListDto;
import com.yuqi.mrrs.celebrity.dto.HotCelebrityDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.Query;

import com.yuqi.mrrs.celebrity.dao.CelebrityDao;
import com.yuqi.mrrs.celebrity.entity.CelebrityEntity;
import com.yuqi.mrrs.celebrity.service.CelebrityService;
import org.springframework.util.StringUtils;


@Service("celebrityService")
public class CelebrityServiceImpl extends ServiceImpl<CelebrityDao, CelebrityEntity> implements CelebrityService {

    @Autowired
    CelebrityDao celebrityDao;

    @Autowired
    CelebrityConvert celebrityConvert;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${mrrs.celebrity.hot.redis.prefix}")
    String prefix;

    @Value("${mrrs.celebrity.hot.redis.key}")
    String key;

    @Value("${mrrs.celebrity.hot.redis.TTL}")
    Long TTL;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object key = params.get("key");
        QueryWrapper<CelebrityEntity> wrapper = new QueryWrapper<>();
        if(null!=key) {
            String skey=key.toString();
            if (!StringUtils.isEmpty(skey)) {
                wrapper.like("name_cn", skey).or().like("name_us", skey);
            }
        }

        if(params.get("limit")==null){
            params.put("limit","10000");
        }
        IPage<CelebrityEntity> page = this.page(
                new Query<CelebrityEntity>().getPage(params),wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void add(CelebrityDto celebrityDto) {
        //step1: 数据入库
        CelebrityEntity entity = celebrityConvert.dto2Entity(celebrityDto);
        entity.setVersion(0);
        entity.setDeleted(Boolean.FALSE);
        this.baseMapper.insert(entity);

        //step2: 名人入redis热度集合
        stringRedisTemplate.opsForZSet().add(prefix+"::"+key,entity.getId()+"_"+entity.getNameCn(),0l);
    }

    @Override
    public void update(CelebrityDto celebrityDto) {
        CelebrityEntity entity = celebrityConvert.dto2Entity(celebrityDto);
        entity.setDeleted(null);
        this.updateById(entity);
    }

    @Override
    public List<HotCelebrityDto> getTodayHotCelebrity() {
        Set<ZSetOperations.TypedTuple<String>> set = stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(prefix + "::" + key, -1, 999999, 0, 5);
        ArrayList<HotCelebrityDto> list = new ArrayList<>();
        set.stream().forEach(item->{
            HotCelebrityDto dto = new HotCelebrityDto();
            String[] s = item.getValue().split("_");
            dto.setId(Long.parseLong(s[0]));
            dto.setNameCn(s[1]);
            dto.setScore(item.getScore());
            list.add(dto);
        });
        return list;

    }

    @Override
    public List<CelebrityListDto> getCelebrityList(List<CelebrityListDto> celebrityListDto) {
        List<CelebrityListDto> list = celebrityListDto.stream().map(item -> {
            CelebrityEntity entity = this.baseMapper.selectById(item.getId());
            item.setPlayImg(entity.getPlayImg());
            return item;
        }).collect(Collectors.toList());
        return list;
    }

}