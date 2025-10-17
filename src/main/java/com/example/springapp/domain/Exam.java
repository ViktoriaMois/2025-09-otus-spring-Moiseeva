package com.example.springapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Exam {

    private final String question;
    private final String answer;
    private final String rightAnswer;
}
