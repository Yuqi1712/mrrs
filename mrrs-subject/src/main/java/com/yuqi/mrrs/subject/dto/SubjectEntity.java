package com.yuqi.mrrs.subject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SubjectEntity {

    @JsonFormat(shape =JsonFormat.Shape.STRING)
    private Long id;

    private String nameCn;

    private String nameUs;

    private String[] label;

    private String[] genre;

    private String[] celebrity;

    private BigDecimal rating;

    private Long playVotes;

    private String playImg;

    private String country;

    private String subjectType;

    private String date;

}
