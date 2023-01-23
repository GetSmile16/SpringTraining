package com.example.demo.repository;

import com.example.demo.domain.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
    Iterable<Film> findFirst5ByOrderByRatingDesc();
}
