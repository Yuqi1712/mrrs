package com.yuqi.mrrs.subject.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuqi.mrrs.common.utils.R;
import com.yuqi.mrrs.subject.dao.UserReviewHistoryDao;
import com.yuqi.mrrs.subject.dto.ReviewDto;
import com.yuqi.mrrs.subject.dto.ReviewSearchParam;
import com.yuqi.mrrs.subject.dto.SubjectEsDto;
import com.yuqi.mrrs.subject.dto.UserDto;
import com.yuqi.mrrs.subject.entity.SubjectEntity;
import com.yuqi.mrrs.subject.entity.SubjectRatingEntity;
import com.yuqi.mrrs.subject.entity.UserReviewHistoryEntity;
import com.yuqi.mrrs.subject.feign.CelebrityFeign;
import com.yuqi.mrrs.subject.feign.ElasticSearchFeign;
import com.yuqi.mrrs.subject.feign.UserFeign;
import com.yuqi.mrrs.subject.service.SubjectRatingService;
import com.yuqi.mrrs.subject.service.SubjectService;
import com.yuqi.yuqi.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqi.mrrs.common.utils.PageUtils;
import com.yuqi.mrrs.common.utils.Query;

import com.yuqi.mrrs.subject.dao.ReviewDao;
import com.yuqi.mrrs.subject.entity.ReviewEntity;
import com.yuqi.mrrs.subject.service.ReviewService;
import org.springframework.transaction.annotation.Transactional;


@Service("reviewService")
public class ReviewServiceImpl extends ServiceImpl<ReviewDao, ReviewEntity> implements ReviewService {

    @Autowired
    SubjectService subjectService;

    @Autowired
    SubjectRatingService subjectRatingService;

    @Autowired
    UserReviewHistoryDao userReviewHistoryDao;

    @Autowired
    ElasticSearchFeign elasticSearchFeign;

    @Autowired
    UserFeign userFeign;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${mrrs.subject.review.redis.prefix}")
    String prefix;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<ReviewEntity> reviewEntityQueryWrapper = new QueryWrapper<>();
        Object subject = params.get("subject");
        if(null!=subject){
            reviewEntityQueryWrapper.eq("subject",Long.parseLong(subject.toString()));
        }
        IPage<ReviewEntity> page = this.page(
                new Query<ReviewEntity>().getPage(params),
                reviewEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils reviewList(ReviewSearchParam params) {
        QueryWrapper<ReviewEntity> reviewEntityQueryWrapper = new QueryWrapper<>();
        Long subject = params.getSubject();
        if(null!=subject){
            reviewEntityQueryWrapper.eq("subject",Long.parseLong(subject.toString()));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",params.getPage());
        map.put("limit",params.getLimit());
        IPage<ReviewEntity> page = this.page(
                new Query<ReviewEntity>().getPage(map),
                reviewEntityQueryWrapper
        );

        List<ReviewDto> reviewDtos = page.getRecords().stream().map(item -> {
            ReviewDto dto = new ReviewDto();
            BeanUtils.copyProperties(item, dto);
            UserDto user = userFeign.info(Long.parseLong(item.getUserId()));
            dto.setUserIcon(user.getUserIcon());
            dto.setUserName(user.getUserName());
            return dto;
        }).collect(Collectors.toList());

        return new PageUtils(reviewDtos,(int)page.getTotal(),(int)page.getSize(),(int)page.getCurrent());
    }

    @Transactional
    @Override
    public void addReview(ReviewDto review) {

        //step0: 检查该用户是否已经评论过该影片
        Boolean member = stringRedisTemplate.opsForSet().isMember(prefix + "::" + review.getSubject(), review.getUserId());
        if(member){
            throw new CommonException("您已经评价过该影片，请勿重复评价。");
        }else {


            //step1:评论入库
            ReviewEntity entity = new ReviewEntity();
            BeanUtils.copyProperties(review, entity);
            this.baseMapper.insert(entity);

            //step2：Mysql中subject更新评分
            SubjectEntity subjectEntity = subjectService.getById(entity.getSubject());
            BigDecimal totalScore = subjectEntity.getRating().multiply(new BigDecimal(subjectEntity.getPlayVotes()));
            subjectEntity.setPlayVotes(subjectEntity.getPlayVotes() + 1L);
            Long playVotes = subjectEntity.getPlayVotes();
            //结果保留一位小数(四舍五入)
            BigDecimal rating = totalScore.add(new BigDecimal(entity.getRating())).divide(new BigDecimal(playVotes), RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP);
            subjectEntity.setRating(rating);
            subjectService.updateById(subjectEntity);

            //step3: Mysql中subject_rating更新评分
            SubjectRatingEntity ratingEntity = subjectRatingService.getOne(new QueryWrapper<SubjectRatingEntity>().eq("subject_id", entity.getSubject()));
            ratingEntity.setRatingScore(rating);
            ratingEntity.setRatingCount(playVotes);
            switch (review.getRating()) {
                case "1":
                    ratingEntity.setRatingE(ratingEntity.getRatingE() + 1);
                    break;
                case "2":
                    ratingEntity.setRatingD(ratingEntity.getRatingD() + 1);
                    break;
                case "3":
                    ratingEntity.setRatingC(ratingEntity.getRatingC() + 1);
                    break;
                case "4":
                    ratingEntity.setRatingB(ratingEntity.getRatingB() + 1);
                    break;
                case "5":
                    ratingEntity.setRatingA(ratingEntity.getRatingA() + 1);
                    break;
            }
            subjectRatingService.updateById(ratingEntity);


            //step4: Es中Subject更新评分
            SubjectEsDto esDto = new SubjectEsDto();
            BeanUtils.copyProperties(subjectEntity, esDto);
            elasticSearchFeign.updateSubject(esDto);

            //step5: 维护影片评价与用户关系
            stringRedisTemplate.opsForSet().add(prefix + "::" + review.getSubject(), review.getUserId());

            //step6: 更新用户评价影片历史表
            UserReviewHistoryEntity historyEntity = userReviewHistoryDao.selectOne(new QueryWrapper<UserReviewHistoryEntity>().eq("user_id", review.getUserId()));
            List<String> history = JSON.parseArray(historyEntity.getReviewList(), String.class);
            List<String> likeList = JSON.parseArray(historyEntity.getLikeList(), String.class);
            List<String> dislikeList = JSON.parseArray(historyEntity.getDislikeList(), String.class);
            history.add(review.getSubject().toString());
            if (Integer.parseInt(review.getRating()) >= 3) {
                likeList.add(review.getSubject().toString());
            } else {
                dislikeList.add(review.getSubject().toString());
            }
            historyEntity.setReviewList(JSON.toJSONString(history));
            historyEntity.setLikeList(JSON.toJSONString(likeList));
            historyEntity.setDislikeList(JSON.toJSONString(dislikeList));
            userReviewHistoryDao.updateById(historyEntity);
        }
    }

}