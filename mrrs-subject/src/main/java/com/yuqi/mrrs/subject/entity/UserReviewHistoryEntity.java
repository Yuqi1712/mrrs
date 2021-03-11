package com.yuqi.mrrs.subject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("mrrs_user_review_history")
public class UserReviewHistoryEntity {

    @TableId
    private Long userId;

    private String reviewList;

    private String likeList;

    private String dislikeList;
}
