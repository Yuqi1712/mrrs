package com.yuqi.mrrs.subject.dto;

import lombok.Data;

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
