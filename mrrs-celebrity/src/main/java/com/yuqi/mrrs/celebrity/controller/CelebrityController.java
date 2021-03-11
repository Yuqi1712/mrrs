package com.yuqi.mrrs.celebrity.controller;

import java.util.Arrays;
import java.util.Map;

import com.yuqi.mrrs.celebrity.convert.CelebrityConvert;
import com.yuqi.mrrs.celebrity.dto.CelebrityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yuqi.mrrs.celebrity.entity.CelebrityEntity;
import com.yuqi.mrrs.celebrity.service.CelebrityService;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.R;



/**
 * 
 *
 * @author Yuqi
 * @email ${email}
 * @date 2021-01-22 12:28:13
 */
@RestController
@RequestMapping("/celebrity")
public class CelebrityController {
    @Autowired
    private CelebrityService celebrityService;

    @Autowired
    private CelebrityConvert celebrityConvert;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = celebrityService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CelebrityEntity celebrity = celebrityService.getById(id);
        CelebrityDto dto = celebrityConvert.entity2Dto(celebrity);
        return R.ok().put("celebrity", dto);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CelebrityEntity celebrity){
		celebrityService.save(celebrity);
        return R.ok();
    }

    /**
     * 添加名人
     */
    @PostMapping("/save")
    public R add(@RequestBody CelebrityDto celebrityDto){
        System.out.println(celebrityDto);
        celebrityService.add(celebrityDto);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CelebrityDto celebrityDto){
		celebrityService.update(celebrityDto);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		celebrityService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
