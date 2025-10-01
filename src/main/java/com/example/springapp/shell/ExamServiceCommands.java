package com.example.springapp.shell;

import com.example.springapp.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Scanner;

import static java.lang.System.in;

@ShellCommandGroup("exam")
@ShellComponent
@RequiredArgsConstructor
public class ExamServiceCommands {
    private final ExamService service;
    private final MessageSource msg;
    private final ClassPathResource CSV = new ClassPathResource("/test.csv");
    private final Scanner SC = new Scanner(in);

    @ShellMethod(value ="Print", key = {"print", "p"})
    public void print() {
        service.print(CSV, SC, msg);
    }
}
