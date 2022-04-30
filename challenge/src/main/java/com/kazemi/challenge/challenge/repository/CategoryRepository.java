package com.kazemi.challenge.challenge.repository;

import com.kazemi.challenge.challenge.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findFirstByName(String name);
}
