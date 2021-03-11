package com.yuqi.mrrs.celebrity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * subject详情页下职员表封装dto
 */
@Data
public class CelebrityListDto {
    @JsonFormat(shape =JsonFormat.Shape.STRING)
    private Long id;

    private String nameCn;

    private String nameUs;

    private String playImg;
}
