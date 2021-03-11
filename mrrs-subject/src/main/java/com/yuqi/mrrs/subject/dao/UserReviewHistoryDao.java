package com.yuqi.mrrs.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuqi.mrrs.subject.entity.UserReviewHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserReviewHistoryDao extends BaseMapper<UserReviewHistoryEntity> {

    public List<Long> getSimilarLikeUser(Map<String,Object> params);
}
