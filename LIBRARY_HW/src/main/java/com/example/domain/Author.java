package com.example.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Author {
    private final long id;
    private final String fullName;
}
