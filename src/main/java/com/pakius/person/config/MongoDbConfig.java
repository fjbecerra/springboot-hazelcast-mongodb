package com.pakius.person.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class MongoDbConfig extends AbstractMongoConfiguration {

    @Value("${mongo.url}")
    private String url;

    @Value("${mongo.db}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(url));
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.pakius.person.model";
    }



}