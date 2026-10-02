package com.group2.geektext.repository;

import com.group2.geektext.model.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {
}