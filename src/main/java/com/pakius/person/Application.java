package com.pakius.person;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.hazelcast.spring.mongodb.MongoMapStore;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableAutoConfiguration
@ImportResource(value ="classpath:hazelcast.xml")
@ComponentScan(basePackages = {"com.pakius.person"})
public class Application
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);


    @Autowired
    MongoTemplate mongoTemplate;

    @Bean
    public MongoMapStore mongoMapStore(){
        MongoMapStore m = new MongoMapStore();
        m.setMongoTemplate(mongoTemplate);
        return m;
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}
