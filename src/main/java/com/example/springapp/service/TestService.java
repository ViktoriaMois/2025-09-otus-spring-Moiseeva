package com.example.springapp.service;

import com.example.springapp.domain.Test;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

public interface TestService {

    void printTest(ClassPathResource csvResource);
}
