package com.example.springapp.dao;

import com.example.springapp.domain.TestObject;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TestDaoImpl implements TestDao {

    @Override
    public List<TestObject> readTest(ClassPathResource csvResource) {
        List<TestObject> test = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvResource.getInputStream()))) {
            reader.readLine();
            String line;
            test = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                test.add(new TestObject(split[0], split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return test;
    }
}
