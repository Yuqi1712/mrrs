package com.yuqi.mrrs.subject.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuqi.mrrs.subject.entity.ReviewEntity;
import com.yuqi.mrrs.subject.service.ReviewService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.R;



/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-02-01 11:55:40
 */
@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = reviewService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ReviewEntity review = reviewService.getById(id);

        return R.ok().put("review", review);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ReviewEntity review){
		reviewService.save(review);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ReviewEntity review){
		reviewService.updateById(review);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		reviewService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
