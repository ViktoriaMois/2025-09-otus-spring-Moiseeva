package com.example.springapp.dao;

import com.example.springapp.domain.Exam;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.shell.Shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExamDaoImplTest {
    private final ExamDao DAO = new ExamDaoImpl(Logger.getLogger(ExamDaoImpl.class.getName()), 3);
    private final ClassPathResource CSV = new ClassPathResource("/test.csv");

    @Autowired
    private MessageSource msg;

    @MockBean
    private Shell shell;

    @ParameterizedTest
    @ValueSource(strings = {"5\n"})
    void testRead(String userInput) {
        Scanner sc = new Scanner(userInput);
        assertEquals(5, DAO.read(CSV, sc, msg, "eng").size(), "success");
    }

    @ParameterizedTest
    @ValueSource(strings = {"5\n"})
    void testAnswersAndQuestions(String userInput) {
        Scanner sc = new Scanner(userInput);
        List<Exam> examRes = DAO.read(CSV, sc, msg, "eng");
        List<Exam> examExp = new ArrayList<>();
        examExp.add(new Exam("Was «Mona Lisa» painted by Sandro Botticelli?","YES/NO","no/нет"));
        examExp.add(new Exam("Was «The Birth of Venus» painted by Sandro Botticelli?","YES/NO","yes/да"));
        examExp.add(new Exam("Was «Mona Lisa» painted by Leonardo da Vinci?","YES/NO","yes/да"));
        examExp.add(new Exam("Was «The Creation of Adam» painted by Tiziano Vecelli?","YES/NO","no/нет"));
        examExp.add(new Exam("Was «The Creation of Adam» painted by Michelangelo Buonarroti?","YES/NO","yes/да"));

        for (int i = 0; i < examRes.size(); i++) {
            assertEquals(examRes.get(i).getQuestion(), examExp.get(i).getQuestion(), "success");
            assertEquals(examRes.get(i).getAnswer(), examExp.get(i).getAnswer(), "success");
        }
    }
}