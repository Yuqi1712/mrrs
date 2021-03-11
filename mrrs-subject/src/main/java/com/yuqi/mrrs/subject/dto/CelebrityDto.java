package com.yuqi.mrrs.subject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CelebrityDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape =JsonFormat.Shape.STRING)
    @NotNull(message = "名人id不能为空")
    private Long id;

    @NotBlank(message = "中文名不能为空")
    private String nameCn;

    @NotBlank(message = "英文名不能为空")
    private String nameUs;

}
