package com.yuqi.mrrs.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.subject.dto.HotSubjectDto;
import com.yuqi.mrrs.subject.dto.SubjectDto;
import com.yuqi.mrrs.subject.entity.SubjectEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-21 16:40:13
 */
public interface SubjectService extends IService<SubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void add(SubjectDto dto);

    SubjectDto info(Long id);

    List<HotSubjectDto> getTodayHotSubject();

}

