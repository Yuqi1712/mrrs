package com.yuqi.mrrs.subject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 今日最热影视Top5Dto
 */
@Data
public class HotSubjectDto {

    @JsonFormat(shape =JsonFormat.Shape.STRING)
    private Long id;

    private String nameCn;

    private double score;
}
