package com.assessment.booklibrary.dataaccess.repositories;

import com.assessment.booklibrary.dataaccess.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllBooksByAuthorIdAndIsAvail(Long authorId, boolean isAvail);
    List<Book> findAllBooksByIsAvail(boolean isAvail);
    List<Book> findAllBooksByTitleContains(String title);
    List<Book> findAllBooksByAuthorIdAndTitle(Long authorId, String title);



}
