package com.yuqi.mrrs.subject.feign;

import com.yuqi.mrrs.subject.dto.SearchParam;
import com.yuqi.mrrs.subject.dto.SubjectEsDto;
import com.yuqi.mrrs.subject.dto.SubjectEsRespDto;
import com.yuqi.mrrs.subject.feign.fallback.ElasticSearchFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "mrrs-thirdservice",fallback = ElasticSearchFallback.class)
public interface ElasticSearchFeign {

    @PostMapping("EsSubject/addSubject")
    public void addSubject(@RequestBody SubjectEsDto dto);

    @PostMapping("EsSubject/subjectList")
    public SubjectEsRespDto getSubjectList(@RequestBody SearchParam searchParam);

    @GetMapping("EsSubject/bestFiveSubject")
    public SubjectEsRespDto bestFiveSubject(@RequestParam String name);

    @GetMapping("EsSubject/latestFiveSubject")
    public SubjectEsRespDto latestFiveSubject(@RequestParam String name);

    @PostMapping("EsSubject/getTopSubject")
    public SubjectEsRespDto getTopSubject(@RequestBody SearchParam searchParam);

    @PostMapping("EsSubject/updateSubject")
    public void updateSubject(@RequestBody SubjectEsDto subjectEsDto);

    @GetMapping("EsSubject/getRecentHot")
    public SubjectEsRespDto getRecentHot(@RequestParam String subjectType);
}
