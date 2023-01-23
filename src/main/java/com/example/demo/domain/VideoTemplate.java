package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class VideoTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, length = 100)
    protected String name;
    protected String description;
    protected String image;
    @Column(nullable = false, length = 50)
    protected String type;
    @Column(nullable = false)
    protected Double rating;
}
