package com.assessment.booklibrary.dataaccess.mapper;

import com.assessment.booklibrary.dataaccess.BookRequest;
import com.assessment.booklibrary.dataaccess.entities.Book;

public class BookMapper {

    public static Book requestToEntity(BookRequest request){
        Book book = new Book();
        book.setIsbn(request.getIsbn());
        book.setAvail(request.isAvail());
        book.setTitle(request.getTitle());
        book.setAuthorId(request.getAuthorId());
        book.setPublicationYear(request.getPublicationYear());
        return book;
    }
}
