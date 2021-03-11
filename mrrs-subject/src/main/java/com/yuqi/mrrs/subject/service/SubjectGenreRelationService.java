package com.yuqi.mrrs.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.subject.entity.SubjectGenreRelationEntity;

import java.util.Map;

/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-28 11:48:11
 */
public interface SubjectGenreRelationService extends IService<SubjectGenreRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

