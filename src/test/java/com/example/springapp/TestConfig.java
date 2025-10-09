package com.example.springapp;

import com.example.springapp.dao.ExamDao;
import com.example.springapp.dao.ExamDaoImpl;
import com.example.springapp.service.ExamService;
import com.example.springapp.service.ExamServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

@TestConfiguration
public class TestConfig {

    @Bean(name = "testCsvResource")
    public ClassPathResource csvResource() {
        return new ClassPathResource("test.csv");
    }

    @Bean(name = "testMessageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean(name = "testExamDao")
    public ExamDao examDao() {
        return new ExamDaoImpl();
    }

    @Bean(name = "testExamService")
    public ExamService examService() {
        return new ExamServiceImpl(examDao(), messageSource(),csvResource());
    }
}
