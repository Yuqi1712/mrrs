package com.yuqi.mrrs.thirdservice.service;

import com.alibaba.fastjson.JSON;
import com.yuqi.mrrs.thirdservice.dto.SubjectEsRespDto;
import com.yuqi.mrrs.thirdservice.es.SearchParam;
import com.yuqi.mrrs.thirdservice.es.entity.SubjectEntity;
import com.yuqi.mrrs.thirdservice.es.repository.SubjectRepository;
import com.yuqi.yuqi.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EsSubjectService {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    SubjectRepository subjectRepository;

    public void addSubject(SubjectEntity subject){
        subjectRepository.save(subject);
//        String s = JSON.toJSONString(dto);
//        IndexRequest request = new IndexRequest("mrrs_subject");
//        request.source(s,XContentType.JSON);
//        try {
//            client.index(request, RequestOptions.DEFAULT);
//        }catch (IOException e){
//            throw new CommonException("Es储存subject失败");
//        }
    }

    public SubjectEsRespDto getSubjectList(SearchParam searchParam){
        SearchRequest request = searchRequestBuildByParam(searchParam);
        SearchResponse response=null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
            throw new CommonException("ES检索出错，请联系管理员");
        }
        SubjectEsRespDto respDto = searchRespBuildByResponse(response);
        return respDto;
    }

    //通过searchParam构建SearchRequest
    public SearchRequest searchRequestBuildByParam(SearchParam searchParam){
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //影片类型匹配
        if(!StringUtils.isEmpty(searchParam.getSubjectType())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("subjectType",searchParam.getSubjectType()));
        }

        //关键字模糊匹配 (针对名人中/英名、影片中/英名)
        if(!StringUtils.isEmpty(searchParam.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchParam.getKeyword(),"nameCn","nameUs","celebrity"));
        }

        //类型匹配
        if(searchParam.getGenre()!=null&&searchParam.getGenre().length>0){
            for(String genre: searchParam.getGenre()) {
                boolQueryBuilder.must(QueryBuilders.matchQuery("genre", genre));
            }
        }

        //标签匹配
        if(searchParam.getLabel()!=null&&searchParam.getLabel().length>0){
            for(String label: searchParam.getLabel()) {
                boolQueryBuilder.must(QueryBuilders.matchQuery("label", label));
            }
        }

        builder.query(boolQueryBuilder);

        //结果排序
        if(!StringUtils.isEmpty(searchParam.getSort())){
            builder.sort(searchParam.getSort().equals("date")?"date":"rating",SortOrder.DESC);
        }

        //类型聚合分析
        TermsAggregationBuilder genreAggs = AggregationBuilders.terms("genre_aggs").field("genre");
        builder.aggregation(genreAggs);

        //标签聚合分析
        TermsAggregationBuilder labelAggs = AggregationBuilders.terms("label_aggs").field("label");
        builder.aggregation(labelAggs);

        SearchRequest searchRequest = new SearchRequest(new String[]{"mrrs_subject"},builder);

        return searchRequest;

    }

    public SubjectEsRespDto searchRespBuildByResponse(SearchResponse response){
        SubjectEsRespDto respDto = new SubjectEsRespDto();
        SearchHits hits = response.getHits();
        respDto.setTotal(hits.getTotalHits().value);
        ArrayList<SubjectEntity> subjectEntities = new ArrayList<>();
        //组装subject数组
        for(SearchHit hit:hits.getHits()){
            SubjectEntity subjectEntity = JSON.parseObject(hit.getSourceAsString(), SubjectEntity.class);
            subjectEntities.add(subjectEntity);
        }
        respDto.setSubjectList(subjectEntities);
        //组装标签聚合信息
        ArrayList<String> label = new ArrayList<>();
        Aggregations aggregations = response.getAggregations();
        ParsedStringTerms labelAggs = aggregations.get("label_aggs");
        for(Terms.Bucket bucket:labelAggs.getBuckets()){
           label.add(bucket.getKeyAsString());
        }
        respDto.setLabelList(label);

        //组装类型聚合信息
        ArrayList<String> genre = new ArrayList<>();
        ParsedStringTerms genreAggs = aggregations.get("genre_aggs");
        for(Terms.Bucket bucket:genreAggs.getBuckets()){
            genre.add(bucket.getKeyAsString());
        }
        respDto.setGenreList(genre);

        return respDto;
    }

    public SubjectEsRespDto getBestFiveSubject(String name) {

        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        if(!StringUtils.isEmpty(name)){
            queryBuilder.must(QueryBuilders.matchQuery("celebrity",name));
        }
        builder.query(queryBuilder);
        builder.sort("rating", SortOrder.DESC);
        builder.size(5);
        SearchRequest request = new SearchRequest(new String[]{"mrrs_subject"},builder);

        SearchResponse response=null;
        try {
            response=client.search(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ES检索出错");
        }

        SubjectEsRespDto respDto = new SubjectEsRespDto();
        SearchHits hits = response.getHits();
        respDto.setTotal(hits.getTotalHits().value);
        ArrayList<SubjectEntity> subjectEntities = new ArrayList<>();
        //组装subject数组
        for(SearchHit hit:hits.getHits()){
            SubjectEntity subjectEntity = JSON.parseObject(hit.getSourceAsString(), SubjectEntity.class);
            subjectEntities.add(subjectEntity);
        }
        respDto.setSubjectList(subjectEntities);
        return respDto;
    }

    public SubjectEsRespDto getLatestFiveSubject(String name) {

        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        if(!StringUtils.isEmpty(name)){
            queryBuilder.must(QueryBuilders.matchQuery("celebrity",name));
        }
        builder.query(queryBuilder);
        builder.sort("date", SortOrder.DESC);
        builder.size(5);
        SearchRequest request = new SearchRequest(new String[]{"mrrs_subject"},builder);

        SearchResponse response=null;
        try {
            response=client.search(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ES检索出错");
        }

        SubjectEsRespDto respDto = new SubjectEsRespDto();
        SearchHits hits = response.getHits();
        respDto.setTotal(hits.getTotalHits().value);
        ArrayList<SubjectEntity> subjectEntities = new ArrayList<>();
        //组装subject数组
        for(SearchHit hit:hits.getHits()){
            SubjectEntity subjectEntity = JSON.parseObject(hit.getSourceAsString(), SubjectEntity.class);
            subjectEntities.add(subjectEntity);
        }
        respDto.setSubjectList(subjectEntities);
        return respDto;
    }

    public SubjectEsRespDto getTopSubject(SearchParam searchParam) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        if(StringUtils.isEmpty(searchParam.getSubjectType())){
            log.error("获取影视排行榜必须携带影视类型--subjectType");
            throw new CommonException("获取影视排行榜必须携带影视类型");
        }

        queryBuilder.must(QueryBuilders.matchQuery("subjectType",searchParam.getSubjectType()));
        builder.query(queryBuilder);

        builder.size(10);

        builder.sort("rating",SortOrder.DESC);

        if(searchParam.getPageNum()!=null) {
            builder.from((searchParam.getPageNum()-1)*10);
        }

        SearchRequest request = new SearchRequest(new String[]{"mrrs_subject"},builder);

        SearchResponse response=null;
        try {
            response=client.search(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ES检索出错");
        }

        SubjectEsRespDto respDto = new SubjectEsRespDto();
        SearchHits hits = response.getHits();
        respDto.setTotal(hits.getTotalHits().value);
        ArrayList<SubjectEntity> subjectEntities = new ArrayList<>();
        //组装subject数组
        for(SearchHit hit:hits.getHits()){
            SubjectEntity subjectEntity = JSON.parseObject(hit.getSourceAsString(), SubjectEntity.class);
            subjectEntities.add(subjectEntity);
        }
        respDto.setSubjectList(subjectEntities);
        return respDto;

    }

    public void updateSubject(SubjectEntity subjectEntity) {
        //ES不能修改文档、采取先查后覆盖操作

        //step1:先查询出存在ES中的数据
        Optional<SubjectEntity> option = subjectRepository.findById(subjectEntity.getId());
        SubjectEntity esEntity = option.get();

        //step2:更新数据重新添加到ES
        esEntity.setPlayVotes(subjectEntity.getPlayVotes());
        esEntity.setRating(subjectEntity.getRating());
        subjectRepository.save(esEntity);

    }

    public SubjectEsRespDto getRecentHotMovie(String subjectType) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(QueryBuilders.matchQuery("subjectType",subjectType));

        builder.query(queryBuilder);
        builder.sort("date",SortOrder.DESC);
        builder.sort("rating",SortOrder.DESC);
        builder.from(0);
        builder.size(6);
        SearchRequest request = new SearchRequest(new String[]{"mrrs_subject"}, builder);
        SearchResponse response=null;
        try {
            response=client.search(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<SubjectEntity> list = new ArrayList<>();
        for(SearchHit hit:hits){
            SubjectEntity entity = JSON.parseObject(hit.getSourceAsString(), SubjectEntity.class);
            list.add(entity);
        }
        SubjectEsRespDto respDto = new SubjectEsRespDto();
        respDto.setSubjectList(list);
        return respDto;
    }
}
