package com.example.springapp.service;

import com.example.springapp.dao.ExamDao;
import com.example.springapp.dao.ExamDaoImpl;
import com.example.springapp.domain.Exam;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExamServiceImplTest {
    private ExamDao dao = new ExamDaoImpl();
    private ExamService examService = new ExamServiceImpl(dao);
    private Exam exam;
    private ClassPathResource csv = new ClassPathResource("/test.csv");

    @ParameterizedTest
    @ValueSource(strings = {"1Test\n no yes yes no yes"
            ,"2Test\n no no no no no"
            ,"3Test\n yes yes yes yes yes"
            ,"4Test\n yes no no yes no"})
    void print(String userInput) {
        Scanner sc = new Scanner(userInput);
        examService.print(csv, sc);
    }

    @Test
    void checkAnswer() {
        exam = new Exam("2+2", "type your answer:", "4", 10);

        assertTrue(examService.checkAnswer(exam, "4"));
        assertFalse(examService.checkAnswer(exam, "5"));
    }

    @Test
    void result() {
        exam = new Exam("2+2", "type your answer:", "4", 10);
        int points = 0;

        // Wrong answer equals 0 points
        points = examService.result(false, exam, points);
        assertEquals(0, points);

        // Wrong answer equals +10 points
        points = examService.result(true, exam, points);
        assertEquals(10, points);

        points = examService.result(false, exam, points);
        assertEquals(10, points);

        points = examService.result(true, exam, points);
        assertEquals(20, points);
    }
}