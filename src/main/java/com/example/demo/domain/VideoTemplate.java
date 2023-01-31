package com.example.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class VideoTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Название не должно быть пустым")
    protected String name;
    @Size(min = 0, max = 300)
    protected String description;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    protected Image image;
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Жанр не должен быть пустым")
    protected String type;
    @Column(nullable = false)
    @NotNull(message = "Рейтинг не должен быть пустым")
    @Min(1)
    @Max(10)
    protected Double rating;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    protected User user;
    protected LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }
}
