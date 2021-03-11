package com.yuqi.mrrs.subject.dto;

import lombok.Data;

/**
 * ES搜索subject参数类
 */
@Data
public class SearchParam {

    //影视类型
    private String subjectType;

    //全文检索关键字
    private String keyword;

    //标签数组
    private String[] label;

    //类型数组
    private String[] genre;

    //当前页码
    private Integer pageNum;

    //排序规则
    private String sort;


}
