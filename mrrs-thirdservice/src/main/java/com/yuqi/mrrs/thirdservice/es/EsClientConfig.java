package com.yuqi.mrrs.thirdservice.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class EsClientConfig  extends AbstractElasticsearchConfiguration {

    @Value("${mrrs.elasticsearch.host}")
    private String host;

    @Value("${mrrs.elasticsearch.port}")
    private int port;


    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(host+":"+port)
                .build();


        return RestClients.create(clientConfiguration).rest();
    }
}
