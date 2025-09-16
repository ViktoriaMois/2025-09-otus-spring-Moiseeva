package com.example.springapp.service;

import com.example.springapp.dao.TestDao;
import com.example.springapp.domain.TestObject;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestDao dao;
    @Override
    public void printTest(ClassPathResource csvResource) {
        List<TestObject> test = dao.readTest(csvResource);
        for (TestObject t : test) {
            System.out.println(t.getQuestion() + "\n" + t.getAnswer());
        }
    }
}
