package com.yuqi.mrrs.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.subject.entity.SubjectRatingEntity;

import java.util.Map;

/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-31 12:47:10
 */
public interface SubjectRatingService extends IService<SubjectRatingEntity> {

    PageUtils queryPage(Map<String, Object> params);

    SubjectRatingEntity getSubjectRating(Long subject);

}

