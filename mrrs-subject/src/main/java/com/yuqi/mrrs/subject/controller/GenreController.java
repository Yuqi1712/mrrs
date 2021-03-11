package com.yuqi.mrrs.subject.controller;

import java.util.Arrays;
import java.util.Map;

import com.yuqi.mrrs.subject.dto.GenreDto;
import com.yuqi.yuqi.vaildGroup.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuqi.mrrs.subject.entity.GenreEntity;
import com.yuqi.mrrs.subject.service.GenreService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.R;



/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-26 11:35:43
 */
@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = genreService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		GenreEntity genre = genreService.getById(id);

        return R.ok().put("genre", genre);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GenreDto genreDto){
		genreService.add(genreDto);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody @Validated({UpdateGroup.class}) GenreDto genreDto){
		genreService.update(genreDto);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		genreService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
