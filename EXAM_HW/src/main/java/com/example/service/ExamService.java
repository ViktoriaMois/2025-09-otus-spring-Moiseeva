package com.example.service;

import com.example.domain.Exam;
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
