package com.yuqi.mrrs.subject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
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
 * @date 2021-01-26 11:35:43
 */
@Data
@TableName("mrrs_genre")
public class GenreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 类型id
	 */
	@TableId
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;
	/**
	 * 类型名称
	 */
	private String name;
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
