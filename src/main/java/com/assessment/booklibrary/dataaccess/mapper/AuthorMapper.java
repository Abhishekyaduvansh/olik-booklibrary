package com.assessment.booklibrary.dataaccess.mapper;

import com.assessment.booklibrary.dataaccess.AuthorRequest;
import com.assessment.booklibrary.dataaccess.entities.Author;

public class AuthorMapper {
    public static Author requestToEntity(AuthorRequest request){
        Author author = new Author();
        author.setBiography(request.getBiography());
        author.setName(request.getName());
        return author;
    }
}
