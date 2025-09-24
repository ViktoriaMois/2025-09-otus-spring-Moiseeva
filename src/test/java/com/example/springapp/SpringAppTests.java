package com.example.springapp;

import com.example.springapp.dao.TestDao;
import com.example.springapp.domain.TestObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringAppTests {
    private final ClassPathXmlApplicationContext CONTEXT =
            new ClassPathXmlApplicationContext("/spring-context.xml");
    private final ClassPathResource CSV = CONTEXT.getBean(ClassPathResource.class);
    private final TestDao DAO = CONTEXT.getBean(TestDao.class);

    @Test
    void testReadTest() {
        assertEquals(6, DAO.readTest(CSV).size());
    }

    @Test
    void testAnswersAndQuestions() {
        List<TestObject> testRes = DAO.readTest(CSV);
        List<TestObject> testExp = new ArrayList<>();
        testExp.add(new TestObject("What do you like to do?", "[1] skate [2] drive [3] walk"));
        testExp.add(new TestObject("What's your favourite colour?", "[1] red [2] green [3] yellow [4] blue"));
        testExp.add(new TestObject("Do you have any hobbies?", "[1] coding [2] painting [3] skating"));
        testExp.add(new TestObject("Do you listen to music?", "[1] yes [2] no"));
        testExp.add(new TestObject("Do you like painting?", "[1] yes [2] no"));
        testExp.add(new TestObject("Do you like coding?", "[1] yes [2] no"));

        for (int i = 0; i < testRes.size(); i++) {
            assertEquals(testRes.get(i).getQuestion(), testExp.get(i).getQuestion());
            assertEquals(testRes.get(i).getAnswer(), testExp.get(i).getAnswer());
        }
    }
}
