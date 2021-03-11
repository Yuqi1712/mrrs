package com.yuqi.mrrs.thirdservice.dto;

import com.yuqi.mrrs.thirdservice.es.entity.SubjectEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 封装从Es获取的Subject集合类
 */
@Data
public class SubjectEsRespDto {

    private List<SubjectEntity> subjectList;

    private List<String> labelList;

    private List<String> genreList;

    private long total;

}
