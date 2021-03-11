package com.yuqi.mrrs.subject.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqi.mrrs.subject.dao.UserReviewHistoryDao;
import com.yuqi.mrrs.subject.entity.SubjectEntity;
import com.yuqi.mrrs.subject.entity.UserReviewHistoryEntity;
import com.yuqi.mrrs.subject.service.SubjectService;
import com.yuqi.mrrs.subject.service.UserReviewHistoryService;
import com.yuqi.yuqi.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service("userReviewHistoryService")
public class UserReviewHistoryImpl extends ServiceImpl<UserReviewHistoryDao, UserReviewHistoryEntity> implements UserReviewHistoryService {


    @Autowired
    SubjectService subjectService;

    @Override
    public List<SubjectEntity> getRecommendSubject(Long userId){

        //step1 查出该用户的评价影片历史记录
        UserReviewHistoryEntity reviewHistoryEntity = this.getById(userId);
        List<String> likeList = JSON.parseArray(reviewHistoryEntity.getLikeList(), String.class);
        List<String> reviewList = JSON.parseArray(reviewHistoryEntity.getReviewList(), String.class);

        if(likeList.size()==0){
            throw new CommonException("暂无推荐影片,请至少对一部影评给出大于三星的评价。");
        }

        //step2 查出用户最近评价过并给予好评(大于等于三星)的三部影片
        List<String> top3LikeSubject = likeList.subList((likeList.size() - 4) > 0 ? (likeList.size() - 4) : 0, likeList.size() );

        //step3 根据这三部影片向数据库中所有用户评价记录查询，只要评价记录包含这三部影片之一就返回该用户作为数据项之一
//        HashSet<Long> userSet = new HashSet<>();
//        for (String s: top3LikeSubject){
//            HashMap<String, Object> params = new HashMap<>();
//            params.put("subject",s);
//            params.put("user",userId);
//            List<Long> userList = this.baseMapper.getSimilarLikeUser(params);
//            userSet.addAll(userList);
//        }
        List<UserReviewHistoryEntity> entityList = this.baseMapper.selectList(new QueryWrapper<UserReviewHistoryEntity>().ne("user_id", userId));

        //step4 整理所有数据项包含的影片
        HashSet<Long> subjectSet = new HashSet<>();
        top3LikeSubject.stream().forEach(item->{
            subjectSet.add(Long.parseLong(item));
        });
        entityList.stream().forEach(item->{
            JSON.parseArray(item.getReviewList(),Long.class).stream().forEach(subject->{
                subjectSet.add(subject);
            });
        });
        //影片列表 下标即二维数组中影片所在位置
        ArrayList<Long> subjectList = new ArrayList<>();
        subjectList.addAll(subjectSet);

        HashSet<Integer>top3Index = new HashSet<Integer>();
        top3LikeSubject.stream().forEach(item->{
            top3Index.add(subjectList.indexOf(Long.parseLong(item)));
        });

        //step5 将影片ID/用户ID 制成二维数组
        Long[][]  arr= new Long[entityList.size()+1][subjectList.size()];

        top3LikeSubject.stream().forEach(item->{
             int i = subjectList.indexOf(Long.parseLong(item));
            System.out.println("index-------"+i);
            arr[0][i]=1l;
        });
        for(int ind=0;ind<entityList.size();ind++){
            final int index=ind;
            JSON.parseArray(entityList.get(ind).getReviewList(),Long.class).stream().forEach(subject->{
                int i = subjectList.indexOf(subject);
                arr[index+1][i]=1l;
            });
        }

        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr[i].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }

        //step6 计算top3LikeSubject的三项与列表中其他影片的项组成的二项的支持度(P(AB)) 置信度（P（B|A） 作用度（P(B|A)/P(B)）
        ConcurrentHashMap<String, Double> resultMap = new ConcurrentHashMap<>();
        for(String top3:top3LikeSubject){
            int currentIndex=subjectList.indexOf(Long.parseLong(top3));
            for(int i=0;i<subjectList.size();i++){
                if(top3Index.contains(i)){
                    continue;
                }else{
                    double countA=0;
                    double countB=0;
                    double countAB=0;
                    for(int j=0;j<arr.length;j++){
                        if(arr[j][currentIndex]!=null&&arr[j][currentIndex]==1){
                            countA++;
                        }
                        if(arr[j][i]!=null&&arr[j][i]==1){
                            countB++;
                        }
                        if(arr[j][currentIndex]!=null&&arr[j][i]!=null&&arr[j][currentIndex]==1&&arr[j][i]==1){
                            countAB++;
                        }
                    }
                    //计算支持度P(AB)
                    double sp= countAB / arr.length;
                    //计算P(A)
                    double pa=countA/arr.length;
                    //计算P(B)
                    double pb= countB/arr.length;
                    //计算置信度P(B/A)
                    double cf= sp/pa;
                    //计算作用度（P(B|A)/P(B)）
                    double lf= cf/pb;
                    //如果作用度大于1则将此频繁二项集放入结果集中
                    if(lf>=1){
                        resultMap.put(top3+"-"+subjectList.get(i),lf);
                    }
                }
            }
        }

        //过滤结果集中影片已经被用户评价过的影片
        Iterator<String> iterator = resultMap.keySet().iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            String[] split = next.split("-");
            log.info(next+"   "+resultMap.get(next));
            if(reviewList.indexOf(split[1])!=-1){
               resultMap.remove(next);
            }
        }

        //整合所有lf>1的影片，并进行去重
        HashSet<String> resultSet = new HashSet<>();
        Iterator<String> iterator1 = resultMap.keySet().iterator();
        while (iterator1.hasNext()){
           resultSet.add(iterator1.next());
        }

        //如果结果集为0

        List<SubjectEntity> list = subjectService.list(new QueryWrapper<SubjectEntity>().in("id", resultSet));






        return list;


    }
}
