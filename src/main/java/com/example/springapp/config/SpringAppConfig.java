package com.example.springapp.config;

import com.example.springapp.dao.ExamDao;
import com.example.springapp.dao.ExamDaoImpl;
import com.example.springapp.service.ExamService;
import com.example.springapp.service.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ConfigurationProperties(prefix = "csv")
public class SpringAppConfig {

    @Value("${csv.path}")
    private String csv;

    @Bean
    public ClassPathResource csvResource() {
        return new ClassPathResource(csv);
    }

    @Bean
    public ExamDao examDao() {
        return new ExamDaoImpl();
    }

    @Bean
    public ExamService examService() {
        return new ExamServiceImpl(examDao());
    }
}
