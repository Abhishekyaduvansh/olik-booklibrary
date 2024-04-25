package com.assessment.booklibrary.dataaccess.repositories;

import com.assessment.booklibrary.dataaccess.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Rental findByBookId(Long bookId);

    List<Rental> findByReturnDateIsNullAndRentalDateBefore(LocalDate localDate);

}
