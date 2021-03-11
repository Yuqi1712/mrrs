package com.yuqi.mrrs.celebrity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqi.mrrs.celebrity.dto.CelebrityDto;
import com.yuqi.mrrs.celebrity.dto.CelebrityListDto;
import com.yuqi.mrrs.celebrity.dto.HotCelebrityDto;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.celebrity.entity.CelebrityEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-22 12:28:13
 */
public interface CelebrityService extends IService<CelebrityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void add(CelebrityDto celebrityDto);

    void update(CelebrityDto celebrityDto);


    List<HotCelebrityDto> getTodayHotCelebrity();

    List<CelebrityListDto> getCelebrityList(List<CelebrityListDto> celebrityListDto);

}

