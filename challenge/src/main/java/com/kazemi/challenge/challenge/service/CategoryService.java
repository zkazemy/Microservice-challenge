package com.kazemi.challenge.challenge.service;

import com.kazemi.challenge.challenge.model.Category;
import com.kazemi.challenge.challenge.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The {@code MovieService} provides several methods for working with award movie's category entity
 *
 * @author Zahra Kazemi
 */

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category saveCategory(String name) {
        Optional<Category> category = categoryRepository.findFirstByName(name);
        return (category.isPresent() ? category.get()
                : categoryRepository.save(Category.builder()
                                                    .name(name)
                                                    .build()));
    }
}
