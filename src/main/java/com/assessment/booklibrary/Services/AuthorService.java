package com.assessment.booklibrary.Services;

import com.assessment.booklibrary.dataaccess.AuthorRequest;
import com.assessment.booklibrary.dataaccess.entities.Author;
import com.assessment.booklibrary.dataaccess.mapper.AuthorMapper;
import com.assessment.booklibrary.dataaccess.repositories.AuthorRepository;
import com.assessment.booklibrary.exception.BookServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    //CRUD
    //Create
    public Author addAuthor(AuthorRequest authorRequest) {
        Author added = authorRepository.save(AuthorMapper.requestToEntity(authorRequest));
        log.info("added new Author {} ", added.getName());
        return added;
    }

    //Read
    public List<Author> findAllAuthor() {
        return authorRepository.findAll();
    }

    public Author findAuthorByName(String name) {
        return authorRepository.findByName(name);
    }


    public Author findAuthorById(Long id) throws BookServiceException {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        throw new BookServiceException("Author by Id " + id + " not found");
    }

    //Update
    public Author updateAuthor(Long id, AuthorRequest authorRequest) throws BookServiceException {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            if (Objects.nonNull(authorRequest.getBiography())) {
                author.setBiography(authorRequest.getBiography());
            }
            if (Objects.nonNull(authorRequest.getName())) {
                author.setName(authorRequest.getName());

            }
            return authorRepository.save(author);
        }
        throw new BookServiceException("Author by Id not found");
    }

    //Delete
    public boolean deleteAuthorByID(Long id) {
        try {
            authorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
