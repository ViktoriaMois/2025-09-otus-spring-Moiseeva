package com.example.springapp;

import com.example.springapp.service.ExamService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.springapp.config")
public class SpringApp {

    public static void main(String[] args) {
        ApplicationContext context =
                SpringApplication.run(SpringApp.class, args);

        ExamService examService = context.getBean(ExamService.class);
        examService.print();
    }
}
