package com.assessment.booklibrary.controller;

import com.assessment.booklibrary.dataaccess.AuthorRequest;
import com.assessment.booklibrary.dataaccess.BookRequest;
import com.assessment.booklibrary.dataaccess.RentalRequest;
import com.assessment.booklibrary.dataaccess.entities.Author;
import com.assessment.booklibrary.dataaccess.entities.Book;
import com.assessment.booklibrary.dataaccess.repositories.AuthorRepository;
import com.assessment.booklibrary.dataaccess.repositories.BookRepository;
import com.assessment.booklibrary.dataaccess.repositories.RentalRepository;
import com.assessment.booklibrary.utils.TestUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.assessment.booklibrary.utils.Constants.*;

@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class RentalControllerTest {

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public void beforeAll() {
        Gson gson = new Gson();
        // Create new Authors
        List<AuthorRequest> authorRequests = TestUtils.bulkAuthorRequestList(5);
        List<BookRequest> bookRequests = TestUtils.bulkBookRequest(5);
        authorRequests.forEach(request -> {
            try {
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_VERSION + AUTHOR_SERVICE + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(request)))
                        .andReturn();
                log.info("MvcResult {} ", mvcResult.getResponse().getContentAsString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        // Create new Books for above authors
        bookRequests.forEach(request -> {

            try {
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_VERSION + BOOK_SERVICE + "/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(request)))
                        .andReturn();
                log.info("MvcResult for book service {} ", mvcResult.getResponse().getContentAsString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterAll
    public void afterAll() {
        rentalRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void createRental() {
        Optional<Book> byId = bookRepository.findById(1L);
        if (byId.isPresent()) {
            Book book = byId.get();
            RentalRequest testRenterRequest = TestUtils.createRentalRequest(book.getTitle(), "TestRenter");
            // call rental api for book
            try {
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_VERSION + RENTAL_SERVICE + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(testRenterRequest)))
                        .andReturn();
                log.info("rental response {}", mvcResult.getResponse().getContentAsString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void returnBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(API_VERSION + RENTAL_SERVICE + "/return/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        log.info("mvcResult for return book {}", mvcResult);
    }

    @Test
    void getAllRentals() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(API_VERSION + RENTAL_SERVICE + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        log.info("mvcResult for all rentals {}", mvcResult);
    }

    @Test
    void getRentalById() {
    }

    @Test
    void getOverdueRentals() {
    }
}