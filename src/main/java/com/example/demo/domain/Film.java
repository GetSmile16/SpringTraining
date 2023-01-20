package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, length = 100)
    private String name;
    private String description;

    private String image;
    @Column(nullable = false, length = 50)
    private String type;
    @Column(nullable = false)
    private Double rating;

    protected Film() {}
}
