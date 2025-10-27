package com.example.shell;

import com.example.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Scanner;

@ShellCommandGroup("library")
@ShellComponent
@RequiredArgsConstructor
public class LibraryServiceCommands {
    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private final LibraryService service;

    @ShellMethod(value ="CREATE", key = {"C"})
    public void create() {
        service.create(scanner);
    }

    @ShellMethod(value ="READ", key = {"R"})
    public void read() {
        service.read(scanner);
    }

    @ShellMethod(value ="UPDATE", key = {"U"})
    public void update() {
        service.update(scanner);
    }

    @ShellMethod(value ="DELETE", key = {"D"})
    public void delete() {
        service.delete(scanner);
    }
}
