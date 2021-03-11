package com.yuqi.mrrs.subject.frontend;

import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.R;
import com.yuqi.mrrs.subject.dto.ReviewDto;
import com.yuqi.mrrs.subject.dto.ReviewSearchParam;
import com.yuqi.mrrs.subject.entity.ReviewEntity;
import com.yuqi.mrrs.subject.service.ReviewService;
import com.yuqi.yuqi.annotation.TokenRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Api(value = "评论前端Api接口文档")
@RestController
@RequestMapping("/frontend/review")
public class ReviewFrontendController {
    @Autowired
    private ReviewService reviewService;

    /**
     * 列表
     */
    @ApiOperation(value = "评论列表",notes = "根据搜索参数分页查询某个subject的评论")
    @PostMapping("/list")
    public R list(@RequestBody ReviewSearchParam params){
        PageUtils page = reviewService.reviewList(params);
        return R.ok().put("list", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "评论信息",notes = "根据id查询评论信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ReviewEntity review = reviewService.getById(id);

        return R.ok().put("review", review);
    }

    /**
     * 新增评论
     */
    @ApiOperation(value = "新增评论",notes = "接收评论dto对象进行存储")
    @TokenRequired
    @PostMapping("/addReview")
    public R save(@RequestBody @Validated ReviewDto review){
        reviewService.addReview(review);
        return R.ok();
    }


}
