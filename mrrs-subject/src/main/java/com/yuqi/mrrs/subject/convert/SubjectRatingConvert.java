package com.yuqi.mrrs.subject.convert;

import com.yuqi.mrrs.subject.dto.SubjectDto;
import com.yuqi.mrrs.subject.dto.SubjectRatingDto;
import com.yuqi.mrrs.subject.entity.SubjectRatingEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * 影片评分相关类转换器
 */
@Component
public class SubjectRatingConvert {

    public SubjectRatingDto entity2Dto(SubjectRatingEntity entity){
        SubjectRatingDto dto = new SubjectRatingDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public SubjectRatingEntity dto2Entity(SubjectRatingDto dto){
        SubjectRatingEntity entity = new SubjectRatingEntity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }
}
