package com.yuqi.mrrs.subject.controller;

import java.util.Arrays;
import java.util.Map;


import com.yuqi.mrrs.subject.convert.SubjectConvert;
import com.yuqi.mrrs.subject.dto.SubjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuqi.mrrs.subject.entity.SubjectEntity;
import com.yuqi.mrrs.subject.service.SubjectService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.R;



/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-21 16:40:13
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectConvert subjectConvert;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = subjectService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SubjectDto subject = subjectService.info(id);
        return R.ok().put("subject", subject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody @Validated SubjectDto dto){
		subjectService.add(dto);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SubjectEntity subject){
		subjectService.updateById(subject);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		subjectService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
