package com.group2.geektext.controller;

import com.group2.geektext.model.BookComment;
import com.group2.geektext.model.BookRating;
import com.group2.geektext.repository.BookCommentRepository;
import com.group2.geektext.repository.BookRatingRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookReviewController {

    private final BookRatingRepository bookRatingRepository;
    private final BookCommentRepository bookCommentRepository;

    public BookReviewController(
            BookRatingRepository bookRatingRepository,
            BookCommentRepository bookCommentRepository) {

        this.bookRatingRepository = bookRatingRepository;
        this.bookCommentRepository = bookCommentRepository;
    }

    @PostMapping("/ratings")
    public BookRating createRating(@RequestBody BookRating rating) {
        return bookRatingRepository.save(rating);
    }

    @PostMapping("/comments")
    public BookComment createComment(@RequestBody BookComment comment) {
        return bookCommentRepository.save(comment);
    }

    @GetMapping("/comments/book/{bookId}")
    public java.util.List<BookComment> getCommentsByBook(@PathVariable Integer bookId) {
        return bookCommentRepository.findAll()
                .stream()
                .filter(comment -> comment.getBookId().equals(bookId))
                .toList();
    }
}