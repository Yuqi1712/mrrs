package com.yuqi.mrrs.subject.convert;

import com.yuqi.mrrs.subject.dto.SubjectGenreRelationDto;
import com.yuqi.mrrs.subject.entity.SubjectGenreRelationEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SubjectGenreRelationConvert {

    public SubjectGenreRelationEntity dto2Entity(SubjectGenreRelationDto dto){
        SubjectGenreRelationEntity entity = new SubjectGenreRelationEntity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public SubjectGenreRelationDto entity2Dto(SubjectGenreRelationEntity entity){
        SubjectGenreRelationDto dto = new SubjectGenreRelationDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

}
