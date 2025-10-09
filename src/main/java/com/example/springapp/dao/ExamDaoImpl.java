package com.example.springapp.dao;

import com.example.springapp.domain.Exam;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class ExamDaoImpl implements ExamDao {

    @Override
    public List<Exam> read(ClassPathResource csvResource) {
        List<Exam> examList = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvResource.getInputStream()))) {
            reader.readLine();
            String line;
            examList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                if (!split[0].isEmpty() && !split[1].isEmpty() && !split[2].isEmpty() ) {
                    examList.add(new Exam(split[0], split[1], split[2]));
                }
                else {
                    throw new RuntimeException();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return examList;
    }
}