package com.example.springapp.shell;

import com.example.springapp.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellCommandGroup("exam")
@ShellComponent
@RequiredArgsConstructor
public class ExamServiceCommands {

    @Autowired
    private final ExamService service;

    @ShellMethod(value ="Print", key = {"print", "p"})
    public void print() {
        service.print();
    }
}
