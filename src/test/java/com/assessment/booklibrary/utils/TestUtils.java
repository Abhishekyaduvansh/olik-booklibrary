package com.assessment.booklibrary.utils;

import com.assessment.booklibrary.dataaccess.AuthorRequest;
import com.assessment.booklibrary.dataaccess.BookRequest;
import com.assessment.booklibrary.dataaccess.RentalRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static AuthorRequest createAuthorRequest(long id, String biography, String name) {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setId(id);
        authorRequest.setBiography(biography);
        authorRequest.setName(name);
        return authorRequest;
    }

    public static List<AuthorRequest> bulkAuthorRequestList(int listSize) {
        List<AuthorRequest> authorRequests = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            authorRequests.add(createAuthorRequest(i, "biography_" + i, "name_" + i));
        }
        return authorRequests;
    }


    public static BookRequest createBookRequest(long id, int isbn, int publicationYear, String title, long authorId) {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setId(id);
        bookRequest.setIsbn(isbn);
        bookRequest.setPublicationYear(publicationYear);
        bookRequest.setTitle(title);
        bookRequest.setAuthorId(authorId);
        return bookRequest;
    }

    public static List<BookRequest> bulkBookRequest(int bookRequestSize) {
        List<BookRequest> bookRequests = new ArrayList<>();
        for (int i = 0; i < bookRequestSize; i++) {
            bookRequests.add(createBookRequest(i, i, 2000 + i, "book_title_" + i, i));
        }
        return bookRequests;
    }

    public static RentalRequest createRentalRequest( String bookName, String renterName) {
        RentalRequest request = new RentalRequest();
        request.setBookName(bookName);
        request.setRenterName(renterName);
        return request;
    }
}
