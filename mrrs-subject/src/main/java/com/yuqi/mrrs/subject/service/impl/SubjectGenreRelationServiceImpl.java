package com.yuqi.mrrs.subject.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.Query;

import com.yuqi.mrrs.subject.dao.SubjectGenreRelationDao;
import com.yuqi.mrrs.subject.entity.SubjectGenreRelationEntity;
import com.yuqi.mrrs.subject.service.SubjectGenreRelationService;


@Service("subjectGenreRelationService")
public class SubjectGenreRelationServiceImpl extends ServiceImpl<SubjectGenreRelationDao, SubjectGenreRelationEntity> implements SubjectGenreRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SubjectGenreRelationEntity> page = this.page(
                new Query<SubjectGenreRelationEntity>().getPage(params),
                new QueryWrapper<SubjectGenreRelationEntity>()
        );

        return new PageUtils(page);
    }

}