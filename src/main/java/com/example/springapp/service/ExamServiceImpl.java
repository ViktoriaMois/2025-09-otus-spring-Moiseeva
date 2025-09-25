package com.example.springapp.service;

import com.example.springapp.dao.ExamDao;
import com.example.springapp.domain.Exam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements ExamService {
    private final ExamDao dao;

    @Value("${exam.minPoints}")
    private int minPoints;

    @Value("${exam.maxPoints}")
    private int maxPoints;

    @Value("${exam.pointsPerQuestion}")
    private int pointsPerQuestion;

    @Override
    public void print(ClassPathResource csvResource, Scanner sc, MessageSource msg) {
        String lang = "";
        System.out.println("ENG/RU");
        if (sc.nextLine().equalsIgnoreCase("ru")) {
            lang = "ru-RU";
        }

        System.out.println(msg.getMessage("main.student-name.string", null, Locale.forLanguageTag(lang)));
        String studentName = sc.nextLine();

        int points = 0;
        List<Exam> examList = dao.read(csvResource, sc, msg, lang);
        for (int i = 0; i < examList.size(); i++) {
            System.out.println(msg.getMessage("main.question.string[" + i + "]", null, Locale.forLanguageTag(lang)) +
                    "\n" + msg.getMessage("main.answers.string", null, Locale.forLanguageTag(lang)));
            String studentAnswer = sc.nextLine().toLowerCase();
            boolean right = checkAnswer(examList.get(i), studentAnswer);
            points = result(right, examList.get(i), points, pointsPerQuestion);
        }

        sc.close();
        System.out.println(msg.getMessage("main.score.string", new Object[]{studentName, points, maxPoints}, Locale.forLanguageTag(lang)));
        if (points > minPoints) {
            System.out.println(msg.getMessage("main.success.string", null, Locale.forLanguageTag(lang)));
        } else {
            System.out.println(msg.getMessage("main.failure.string", null, Locale.forLanguageTag(lang)));
        }
    }

    @Override
    public boolean checkAnswer(Exam exam, String answer) {
        return exam.getRightAnswer().contains(answer);
    }

    @Override
    public int result(boolean right, Exam exam, int points, int pointsPerQuestion) {
        if (right) {
            points += pointsPerQuestion;
        }
        return points;
    }
}
