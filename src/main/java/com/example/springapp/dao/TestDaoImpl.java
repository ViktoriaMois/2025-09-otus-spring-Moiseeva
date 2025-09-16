package com.example.springapp.dao;

import com.example.springapp.domain.Test;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TestDaoImpl implements TestDao {

    @Override
    public List<Test> readTest(ClassPathResource csvResource) {
        List<Test> test = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvResource.getInputStream()))) {
            reader.readLine();
            String line;
            test = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                test.add(new Test(split[0], split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return test;
    }
}
