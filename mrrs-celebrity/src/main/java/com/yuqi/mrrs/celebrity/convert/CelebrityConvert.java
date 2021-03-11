package com.yuqi.mrrs.celebrity.convert;

import com.yuqi.mrrs.celebrity.dto.CelebrityDto;
import com.yuqi.mrrs.celebrity.entity.CelebrityEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * 名人相关实体类转换器
 */
@Component
public class CelebrityConvert {

    public CelebrityEntity dto2Entity(CelebrityDto celebrityDto){
        CelebrityEntity entity = new CelebrityEntity();
        BeanUtils.copyProperties(celebrityDto,entity);
        StringBuffer buffer = new StringBuffer();
        String[] professions = celebrityDto.getProfession();
        for(int i=0;i<professions.length;i++){
            buffer.append(professions[i]);
            if(i<professions.length-1){
                buffer.append(",");
            }
        }
        entity.setProfession(buffer.toString());
        return entity;
    }

    public CelebrityDto entity2Dto(CelebrityEntity entity){
        CelebrityDto dto = new CelebrityDto();
        BeanUtils.copyProperties(entity,dto);
        String[] professions = entity.getProfession().split(",");
        dto.setProfession(professions);
        return dto;
    }
}
