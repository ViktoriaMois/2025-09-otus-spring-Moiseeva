package com.example.springapp.service;

import com.example.springapp.dao.ExamDao;
import com.example.springapp.domain.Exam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements ExamService {
    private final ExamDao dao;

    @Override
    public void print(ClassPathResource csvResource, Scanner sc) {
        int points = 0;
        int totalPerExam = 0;

        System.out.println("Please, type your name and surname: ");
        String studentName = sc.nextLine();

        List<Exam> examList = dao.read(csvResource);
        for (Exam e : examList) {
            System.out.println(e.getQuestion() + "\n" + e.getAnswer());
            String studentAnswer = sc.next().toLowerCase();
            boolean right = checkAnswer(e, studentAnswer);
            points = result(right, e, points);
            totalPerExam += e.getPoints();

        }

        sc.close();
        System.out.println("Your final score, " + studentName + ", : " + points + "/" + totalPerExam);
        if (points >= 60) {
            System.out.println("You've passed the test");
        } else {
            System.out.println("You haven't passed the test");
        }
    }

    @Override
    public boolean checkAnswer(Exam exam, String answer) {
        return answer.equals(exam.getRightAnswer());
    }

    @Override
    public int result(boolean right, Exam exam, int points) {
        if (right) {
            points += exam.getPoints();
        }
        return points;
    }
}
