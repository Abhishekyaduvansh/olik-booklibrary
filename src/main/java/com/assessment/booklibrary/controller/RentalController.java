package com.assessment.booklibrary.controller;

import com.assessment.booklibrary.Services.RentalService;
import com.assessment.booklibrary.dataaccess.RentalRequest;
import com.assessment.booklibrary.dataaccess.entities.Rental;
import com.assessment.booklibrary.exception.BookServiceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assessment.booklibrary.utils.Constants.API_VERSION;
import static com.assessment.booklibrary.utils.Constants.RENTAL_SERVICE;

@RestController
@RequestMapping(API_VERSION + RENTAL_SERVICE)
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // Create new rental record
    @PostMapping("/create")
    public ResponseEntity<Rental> createRental(@Valid @RequestBody RentalRequest request) {
        try {
            Rental rental = rentalService.createRental(request);
            return new ResponseEntity<>(rental, HttpStatus.CREATED);
        } catch (BookServiceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update rental record
    @PutMapping("/return/{id}")
    public ResponseEntity<Void> returnBook(@PathVariable Long id) {
        try {
            rentalService.returnBook(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Find all rental records
    @GetMapping("/all")
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.findAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    // Find rental record by id
    @GetMapping("/rentedBook/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        try {
            Rental rental = rentalService.findRentalById(id);
            return new ResponseEntity<>(rental, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Check overdue rental records
    @GetMapping("/overdue")
    public ResponseEntity<List<Rental>> getOverdueRentals() {
        List<Rental> overdueRentals = rentalService.findOverdueRentals();
        return new ResponseEntity<>(overdueRentals, HttpStatus.OK);
    }
}
