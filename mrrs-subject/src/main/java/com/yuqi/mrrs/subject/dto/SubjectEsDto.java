package com.yuqi.mrrs.subject.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Subject存储ES的DTO
 */
@Data
public class SubjectEsDto {

    private Long id;

    private String nameCn;

    private String nameUs;

    private String[] label;

    private String[] genre;

    private String[] celebrity;

    private BigDecimal rating;

    private String playImg;

    private String country;

    private String subjectType;

    private String date;

    private Long playVotes;
}
