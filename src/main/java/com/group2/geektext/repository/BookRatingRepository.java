package com.group2.geektext.repository;

import com.group2.geektext.model.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {

    @Query("SELECT AVG(r.rating) FROM BookRating r WHERE r.bookId = :bookId")
    Double findAverageRatingByBookId(@Param("bookId") Integer bookId);
}
