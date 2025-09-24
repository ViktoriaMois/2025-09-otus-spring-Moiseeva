package com.example.springapp.service;

import com.example.springapp.domain.Exam;
import org.springframework.core.io.ClassPathResource;

import java.util.Scanner;

public interface ExamService {

    void print(ClassPathResource csvResource, Scanner sc);

    boolean checkAnswer(Exam exam, String answer);

    int result(boolean right, Exam exam, int points);
}
