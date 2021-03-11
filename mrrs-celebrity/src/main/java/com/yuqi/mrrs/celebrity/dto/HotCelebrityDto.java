package com.yuqi.mrrs.celebrity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class HotCelebrityDto {

    @JsonFormat(shape =JsonFormat.Shape.STRING)
    private Long id;

    private String nameCn;

    private Double score;
}
