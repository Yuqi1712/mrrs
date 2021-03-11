package com.yuqi.mrrs.subject.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SubjectRatingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 影片id
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING)
    private Long subjectId;
    /**
     * 影片名称
     */
    private String subjectName;
    /**
     * 评分
     */
    private BigDecimal ratingScore;
    /**
     * 五星评级数
     */
    private Long ratingA;
    /**
     * 四星评级数
     */
    private Long ratingB;
    /**
     * 三星评级数
     */
    private Long ratingC;
    /**
     * 二星评级数
     */
    private Long ratingD;
    /**
     * 一星评级数
     */
    private Long ratingE;
    /**
     * 评级人数
     */
    private Long ratingCount;
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
