package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Data
public class Genre {
    @Setter
    private Long id;
    private final String name;
}
