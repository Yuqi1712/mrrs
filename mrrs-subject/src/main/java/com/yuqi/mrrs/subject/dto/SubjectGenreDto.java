package com.yuqi.mrrs.subject.dto;

import com.yuqi.yuqi.vaildGroup.AddGroup;
import com.yuqi.yuqi.vaildGroup.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SubjectGenreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型id
     */
    @NotNull(message = "类型id不能为null")
    private Long id;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名不能为空")
    private String name;

}
