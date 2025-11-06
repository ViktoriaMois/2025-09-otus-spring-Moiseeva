package com.example.dao;

import com.example.domain.Exam;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import java.util.List;

public interface ExamDao {

    List<Exam> read(ClassPathResource csvResource);
}
