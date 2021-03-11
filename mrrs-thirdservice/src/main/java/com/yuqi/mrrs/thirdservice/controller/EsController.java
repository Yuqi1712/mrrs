package com.yuqi.mrrs.thirdservice.controller;

import com.yuqi.mrrs.thirdservice.dto.SubjectEsRespDto;
import com.yuqi.mrrs.thirdservice.es.SearchParam;
import com.yuqi.mrrs.thirdservice.es.entity.SubjectEntity;
import com.yuqi.mrrs.thirdservice.service.EsSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/EsSubject")
public class EsController {

    @Autowired
    EsSubjectService esSubjectService;

    @PostMapping("/addSubject")
    public void addSubject(@RequestBody SubjectEntity subjectEntity){
        esSubjectService.addSubject(subjectEntity);
    }

    @PostMapping("/subjectList")
    public SubjectEsRespDto getSubjectList(@RequestBody SearchParam searchParam){
        return esSubjectService.getSubjectList(searchParam);
    }

    @GetMapping("/bestFiveSubject")
    public SubjectEsRespDto bestFiveSubject(@RequestParam String name){
        return esSubjectService.getBestFiveSubject(name);
    }

    @GetMapping("/latestFiveSubject")
    public SubjectEsRespDto latestFiveSubject(@RequestParam String name){
        return esSubjectService.getLatestFiveSubject(name);
    }

    @PostMapping("/getTopSubject")
    public SubjectEsRespDto getTopSubject(@RequestBody SearchParam searchParam){
        return esSubjectService.getTopSubject(searchParam);
    }

    @PostMapping("/updateSubject")
    public void updateSubject(@RequestBody SubjectEntity subjectEntity){
        esSubjectService.updateSubject(subjectEntity);
    }

    @GetMapping("/getRecentHot")
    public SubjectEsRespDto getRecentHotMovie(@RequestParam String subjectType){
       return esSubjectService.getRecentHotMovie(subjectType);
    }
}
