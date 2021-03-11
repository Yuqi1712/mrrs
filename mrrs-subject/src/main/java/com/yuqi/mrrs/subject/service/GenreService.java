package com.yuqi.mrrs.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.subject.dto.GenreDto;
import com.yuqi.mrrs.subject.entity.GenreEntity;

import java.util.Map;

/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-26 11:35:43
 */
public interface GenreService extends IService<GenreEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void add(GenreDto genreDto);

    void update(GenreDto genreDto);

    void movieAccountAdd(Long genreId);

    void movieAccountSub(Long genreId);

}

