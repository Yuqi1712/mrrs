package com.yuqi.mrrs.subject.feign.fallback;

import com.yuqi.mrrs.subject.dto.SearchParam;
import com.yuqi.mrrs.subject.dto.SubjectEsDto;
import com.yuqi.mrrs.subject.dto.SubjectEsRespDto;
import com.yuqi.mrrs.subject.feign.ElasticSearchFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ElasticSearchFallback implements ElasticSearchFeign {
    @Override
    public void addSubject(SubjectEsDto dto) {
      log.error("远程调用addSubject方法失败，降级处理");
    }

    @Override
    public SubjectEsRespDto getSubjectList(SearchParam searchParam) {
        log.error("远程调用方法获取SubjectList失败，降级处理");
        return null;
    }

    @Override
    public SubjectEsRespDto bestFiveSubject(String name) {
        log.error("远程调用方法获取bestFiveSubject失败，降级处理");
        return null;
    }

    @Override
    public SubjectEsRespDto latestFiveSubject(String name) {
        log.error("远程调用方法获取latestFiveSubject失败，降级处理");
        return null;
    }

    @Override
    public SubjectEsRespDto getTopSubject(SearchParam searchParam) {
        log.error("远程调用方法获取getTopSubject失败，降级处理");
        return null;
    }

    @Override
    public void updateSubject(SubjectEsDto subjectEsDto) {
        log.error("远程调用方法获取updateSubject失败，降级处理");
    }

    @Override
    public SubjectEsRespDto getRecentHot(String subjectType) {
        log.error("远程调用方法失败，降级处理");
        return null;
    }
}
