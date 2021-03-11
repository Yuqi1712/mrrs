package com.yuqi.mrrs.subject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Yuqi
 * @email ${email}
 * @date 2021-02-01 11:55:40
 */
@Data
@TableName("mrrs_review")
public class ReviewEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 影片评论id
	 */
	@TableId
	private Long id;
	/**
	 * 影片id
	 */
	private Long subject;
	/**
	 * 评论者id
	 */
	private String userId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评级  1,2,3,4,5 对应星级
	 */
	private String rating;
	/**
	 * 评论日期
	 */
	private LocalDateTime reviewDate;
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
