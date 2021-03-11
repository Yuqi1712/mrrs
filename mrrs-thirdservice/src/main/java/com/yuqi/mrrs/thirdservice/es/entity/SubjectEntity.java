package com.yuqi.mrrs.thirdservice.es.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document(indexName = "mrrs_subject")
public class SubjectEntity {

    @Id
    @Field(type = FieldType.Long, store = true)
    private Long id;

    @Field(type = FieldType.Text, store = true)
    private String nameCn;

    @Field(type = FieldType.Text, store = true)
    private String nameUs;

    @Field(type = FieldType.Keyword, store = true)
    private String[] label;

    @Field(type = FieldType.Keyword, store = true)
    private String[] genre;

    @Field(type = FieldType.Text, store = true)
    private String[] celebrity;

    @Field(type = FieldType.Double, store = true)
    private BigDecimal rating;

    @Field(type = FieldType.Long, store = true)
    private Long playVotes;

    @Field(type = FieldType.Keyword, store = true)
    private String playImg;

    @Field(type = FieldType.Keyword, store = true)
    private String country;

    @Field(type = FieldType.Keyword, store = true)
    private String subjectType;

    @Field(type = FieldType.Date, store = true, pattern = "yyyy-MM-dd",format = DateFormat.basic_date)
    private String date;

}
