package com.assessment.booklibrary.dataaccess.repositories;

import com.assessment.booklibrary.dataaccess.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
