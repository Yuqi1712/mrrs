package com.yuqi.mrrs.subject.controller;

import com.yuqi.mrrs.subject.dao.UserReviewHistoryDao;
import com.yuqi.mrrs.subject.entity.UserReviewHistoryEntity;
import io.renren.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviewHistory")
public class UserReviewHistoryController {
    @Autowired
    UserReviewHistoryDao dao;

    @GetMapping("/addUser2ReviewHistory")
    public R  addUser2ReviewHistory(@RequestParam Long userId){
        UserReviewHistoryEntity historyEntity = new UserReviewHistoryEntity();
        historyEntity.setUserId(userId);
        historyEntity.setReviewList("[]");
        historyEntity.setLikeList("[]");
        historyEntity.setDislikeList("[]");
        dao.insert(historyEntity);
        return R.ok();
    }
}
