package com.gmail.bishoybasily.cdc.model.elasticsearch.reporitory;

import com.gmail.bishoybasily.cdc.model.elasticsearch.entity.ElasticSearchUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepositoryUsers extends ElasticsearchRepository<ElasticSearchUser, String> {
    
}
