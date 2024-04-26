package com.assessment.booklibrary.controller;

import com.assessment.booklibrary.dataaccess.AuthorRequest;
import com.assessment.booklibrary.dataaccess.entities.Author;
import com.assessment.booklibrary.Services.AuthorService;
import com.assessment.booklibrary.exception.BookServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assessment.booklibrary.utils.Constants.API_VERSION;
import static com.assessment.booklibrary.utils.Constants.AUTHOR_SERVICE;

@RestController
@RequestMapping(API_VERSION + AUTHOR_SERVICE)
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<Author> addNewAuthor(@RequestBody AuthorRequest request) {
        Author authorResponse = authorService.addAuthor(request);
        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Author>> searchAllAuthor() {
        return new ResponseEntity<>(authorService.findAllAuthor(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAuthorById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.deleteAuthorByID(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody AuthorRequest request) throws BookServiceException {
        return new ResponseEntity<>(authorService.updateAuthor(id, request), HttpStatus.OK);
    }

}
