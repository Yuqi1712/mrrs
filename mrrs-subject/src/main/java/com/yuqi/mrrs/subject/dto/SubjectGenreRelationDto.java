package com.yuqi.mrrs.subject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SubjectGenreRelationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 影片id
     */
    @NotNull(message = "影片id不能为null")
    @JsonFormat(shape =JsonFormat.Shape.STRING)
    private Long subjectId;
    /**
     * 影片类型id
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING)
    @NotNull(message = "影片类型id不能为空")
    private Long genreId;
    /**
     * 影片类型名称
     */
    @NotBlank(message = "影片类型名称不能为空")
    private String genreName;

}
