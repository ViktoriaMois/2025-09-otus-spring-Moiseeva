package com.example.springapp.service;

import com.example.springapp.domain.Exam;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;

import java.util.Scanner;

public interface ExamService {

    void print();

    boolean checkAnswer(Exam exam, String answer);

    void result(boolean right);

    void out(String message, Object[] obj);

    String readString(Scanner scanner);

    int getPoints();
}
