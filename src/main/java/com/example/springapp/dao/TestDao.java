package com.example.springapp.dao;

import com.example.springapp.domain.TestObject;
import org.springframework.core.io.ClassPathResource;
import java.util.List;

public interface TestDao {

    List<TestObject> readTest(ClassPathResource csvResource);
}
