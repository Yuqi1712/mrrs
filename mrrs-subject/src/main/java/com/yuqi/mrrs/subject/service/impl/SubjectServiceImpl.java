package com.yuqi.mrrs.subject.service.impl;

import com.yuqi.mrrs.subject.convert.GenreConvert;
import com.yuqi.mrrs.subject.convert.SubjectConvert;
import com.yuqi.mrrs.subject.convert.SubjectGenreRelationConvert;
import com.yuqi.mrrs.subject.dto.HotSubjectDto;
import com.yuqi.mrrs.subject.dto.SubjectDto;
import com.yuqi.mrrs.subject.dto.SubjectEsDto;
import com.yuqi.mrrs.subject.dto.SubjectGenreRelationDto;
import com.yuqi.mrrs.subject.entity.SubjectGenreRelationEntity;
import com.yuqi.mrrs.subject.entity.SubjectRatingEntity;
import com.yuqi.mrrs.subject.feign.ElasticSearchFeign;
import com.yuqi.mrrs.subject.service.GenreService;
import com.yuqi.mrrs.subject.service.SubjectGenreRelationService;
import com.yuqi.mrrs.subject.service.SubjectRatingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.Query;

import com.yuqi.mrrs.subject.dao.SubjectDao;
import com.yuqi.mrrs.subject.entity.SubjectEntity;
import com.yuqi.mrrs.subject.service.SubjectService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;


@Service("subjectService")
public class SubjectServiceImpl extends ServiceImpl<SubjectDao, SubjectEntity> implements SubjectService {

    @Autowired
    ElasticSearchFeign elasticSearchFeign;

    @Autowired
    SubjectConvert subjectConvert;

    @Autowired
    SubjectGenreRelationConvert subjectGenreRelationConvert;

    @Autowired
    SubjectGenreRelationService subjectGenreRelationService;

    @Autowired
    GenreService genreService;

    @Autowired
    SubjectRatingService subjectRatingService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${mrrs.subject.hot.redis.TTL}")
    Long redisHotTTL;

    @Value("${mrrs.subject.hot.redis.prefix}")
    String redisHotPrefix;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {


        IPage<SubjectEntity> page = this.page(
                new Query<SubjectEntity>().getPage(params),
                new QueryWrapper<SubjectEntity>()
        );

        List<SubjectDto> dtos = page.getRecords().stream().map(item -> {
            SubjectDto dto = subjectConvert.entity2Dto(item);

            //查询出subject类型属性进行组装
            List<SubjectGenreRelationEntity> genres = subjectGenreRelationService.getBaseMapper().selectList(new QueryWrapper<SubjectGenreRelationEntity>().eq("subject_id", item.getId()));
            List<SubjectGenreRelationDto> genreDtoList = genres.stream().map(rela -> {
                SubjectGenreRelationDto genreDto = subjectGenreRelationConvert.entity2Dto(rela);
                return genreDto;
            }).collect(Collectors.toList());

            //填充类型属性
            dto.setGenre(genreDtoList);

            //查询出影片评分信息进行填充
            SubjectRatingEntity subjectRatingEntity = subjectRatingService.getOne(new QueryWrapper<SubjectRatingEntity>().eq("subject_id", item.getId()));
            dto.setRating(subjectRatingEntity.getRatingScore());
            dto.setPlayVotes(subjectRatingEntity.getRatingCount());

            return dto;
        }).collect(Collectors.toList());

       return new PageUtils(dtos,(int)page.getTotal(),(int)page.getSize(),(int)page.getCurrent());

    }


    @Transactional
    @Override
    public void add(SubjectDto dto) {
        SubjectEntity entity = subjectConvert.dto2Entity(dto);
        //数据预处理
        entity.setDeleted(Boolean.FALSE);
        entity.setVersion(null);
        entity.setUpdateTime(null);
        entity.setRating(new BigDecimal(0));
        entity.setPlayVotes(0L);

        //step1 添加影片进库
        this.save(entity);

        //step2 添加影片类型关系入库
        List<SubjectGenreRelationDto> list = dto.getGenre();
        ArrayList<String> genres = new ArrayList<>();
        for (SubjectGenreRelationDto d:list){
            d.setSubjectId(entity.getId());
            genres.add(d.getGenreName());
            subjectGenreRelationService.save(subjectGenreRelationConvert.dto2Entity(d));
        }

        //step3 添加该影片评分入库
        SubjectRatingEntity ratingEntity = new SubjectRatingEntity();
        ratingEntity.setSubjectId(entity.getId());
        ratingEntity.setSubjectName(entity.getNameCn());
        subjectRatingService.save(ratingEntity);

        //step4 将影片存入elasticSearch
        SubjectEsDto esDto = new SubjectEsDto();
        esDto.setId(entity.getId());
        esDto.setCountry(entity.getCountry());
        esDto.setLabel(dto.getLabel());
        esDto.setNameCn(entity.getNameCn());
        esDto.setNameUs(entity.getNameUs());
        esDto.setPlayImg(entity.getPlayImg());
        esDto.setRating(entity.getRating());
        esDto.setGenre(genres.toArray(new String[0]));
        esDto.setSubjectType(entity.getSubjectType());
        esDto.setPlayVotes(entity.getPlayVotes());
        esDto.setDate(entity.getDatePublished().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        ArrayList<String> celebrity = new ArrayList<>();
        dto.getDirector().stream().forEach(item->{
            celebrity.add(item.getNameCn());
            celebrity.add(item.getNameUs());
        });

        dto.getPlayActor().stream().forEach(item->{
            celebrity.add(item.getNameCn());
            celebrity.add(item.getNameUs());
        });

        dto.getPlayWriter().stream().forEach(item->{
            celebrity.add(item.getNameCn());
            celebrity.add(item.getNameUs());
        });

        esDto.setCelebrity(celebrity.toArray(new String[0]));
        elasticSearchFeign.addSubject(esDto);

        //step5维护热度榜：将影片id+nameCn作为key存入redis   TTL:24H
        stringRedisTemplate.opsForZSet().add(redisHotPrefix+"::subjectZset",entity.getId()+"_"+entity.getNameCn(),0l);
        //stringRedisTemplate.opsForValue().set(redisHotPrefix+"::"+entity.getId().toString(),String.valueOf(0),redisHotTTL, TimeUnit.HOURS);

    }

    @Override
    public SubjectDto info(Long id) {
        SubjectEntity entity = this.getById(id);
        SubjectDto dto = subjectConvert.entity2Dto(entity);

        //查询出subject类型属性进行组装
        List<SubjectGenreRelationEntity> genres = subjectGenreRelationService.getBaseMapper().selectList(new QueryWrapper<SubjectGenreRelationEntity>().eq("subject_id", dto.getId()));
        List<SubjectGenreRelationDto> genreDtoList = genres.stream().map(item -> {
            SubjectGenreRelationDto genreDto = subjectGenreRelationConvert.entity2Dto(item);
            return genreDto;
        }).collect(Collectors.toList());
        dto.setGenre(genreDtoList);

        return dto;
    }

    @Override
    public List<HotSubjectDto> getTodayHotSubject() {
        Set<ZSetOperations.TypedTuple<String>> set = stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(redisHotPrefix + "::subjectZset", -1, 999999999, 0, 5);

        ArrayList<HotSubjectDto> list = new ArrayList<>();
        set.stream().forEach(item->{
            HotSubjectDto dto = new HotSubjectDto();
            String[] s = item.getValue().split("_");
            dto.setId(Long.parseLong(s[0]));
            dto.setNameCn(s[1]);
            dto.setScore(item.getScore());
            list.add(dto);
        });
        return list;
    }

}