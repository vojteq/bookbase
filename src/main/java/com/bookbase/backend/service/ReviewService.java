package com.bookbase.backend.service;

import com.bookbase.backend.entity.Review;
import com.bookbase.backend.repository.BookRepository;
import com.bookbase.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ReviewService {

    private static final Logger LOGGER = Logger.getLogger(ReviewService.class.getName());

    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

    public long count(){
        return reviewRepository.count();
    }

    public void delete(Review review){
        reviewRepository.delete(review);
    }

    public void save(Review review){
        if (review != null) {
            reviewRepository.save(review);
        }
        LOGGER.log(Level.SEVERE,
                "Author is null. Can't save null value in the database.");
    }
//    @PostConstruct
//    public void populateTestData() {
//        if (reviewRepository.count() == 0 && bookRepository.findAll() != null) {
//            List<Book> books = bookRepository.findAll();
////            List<Review> reviews = new ArrayList<>();
//            for (Book book : books) {
//                int i = books.indexOf(book);
//                Review review = new Review(book, "user" + i, "some review number " + i, i % 5 + 1);
////                reviews.add(review);
//                save(review);
//                review.addReviewToBook();
//            }
////            reviewRepository.saveAll(reviews);
//        }
//    }
}
