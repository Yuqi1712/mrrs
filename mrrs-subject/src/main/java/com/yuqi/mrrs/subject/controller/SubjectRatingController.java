package com.yuqi.mrrs.subject.controller;

import java.util.Arrays;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuqi.mrrs.subject.entity.SubjectRatingEntity;
import com.yuqi.mrrs.subject.service.SubjectRatingService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.R;



/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-31 12:47:10
 */
@RestController
@RequestMapping("/subjectrating")
public class SubjectRatingController {
    @Autowired
    private SubjectRatingService subjectRatingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = subjectRatingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SubjectRatingEntity subjectRating = subjectRatingService.getById(id);

        return R.ok().put("subjectRating", subjectRating);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SubjectRatingEntity subjectRating){
		subjectRatingService.save(subjectRating);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SubjectRatingEntity subjectRating){
		subjectRatingService.updateById(subjectRating);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		subjectRatingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
