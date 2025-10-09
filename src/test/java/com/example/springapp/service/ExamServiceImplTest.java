package com.example.springapp.service;

import com.example.springapp.domain.Exam;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
class ExamServiceImplTest {

    @Autowired
    private ExamService examService;

    @MockBean
    private Shell shell;

    @ParameterizedTest
    @ValueSource(strings = {"1Test\n5\nno\nyes\nyes\nno\nyes"
            ,"2Test\n5\nno\nno\nno\nno\nno"
            ,"3Test\n5\nyes\nyes\nyes\nyes\nyes"
            ,"4Test\n5\nyes\nno\nno\nyes\nno"})
    void print(String userInput) {
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(userInput.getBytes()));
            examService.print();
        } finally {
            System.setIn(stdin);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"1Test\n6\n"})
    void exceptionMore(String userInput) {
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(userInput.getBytes()));
            assertThrows(RuntimeException.class, () -> examService.print());
        } finally {
            System.setIn(stdin);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"1Test\n1\n"})
    void exceptionLess(String userInput) {
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(userInput.getBytes()));
            assertThrows(RuntimeException.class, () -> examService.print());
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void checkAnswer() {
        Exam exam = new Exam("2+2", "type your answer:", "4");

        assertTrue(examService.checkAnswer(exam, "4"));
        assertFalse(examService.checkAnswer(exam, "5"));
    }

    @Test
    void result() {
        // Wrong answer equals 0 points
        examService.result(false);
        assertEquals(0, examService.getPoints());

        // Right answer equals 20 points
        examService.result(true);
        assertEquals(20, examService.getPoints());

        examService.result(false);
        assertEquals(20, examService.getPoints());

        examService.result(true);
        assertEquals(40, examService.getPoints());
    }
}