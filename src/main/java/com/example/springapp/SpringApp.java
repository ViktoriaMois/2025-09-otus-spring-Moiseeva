package com.example.springapp;

import com.example.springapp.service.ExamService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.util.Scanner;

@SpringBootApplication
@ComponentScan("com.example.springapp.config")
public class SpringApp {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.register(SpringApp.class);
        context.refresh();

        ExamService examService = context.getBean(ExamService.class);
        ClassPathResource csvResource = context.getBean(ClassPathResource.class);
        examService.print(csvResource, SC);
    }
}
