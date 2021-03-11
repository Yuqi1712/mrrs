package com.yuqi.mrrs.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.subject.dto.ReviewDto;
import com.yuqi.mrrs.subject.dto.ReviewSearchParam;
import com.yuqi.mrrs.subject.entity.ReviewEntity;

import java.util.Map;

/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-02-01 11:55:40
 */
public interface ReviewService extends IService<ReviewEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils reviewList(ReviewSearchParam params);

    void addReview(ReviewDto review);



}

