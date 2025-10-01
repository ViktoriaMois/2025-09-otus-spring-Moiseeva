package com.example.springapp.service;

import com.example.springapp.dao.ExamDao;
import com.example.springapp.dao.ExamDaoImpl;
import com.example.springapp.domain.Exam;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.shell.Shell;

import java.util.Scanner;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExamServiceImplTest {
    private final ExamDao DAO = new ExamDaoImpl();
    private final ExamService EXAM_SERVICE = new ExamServiceImpl(DAO);
    private final ClassPathResource CSV = new ClassPathResource("/test.csv");
    private Exam exam;

    @MockBean
    private Shell shell;

    @Autowired
    private MessageSource msg;

    @ParameterizedTest
    @ValueSource(strings = {"eng\n1Test\n5\nno\nyes\nyes\nno\nyes"
            ,"eng\n2Test\n5\nno\nno\nno\nno\nno"
            ,"eng\n3Test\n5\nyes\nyes\nyes\nyes\nyes"
            ,"eng\n4Test\n5\nyes\nno\nno\nyes\nno"})
    void print(String userInput) {
        Scanner sc = new Scanner(userInput);
        EXAM_SERVICE.print(CSV, sc, msg);
    }

    @Test
    void checkAnswer() {
        exam = new Exam("2+2", "type your answer:", "4");

        assertTrue(EXAM_SERVICE.checkAnswer(exam, "4"));
        assertFalse(EXAM_SERVICE.checkAnswer(exam, "5"));
    }

    @Test
    void result() {
        exam = new Exam("2+2", "type your answer:", "4");
        int points = 0;

        // Wrong answer equals 0 points
        points = EXAM_SERVICE.result(false, exam, points, 10);
        assertEquals(0, points);

        // Wrong answer equals +10 points
        points = EXAM_SERVICE.result(true, exam, points, 10);
        assertEquals(10, points);

        points = EXAM_SERVICE.result(false, exam, points, 10);
        assertEquals(10, points);

        points = EXAM_SERVICE.result(true, exam, points, 10);
        assertEquals(20, points);
    }
}