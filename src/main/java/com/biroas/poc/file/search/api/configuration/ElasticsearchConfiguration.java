package com.biroas.poc.file.search.api.configuration;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticsearchConfiguration {

    @Value("${es.cluster.name}")
    private String clusterName;
    @Value("${es.host}")
    private String host;
    @Value("${es.port}")
    private int port;

    @Bean
    public Client getClient() throws UnknownHostException {
        Settings esSettings = Settings.settingsBuilder()
                .put("cluster.name", clusterName)
                .build();
        return TransportClient.builder()
                .settings(esSettings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

    }

    @Bean
    public ElasticsearchOperations getElasticsearchTemplate() throws UnknownHostException{
        return new ElasticsearchTemplate(getClient());
    }
}
