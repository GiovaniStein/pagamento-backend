package com.example.pagamento_backend.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.client.RestClient;

@Configuration
public class Config {

    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name("transaction-notification")
                .build();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

}
