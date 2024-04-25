package com.assessment.booklibrary.Services;

import com.assessment.booklibrary.dataaccess.entities.Book;
import com.assessment.booklibrary.dataaccess.entities.Rental;
import com.assessment.booklibrary.dataaccess.repositories.BookRepository;
import com.assessment.booklibrary.dataaccess.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BookRepository bookRepository;

    //When renting a book, ensure the book is not already rented.
    public boolean isAvailable(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return book.isAvail();
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    // create new rental record - when a book is rented
    // 1. find book by title
    // 2. filter by isAvail
    // 3. Create rental object
    // 4. update book isAvail = false
    // 5. save rental
    // 6. save book
    public Rental createRental(String bookTitle, String renterName) {
        List<Book> books = bookRepository.findAllBooksByTitleContains(bookTitle);
        if (books.isEmpty()) {
            throw new RuntimeException("No books found with title containing " + bookTitle);
        }

        List<Book> availableBooks = books.stream()
                .filter(Book::isAvail)
                .toList();

        if (availableBooks.isEmpty()) {
            throw new RuntimeException("No available books found with title containing " + bookTitle);
        }

        // For simplicity, assume we choose the first available book
        Book book = availableBooks.get(0);

        Rental rental = new Rental();
        rental.setBook(book);
        rental.setRenterName(renterName);
        rental.setRentalDate(LocalDate.now());
        book.setAvail(false);
        rental = rentalRepository.save(rental);
        bookRepository.save(book);
        return rental;
    }

    // update rental record - when a book is returned
    // 1. find rental record by bookId
    // 2. update return date, update book isAvail = true
    // 3. save rental record
    // 4. save book
    public void returnBook(Long bookId) {

        Rental rental = rentalRepository.findByBookId(bookId);

        if (rental == null) {
            throw new RuntimeException("No rental record found for book with ID: " + bookId);
        }

        rental.setReturnDate(LocalDate.now());
        Book book = rental.getBook();
        book.setAvail(true);
        rentalRepository.save(rental);
        bookRepository.save(book);
    }

    // find all rental records
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    // find rental record by Rental_Id
    public Rental findRentalById(Long rentalId) {
        Optional<Rental> rentalOptional = rentalRepository.findById(rentalId);
        if (rentalOptional.isPresent()) {
            return rentalOptional.get();
        } else {
            throw new RuntimeException("Rental record not found for rentalId: " + rentalId);
        }
    }


    // check overdue rental records
    // 1. find all records which are past due date
    public List<Rental> findOverdueRentals() {
        LocalDate today = LocalDate.now();
        return rentalRepository.findByReturnDateIsNullAndRentalDateBefore(today.minusDays(14));
    }
}