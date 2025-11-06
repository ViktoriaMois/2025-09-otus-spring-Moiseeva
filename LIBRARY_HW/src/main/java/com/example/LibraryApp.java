package com.example;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryApp {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LibraryApp.class, args);

//        Console.main(args);
    }
}
