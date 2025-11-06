package com.example.shell;

import com.example.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class LibraryServiceCommandsTest {
    private LibraryServiceCommands commands;
    @MockBean
    private LibraryService service;

    @BeforeEach
    void setUp() {
        commands = new LibraryServiceCommands(service);
    }

    @Test
    void testCreateCommand() {
        commands.create();
        verify(service, times(1)).create(any(Scanner.class));
    }

    @Test
    void testReadCommand() {
        commands.read();
        verify(service, times(1)).read(any(Scanner.class));
    }

    @Test
    void testUpdateCommand() {
        commands.update();
        verify(service, times(1)).update(any(Scanner.class));
    }

    @Test
    void testDeleteCommand() {
        commands.delete();
        verify(service, times(1)).delete(any(Scanner.class));
    }
}