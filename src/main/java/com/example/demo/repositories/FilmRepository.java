package com.example.demo.repositories;

import com.example.demo.domain.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
    Optional<Film> findAllByOrderByRatingDesc();
    Film findById(long id);
}
