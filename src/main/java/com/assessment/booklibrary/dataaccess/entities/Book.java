package com.assessment.booklibrary.dataaccess.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//id, title, author, isbn, and publicationYear.
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 100, message = "Book title length validation failed")
    private String title;

    @Min(value = 1, message = "ISBN cannot be empty")
    private long isbn;


    @Min(value = 1800, message = "Publication Year is invalid")
    @Max(value = 2024, message = "Publication year is invalid")
    private int publicationYear;

    private boolean isAvail = true;

    @Min(value = 0, message = "Author Id is mandatory")
    private Long authorId;
}
