package com.example.springapp.dao;

import com.example.springapp.domain.Exam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ExamDaoImpl implements ExamDao {
    private Logger logger = Logger.getLogger(ExamDaoImpl.class.getName());

    @Value("${exam.minQuestions}")
    private int minQuestions;

    @Override
    public List<Exam> read(ClassPathResource csvResource, Scanner sc, MessageSource msg, String lang) {
        System.out.println(msg.getMessage("main.question-amount.string", new Object[]{minQuestions}, Locale.forLanguageTag(lang)));
        String input = sc.nextLine();
        int questionsWanted = parseInt(input);

        if (questionsWanted < minQuestions) {
            System.out.println(msg.getMessage("main.warning.string", new Object[]{minQuestions}, Locale.forLanguageTag(lang)));
            throw new RuntimeException();
        }

        List<Exam> examList = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvResource.getInputStream()))) {
            reader.readLine();
            String line;
            examList = new ArrayList<>();
            while ((line = reader.readLine()) != null && examList.size() < questionsWanted) {
                String[] split = line.split(",");
                if (!split[0].isEmpty() && !split[1].isEmpty() && !split[2].isEmpty() ) {
                    examList.add(new Exam(split[0], split[1], split[2]));
                }
                else {
                    System.out.println(msg.getMessage("main.error.string", null, Locale.forLanguageTag(lang)));
                    throw new RuntimeException();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return examList;
    }
}