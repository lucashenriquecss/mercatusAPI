package com.example.mercatusAPI.infra.databases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(
    basePackages = "com.example.repository.mongo",
    mongoTemplateRef = "mongoTemplate"
)
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("${MONGO_URI}");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "${MONGO_DATABASE}");
    }
}

