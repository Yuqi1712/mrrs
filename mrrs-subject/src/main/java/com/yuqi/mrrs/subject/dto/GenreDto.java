package com.yuqi.mrrs.subject.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuqi.yuqi.vaildGroup.AddGroup;
import com.yuqi.yuqi.vaildGroup.UpdateGroup;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GenreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型id
     */
    @TableId
    @JsonFormat(shape =JsonFormat.Shape.STRING)
    @NotNull(message = "影片类型id不能为null",groups = {UpdateGroup.class})
    private Long id;
    /**
     * 类型名称
     */
    @NotBlank(message = "影片类型名不能为空",groups = {UpdateGroup.class,AddGroup.class})
    private String name;
    /**
     * 乐观锁版本
     */
    private Integer version;
    /**
     * 是否被删除: 0-未删除,1-删除
     */
    private Boolean deleted;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
