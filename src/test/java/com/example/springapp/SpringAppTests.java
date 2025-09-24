package com.example.springapp;

import com.example.springapp.dao.ExamDao;
import com.example.springapp.domain.Exam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringAppTests {
    private final AnnotationConfigApplicationContext CONTEXT =
            new AnnotationConfigApplicationContext(SpringApp.class);
    private final ClassPathResource CSV = CONTEXT.getBean(ClassPathResource.class);
    private final ExamDao DAO = CONTEXT.getBean(ExamDao.class);

    @Test
    void testRead() {
        assertEquals(5, DAO.read(CSV).size());
    }

    @Test
    void testAnswersAndQuestions() {
        List<Exam> examRes = DAO.read(CSV);
        List<Exam> examExp = new ArrayList<>();
        examExp.add(new Exam("Was \"Mona Lisa\" painted by Sandro Botticelli?","YES/NO","no",20));
        examExp.add(new Exam("Was \"The Birth of Venus\" painted by Sandro Botticelli?","YES/NO","yes",20));
        examExp.add(new Exam("Was \"Mona Lisa\" painted by Leonardo da Vinci?","YES/NO","yes",20));
        examExp.add(new Exam("Was \"The Creation of Adam\" painted by Tiziano Vecelli?","YES/NO","no",20));
        examExp.add(new Exam("Was \"The Creation of Adam\" painted by Michelangelo Buonarroti?","YES/NO","yes",20));

        for (int i = 0; i < examRes.size(); i++) {
            assertEquals(examRes.get(i).getQuestion(), examExp.get(i).getQuestion());
            assertEquals(examRes.get(i).getAnswer(), examExp.get(i).getAnswer());
        }
    }
}
