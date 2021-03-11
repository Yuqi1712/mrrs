package com.yuqi.mrrs.subject.dto;

import lombok.Data;

@Data
public class ReviewSearchParam {

    private Long subject;

    private String page;

    private String limit;
}
