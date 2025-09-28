package com.example.springapp.dao;

import com.example.springapp.domain.Exam;
import org.springframework.core.io.ClassPathResource;
import java.util.List;

public interface ExamDao {

    List<Exam> read(ClassPathResource csvResource);
}
