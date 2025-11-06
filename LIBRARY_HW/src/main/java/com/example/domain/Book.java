package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Data
public class Book {
    @Setter
    private Long id;
    private final String name;
    private final Long author;
    private final Long genre;
}
