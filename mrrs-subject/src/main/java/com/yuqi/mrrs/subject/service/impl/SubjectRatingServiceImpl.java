package com.yuqi.mrrs.subject.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.Query;

import com.yuqi.mrrs.subject.dao.SubjectRatingDao;
import com.yuqi.mrrs.subject.entity.SubjectRatingEntity;
import com.yuqi.mrrs.subject.service.SubjectRatingService;


@Service("subjectRatingService")
public class SubjectRatingServiceImpl extends ServiceImpl<SubjectRatingDao, SubjectRatingEntity> implements SubjectRatingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SubjectRatingEntity> page = this.page(
                new Query<SubjectRatingEntity>().getPage(params),
                new QueryWrapper<SubjectRatingEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public SubjectRatingEntity getSubjectRating(Long subject) {
       return this.baseMapper.selectOne(new QueryWrapper<SubjectRatingEntity>().eq("subject_id",subject));
    }

}