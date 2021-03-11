package com.yuqi.mrrs.subject.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 影片id
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 中文名
     */
    @NotBlank(message = "中文名不能为空")
    private String nameCn;
    /**
     * 英文名
     */
    @NotBlank(message = "英文名不能为空")
    private String nameUs;
    /**
     * 剧情简介
     */
    @NotBlank(message = "剧情简介不能为空")
    private String playDesc;

    /**
     * 上映日期
     */
    @NotNull(message = "上映日期不能为null")
    private LocalDate datePublished;

    /**
     * 影片类型关系列表
     */
    @NotNull(message = "影片类型关系列表不能为null")
    private List<SubjectGenreRelationDto> genre;
    /**
     * 影片标签
     */
    @NotNull(message = "影片标签不能为null")
    private String[] label;
    /**
     * 影片评分
     */
    private BigDecimal rating;
    /**
     * 评分人数
     */
    private Long playVotes;
    /**
     * 别民
     */
    @NotBlank(message = "别名不能为空")
    private String otherName;
    /**
     * 演员列表
     */
    @NotNull(message = "演员列表不能为null")
    private List<CelebrityDto> playActor;
    /**
     * 导演列表
     */
    @NotNull(message = "导演列表不能为null")
    private List<CelebrityDto> director;
    /**
     * 编剧列表
     */
    @NotNull(message = "编剧列表不能为null")
    private List<CelebrityDto> playWriter;
    /**
     * 制片国家
     */
    @NotBlank(message = "制片国家不能为空")
    private String country;
    /**
     * 语言
     */
    @NotBlank(message = "语言不能为空")
    private String language;
    /**
     * 影片海报
     */
    private String playImg;

    /**
     * 类型  MOVIE:电影 TVPLAY:电视剧
     */
    @NotBlank(message = "中文名不能为空")
    private String subjectType;
    /**
     * 电视剧集数
     */
    private Integer number;
    /**
     * 影片时长
     */
    private Integer duration;

    /**
     * 是否删除 未删除:0 删除:1
     */
    private Boolean deleted;
    /**
     * 乐观锁版本
     */
    private Integer version;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
