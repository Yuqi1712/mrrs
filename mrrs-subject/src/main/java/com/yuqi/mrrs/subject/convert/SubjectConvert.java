package com.yuqi.mrrs.subject.convert;

import com.alibaba.fastjson.JSON;
import com.yuqi.mrrs.subject.dto.CelebrityDto;
import com.yuqi.mrrs.subject.dto.SubjectDto;
import com.yuqi.mrrs.subject.entity.SubjectEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import javax.validation.constraints.NotNull;

/**
 * 影片相关实体类转换器
 */
@Component
public class SubjectConvert {

    public SubjectDto entity2Dto(SubjectEntity entity){
        SubjectDto dto = new SubjectDto();
        BeanUtils.copyProperties(entity,dto);
        //影片标签转换
        dto.setLabel(entity.getLabel().split(","));

        //演员列表转换
        dto.setPlayActor(JSON.parseArray(entity.getPlayActor(), CelebrityDto.class));
        //导演列表转换
        dto.setDirector(JSON.parseArray(entity.getDirector(), CelebrityDto.class));
        //编剧列表转换
        dto.setPlayWriter(JSON.parseArray(entity.getPlayWriter(), CelebrityDto.class));

        return dto;
    }

    public SubjectEntity dto2Entity(SubjectDto subjectDto){
        SubjectEntity entity = new SubjectEntity();
        BeanUtils.copyProperties(subjectDto,entity);
        String director = JSON.toJSONString(subjectDto.getDirector());
        String playActor = JSON.toJSONString(subjectDto.getPlayActor());
        String playWriter = JSON.toJSONString(subjectDto.getPlayWriter());
        String[] labels = subjectDto.getLabel();
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<labels.length;i++){
            buffer.append(labels[i]);
            if(i!=labels.length-1){
                buffer.append(",");
            }
        }
        entity.setLabel(buffer.toString());
        entity.setDirector(director);
        entity.setPlayWriter(playWriter);
        entity.setPlayActor(playActor);
        return entity;
    }

}
