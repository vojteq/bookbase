package com.bookbase.backend.service;

import com.bookbase.backend.entity.Author;
import com.bookbase.backend.entity.Book;
import com.bookbase.backend.entity.Category;
import com.bookbase.backend.repository.AuthorRepository;
import com.bookbase.backend.repository.BookRepository;
import com.bookbase.backend.repository.CategoryRepository;
import com.bookbase.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {
    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());

    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private ReviewRepository reviewRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository,
                       ReviewRepository reviewRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;

    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public long count(){
        return bookRepository.count();
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public void save(Book book){
        if (book != null) {
            bookRepository.save(book);
        }
        LOGGER.log(Level.SEVERE,
                "Book is null. Can't save null value in the database.");
    }

    @PostConstruct
    public void populateTestData() {
        if (categoryRepository.count() == 0){
            categoryRepository.saveAll(
                    Stream.of("Informatyka", "Przygoda życia")
                    .map(Category::new).collect(Collectors.toList()));
        }
        if (bookRepository.count() == 0) {
            Random r = new Random(0);
            List<Category> categories = categoryRepository.findAll();
            bookRepository.saveAll(
                    Stream.of("Na następnych zawodach cię pokonam", "Wprowadzenie do algorytmów", "Los Pollos Hermanos")
                            .map(name -> {
                                        Book book = new Book();
                                        book.setTitle(name);
                                        book.setYear(r.nextInt()%20 + 2001);
                                        book.setCategory(categories.get(r.nextInt()%2));
                                        return book;
                            })
                            .collect(Collectors.toList()));
        }
        if (authorRepository.count() == 0) {
            List<Book> books = bookRepository.findAll();
            List<Author> authors = new ArrayList<>();
            Author author1 = new Author("Janne", "Ahonnen", 1969);
            author1.addBook(books.get(0));
            books.get(0).setAuthor(author1);
            authors.add(author1);
            Author author2 = new Author("Garek", "Cormen", 1954);
            authors.add(author2);
            books.get(1).setAuthor(author2);
            Author author3 = new Author("Alejandro", "McDonald", 2010);
            authors.add(author3);
            books.get(2).setAuthor(author3);
            authorRepository.saveAll(authors);
            bookRepository.saveAll(books);
        }
    }
}
