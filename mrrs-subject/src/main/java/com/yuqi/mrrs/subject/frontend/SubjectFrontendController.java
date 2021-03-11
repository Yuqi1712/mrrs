package com.yuqi.mrrs.subject.frontend;

import com.yuqi.mrrs.common.utils.R;
import com.yuqi.mrrs.subject.convert.SubjectConvert;
import com.yuqi.mrrs.subject.dto.HotSubjectDto;
import com.yuqi.mrrs.subject.dto.SearchParam;
import com.yuqi.mrrs.subject.dto.SubjectDto;
import com.yuqi.mrrs.subject.dto.SubjectEsRespDto;
import com.yuqi.mrrs.subject.entity.SubjectEntity;
import com.yuqi.mrrs.subject.entity.SubjectRatingEntity;
import com.yuqi.mrrs.subject.feign.ElasticSearchFeign;
import com.yuqi.mrrs.subject.service.SubjectRatingService;
import com.yuqi.mrrs.subject.service.SubjectService;
import com.yuqi.mrrs.subject.service.UserReviewHistoryService;
import com.yuqi.yuqi.annotation.TokenRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frontend/subject")
public class SubjectFrontendController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectConvert subjectConvert;

    @Autowired
    private SubjectRatingService subjectRatingService;

    @Autowired
    private ElasticSearchFeign elasticSearchFeign;

    @Autowired
    UserReviewHistoryService userReviewHistoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${mrrs.subject.hot.redis.prefix}")
    String prefix;

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SubjectDto subject = subjectService.info(id);
        //热度+1
        stringRedisTemplate.opsForZSet().incrementScore(prefix+"::subjectZset",subject.getId()+"_"+subject.getNameCn(),1l);
        return R.ok().put("subject", subject);
    }

    /**
     * subject列表
     */
    @PostMapping("/getSubjectList")
    public R getSubjectList(@RequestBody SearchParam searchParam){
        SubjectEsRespDto esRespDto = elasticSearchFeign.getSubjectList(searchParam);
        return R.ok().put("list", esRespDto);
    }

    /**
     * 评分最高的5个subject
     */
    @GetMapping("/getBestFiveSubject")
    public R getBestFiveSubject(@RequestParam String name){
        SubjectEsRespDto esRespDto = elasticSearchFeign.bestFiveSubject(name);
        return R.ok().put("list", esRespDto);
    }

    /**
     * 时间最近的五个subject
     */
    @GetMapping("/getLatestFiveSubject")
    public R getLatestFiveSubject(@RequestParam String name){
        SubjectEsRespDto esRespDto = elasticSearchFeign.latestFiveSubject(name);
        return R.ok().put("list", esRespDto);
    }

    /**
     * 获取影视排行榜top100
     */
    @PostMapping("/getTopSubject")
    public R getTopSubject(@RequestBody SearchParam searchParam){
        SubjectEsRespDto esRespDto = elasticSearchFeign.getTopSubject(searchParam);
        return R.ok().put("list", esRespDto);
    }

    /**
     * 获取今日最热top5影视
     */
    @GetMapping("/getTodayHotSubject")
    public R getTodayHotSubject(){
        List<HotSubjectDto>  list= subjectService.getTodayHotSubject();
        return R.ok().put("list", list);
    }

    /**
     * 获取subject评分信息
     * @param subject
     * @return
     */
    @GetMapping("/getSubjectRating")
    public R getSubjectRating(@RequestParam Long subject){
        SubjectRatingEntity rating = subjectRatingService.getSubjectRating(subject);
        return R.ok().put("data",rating);
    }

    @GetMapping("/getRecentHot")
    public R getRecentHot(@RequestParam String subjectType){
        SubjectEsRespDto respDto = elasticSearchFeign.getRecentHot(subjectType);
        return R.ok().put("list",respDto.getSubjectList());
    }

    //猜你喜欢
    @TokenRequired
    @GetMapping("/getUserLike")
    public R getUserLike(@RequestParam Long userId){
        List<SubjectEntity> recommendSubject = userReviewHistoryService.getRecommendSubject(userId);
        return R.ok().put("like",recommendSubject);
    }

}
