package com.example.springapp.dao;

import com.example.springapp.domain.Test;
import org.springframework.core.io.ClassPathResource;
import java.util.List;

public interface TestDao {

    List<Test> readTest(ClassPathResource csvResource);
}
