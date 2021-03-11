package com.yuqi.mrrs.subject.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReviewDto {

    /**
     * 影片评论id
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 影片id
     */
    @NotNull
    private Long subject;
    /**
     * 评论者id
     */
    @NotNull
    private String userId;

    /**
     * 评论者名称
     */
    private String userName;
    /**
     * 评论内容
     */
    @NotBlank
    private String content;
    /**
     * 评论者头像
     */
    private String userIcon;
    /**
     * 评级  1,2,3,4,5 对应星级
     */
    @NotBlank
    private String rating;
    /**
     * 评论日期
     */
    private LocalDateTime reviewDate;
}
