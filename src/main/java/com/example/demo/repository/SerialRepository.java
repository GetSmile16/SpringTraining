package com.example.demo.repository;

import com.example.demo.domain.Serial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialRepository extends CrudRepository<Serial, Long>, VideoTemplateRepository {
    Iterable<Serial> findFirst5ByOrderByRatingDesc();
}
