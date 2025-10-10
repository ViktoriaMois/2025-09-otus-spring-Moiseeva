package com.example.springapp.dao;

//import com.example.springapp.TestConfig;
import com.example.springapp.domain.Exam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExamDaoImplTest {

    @Autowired
    private ExamDao dao;

    @MockBean
    private Shell shell;

    private final ClassPathResource csv = new ClassPathResource("/test.csv");

    @Test
    void read() {
        assertEquals(5, dao.read(csv).size(), "success");
    }

    @Test
    void answersAndQuestions() {
        List<Exam> examRes = dao.read(csv);
        List<Exam> examExp = new ArrayList<>();
        examExp.add(new Exam("Was «Mona Lisa» painted by Sandro Botticelli?","YES/NO","no/нет"));
        examExp.add(new Exam("Was «The Birth of Venus» painted by Sandro Botticelli?","YES/NO","yes/да"));
        examExp.add(new Exam("Was «Mona Lisa» painted by Leonardo da Vinci?","YES/NO","yes/да"));
        examExp.add(new Exam("Was «The Creation of Adam» painted by Tiziano Vecelli?","YES/NO","no/нет"));
        examExp.add(new Exam("Was «The Creation of Adam» painted by Michelangelo Buonarroti?","YES/NO","yes/да"));

        for (int i = 0; i < examRes.size(); i++) {
            assertEquals(normalize(examRes.get(i).getQuestion()), examExp.get(i).getQuestion(), "success");
            assertEquals(examRes.get(i).getAnswer(), examExp.get(i).getAnswer(), "success");
        }
    }

    private String normalize(String s) {
        return s.replace("В«", "«")
                .replace("В»", "»")
                .trim();
    }
}