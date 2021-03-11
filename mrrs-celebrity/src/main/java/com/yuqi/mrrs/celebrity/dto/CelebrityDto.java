package com.yuqi.mrrs.celebrity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CelebrityDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名人id
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 中文名
     */
    @NotBlank
    private String nameCn;
    /**
     * 英文名
     */
    @NotBlank
    private String nameUs;
    /**
     * MAN-男，WOMAN-女
     */
    private String sex;
    /**
     * 星座
     */
    @NotBlank
    private String constellation;
    /**
     * 出生日期
     */
    private LocalDate birthday;
    /**
     * 出生地
     */
    @NotBlank
    private String birthPlace;
    /**
     * 职业
     */
    private String[] profession;
    /**
     * 别名
     */
    @NotBlank
    private String otherName;
    /**
     * 名人介绍
     */
    @NotBlank
    private String introduction;
    /**
     * 展示图片
     */
    @NotBlank
    private String playImg;
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
