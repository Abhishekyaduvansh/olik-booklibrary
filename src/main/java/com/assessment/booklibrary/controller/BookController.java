package com.assessment.booklibrary.controller;

import com.assessment.booklibrary.dataaccess.BookRequest;
import com.assessment.booklibrary.dataaccess.entities.Book;
import com.assessment.booklibrary.Services.BookService;
import com.assessment.booklibrary.exception.BookServiceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookRequest request) throws BookServiceException {
        Book bookResponse = bookService.addBook(request);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll(@RequestParam(name = "author", required = false) String author,
                                              @RequestParam(name = "isAvail", required = false) Boolean isAvail,
                                              @RequestParam(name = "title", required = false) String title) {
        return new ResponseEntity<>(bookService.findAllBooks(author, isAvail, title), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        Book bookResponse = bookService.updateBook(id, request);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBookById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.deleteBookByID(id), HttpStatus.OK);
    }
}
