package com.yuqi.mrrs.celebrity.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-22 12:28:13
 */
@Data
@TableName("mrrs_celebrity")
public class CelebrityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@TableId
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
	 * MAN-男，WOMAN-女
	 */
	private String sex;
	/**
	 * 星座
	 */
	private String constellation;
	/**
	 * 出生日期
	 */
	private LocalDate birthday;
	/**
	 * 出生地
	 */
	private String birthPlace;
	/**
	 * 职业
	 */
	private String profession;
	/**
	 * 别名
	 */
	private String otherName;
	/**
	 * 名人介绍
	 */
	private String introduction;
	/**
	 * 展示图片
	 */
	private String playImg;
	/**
	 * 乐观锁版本
	 */
	@Version
	private Integer version;
	/**
	 * 是否被删除: 0-未删除,1-删除
	 */
	@TableLogic
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
