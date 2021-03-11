package com.yuqi.mrrs.thirdservice.es.repository;

import com.yuqi.mrrs.thirdservice.es.entity.SubjectEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends ElasticsearchRepository<SubjectEntity,Long> {

}
