package com.yuqi.mrrs.subject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-31 12:47:10
 */
@Data
@TableName("mrrs_subject_rating")
public class SubjectRatingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 影片id
	 */
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
