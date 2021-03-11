package com.yuqi.mrrs.subject.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuqi.mrrs.subject.convert.GenreConvert;
import com.yuqi.mrrs.subject.dto.GenreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.Query;

import com.yuqi.mrrs.subject.dao.GenreDao;
import com.yuqi.mrrs.subject.entity.GenreEntity;
import com.yuqi.mrrs.subject.service.GenreService;


@Service("genreService")
public class GenreServiceImpl extends ServiceImpl<GenreDao, GenreEntity> implements GenreService {

    @Autowired
    GenreConvert genreConvert;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if(params.get("limit")==null){
            params.put("limit","10000");
        }
        IPage<GenreEntity> page = this.page(
                new Query<GenreEntity>().getPage(params),
                new QueryWrapper<GenreEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void add(GenreDto genreDto) {
        GenreEntity entity = genreConvert.dto2Entity(genreDto);
        //数据预处理
        entity.setDeleted(Boolean.FALSE);
        entity.setVersion(0);
        this.save(entity);
    }

    @Override
    public void update(GenreDto genreDto) {
        GenreEntity entity = genreConvert.dto2Entity(genreDto);
        //数据预处理
        entity.setDeleted(Boolean.FALSE);
        this.updateById(entity);

    }

    @Override
    public void movieAccountAdd(Long genreId) {
        this.update(new UpdateWrapper<GenreEntity>().setSql("movie_account=movie_account+1").eq("id",genreId));
    }

    @Override
    public void movieAccountSub(Long genreId) {
        this.update(new UpdateWrapper<GenreEntity>().setSql("movie_account=movie_account-1").eq("id",genreId));
    }


}