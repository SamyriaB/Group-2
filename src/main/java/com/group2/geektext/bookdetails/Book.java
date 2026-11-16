package com.group2.geektext.bookdetails;

import java.math.BigDecimal;

// Book info sent in request body when creating book

public record Book(
        String isbn,
        String bookName,
        String bookDescription,
        BigDecimal price,
        Integer authorId,
        Integer genreId,
        Integer publisherId,
        Integer yearPublished,
        Integer copiesSold
) {}