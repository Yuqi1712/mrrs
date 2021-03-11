package com.yuqi.mrrs.subject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-28 11:48:11
 */
@Data
@TableName("mrrs_subject_genre_relation")
public class SubjectGenreRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 影片与类型关系id
	 */
	@TableId
	private Long id;
	/**
	 * 影片id
	 */
	private Long subjectId;
	/**
	 * 影片类型id
	 */
	private Long genreId;
	/**
	 * 影片类型名称
	 */
	private String genreName;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

}
