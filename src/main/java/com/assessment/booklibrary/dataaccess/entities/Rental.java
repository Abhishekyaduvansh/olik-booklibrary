package com.assessment.booklibrary.dataaccess.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//id, bookId, renterName, rentalDate, and returnDate.
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne // Many rentals can be associated with one book
    @JoinColumn(name = "book_id") // Define the foreign key column
    private Book book;
    private String renterName;
    private LocalDate rentalDate;
    private LocalDate returnDate;
}
