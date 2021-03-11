package com.yuqi.mrrs.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqi.mrrs.subject.entity.SubjectEntity;
import com.yuqi.mrrs.subject.entity.UserReviewHistoryEntity;

import java.util.List;

public interface UserReviewHistoryService extends IService<UserReviewHistoryEntity> {

    public List<SubjectEntity> getRecommendSubject(Long userId);
}
