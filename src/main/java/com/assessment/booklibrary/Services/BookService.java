package com.assessment.booklibrary.Services;

import com.assessment.booklibrary.dataaccess.BookRequest;
import com.assessment.booklibrary.dataaccess.entities.Author;
import com.assessment.booklibrary.dataaccess.entities.Book;
import com.assessment.booklibrary.dataaccess.mapper.BookMapper;
import com.assessment.booklibrary.dataaccess.repositories.BookRepository;
import com.assessment.booklibrary.exception.BookServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import java.util.List;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;

    public Book addBook(BookRequest bookRequest) throws BookServiceException {
        if (validateBookRequest(bookRequest)) {
            Book added = bookRepository.save(BookMapper.requestToEntity(bookRequest));
            log.info("added new Book {} ", added.getTitle());
            return added;
        }
        throw new BookServiceException("Book cannot be added");
    }

    private boolean validateBookRequest(BookRequest bookRequest) {
        //validate 1. Duplicacy of book
        //2. Author exists or not
        try {
            authorService.findAuthorById(bookRequest.getAuthorId());
        } catch (BookServiceException e) {
            log.error("Validation failed, author id is not valid");
            return false;
        }
        List<Book> allBooksByAuthorIdAndTitle = bookRepository.findAllBooksByAuthorIdAndTitle(bookRequest.getAuthorId(), bookRequest.getTitle());
        if (!allBooksByAuthorIdAndTitle.isEmpty()) {
            log.error("Validation failed, book already exists");
            return false;
        }
        return true;
    }

    public List<Book> findAllBooks(String author, Boolean isAvail, String title) {
        if (Objects.nonNull(author)) {
            if (Objects.isNull(isAvail)) {
                isAvail = Boolean.TRUE;
            }
            Author authorByName = authorService.findAuthorByName(author);
            return bookRepository.findAllBooksByAuthorIdAndIsAvail(authorByName.getId(), isAvail);
        } else if (Objects.nonNull(isAvail)) {
            return bookRepository.findAllBooksByIsAvail(isAvail);
        } else if (Objects.nonNull(title)) {
            return bookRepository.findAllBooksByTitleContains(title);
        }
        return bookRepository.findAll();
    }

    public boolean deleteBookByID(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public Book updateBook(Long id, BookRequest bookRequest) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (Objects.nonNull(bookRequest.getTitle())) {
                book.setTitle(bookRequest.getTitle());
            }
            if (Objects.nonNull(bookRequest.getIsbn())) {
                book.setIsbn(bookRequest.getIsbn());
            }
            if (Objects.nonNull(bookRequest.getPublicationYear())) {
                book.setPublicationYear(bookRequest.getPublicationYear());
            }
            if (Objects.nonNull(bookRequest.getAuthorId())) {
                book.setAuthorId(bookRequest.getAuthorId());
            }
            return bookRepository.save(book);
        } else {
            throw new RuntimeException("Book not found");
        }
    }
}
