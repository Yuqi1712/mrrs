package com.yuqi.mrrs.subject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-21 16:40:13
 */
@Data
@TableName("mrrs_subject")
public class SubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 影片id
	 */
	@TableId
	@JsonFormat(shape =JsonFormat.Shape.STRING)
	private Long id;
	/**
	 * 中文名
	 */
	private String nameCn;
	/**
	 * 英文名
	 */
	private String nameUs;
	/**
	 * 剧情简介
	 */
	private String playDesc;
	/**
	 * 上映日期
	 */
	private LocalDate datePublished;
	/**
	 * 影片标签
	 */
	private String label;
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
	private String otherName;
	/**
	 * 演员列表
	 */
	private String playActor;
	/**
	 * 导演列表
	 */
	private String director;
	/**
	 * 编剧列表
	 */
	private String playWriter;
	/**
	 * 制片国家
	 */
	private String country;
	/**
	 * 语言
	 */
	private String language;
	/**
	 * 影片海报
	 */
	private String playImg;


	/**
	 * 类型  MOVIE:电影
	 * TVPLAY:电视剧
	 */
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
	 * 是否删除 删除为1，未删除为0
	 */
	@TableLogic
	private Boolean deleted;
	/**
	 * 乐观锁版本
	 */
	@Version
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
