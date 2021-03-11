package com.yuqi.mrrs.subject.convert;

import com.yuqi.mrrs.subject.dto.GenreDto;
import com.yuqi.mrrs.subject.entity.GenreEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * 影片类型相关实体类转换器
 */
@Component
public class GenreConvert {

    public GenreDto entity2Dto(GenreEntity entity){
        GenreDto dto = new GenreDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public GenreEntity dto2Entity(GenreDto dto){
        GenreEntity entity = new GenreEntity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

}
