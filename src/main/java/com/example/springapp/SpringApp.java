package com.example.springapp;

import com.example.springapp.service.ExamService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.util.Scanner;

@SpringBootApplication
@ComponentScan("com.example.springapp.config")
public class SpringApp {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        ApplicationContext context =
                SpringApplication.run(SpringApp.class, args);

        ExamService examService = context.getBean(ExamService.class);
        ClassPathResource csvResource = context.getBean(ClassPathResource.class);
        MessageSource msg = context.getBean(MessageSource.class);
        examService.print(csvResource, SC, msg);
    }
}
