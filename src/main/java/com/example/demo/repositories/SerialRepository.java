package com.example.demo.repositories;

import com.example.demo.domain.Serial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SerialRepository extends CrudRepository<Serial, Long> {
    Optional<Serial> findAllByOrderByRatingDesc();
    Serial findById(long id);
}
