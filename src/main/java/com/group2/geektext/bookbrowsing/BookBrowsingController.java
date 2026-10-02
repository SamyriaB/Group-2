package com.group2.geektext.bookbrowsing;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookBrowsingController {

    private final JdbcTemplate jdbcTemplate;

    public BookBrowsingController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Retrieve books by genre
    @GetMapping("/genre")
    public List<Map<String, Object>> getBooksByGenre(
            @RequestParam String genre) {

        String sql = """
                SELECT
                    b.book_id,
                    b.isbn,
                    b.book_name,
                    b.book_description,
                    b.price,
                    b.year_published,
                    b.copies_sold,
                    g.genre_name
                FROM book b
                JOIN genre g ON b.genre_id = g.genre_id
                WHERE LOWER(g.genre_name) = LOWER(?)
                ORDER BY b.book_name
                """;

        return jdbcTemplate.queryForList(sql, genre);
    }

    // 2. Retrieve top 10 best-selling books
    @GetMapping("/top-sellers")
    public List<Map<String, Object>> getTopSellers() {

        String sql = """
                SELECT
                    book_id,
                    isbn,
                    book_name,
                    book_description,
                    price,
                    year_published,
                    copies_sold
                FROM book
                ORDER BY copies_sold DESC
                LIMIT 10
                """;

        return jdbcTemplate.queryForList(sql);
    }

    // 3. Retrieve books with a particular rating or higher
    @GetMapping("/rating")
    public List<Map<String, Object>> getBooksByRating(
            @RequestParam double rating) {

        String sql = """
                SELECT
                    b.book_id,
                    b.isbn,
                    b.book_name,
                    b.book_description,
                    b.price,
                    b.year_published,
                    b.copies_sold,
                    ROUND(AVG(br.rating), 2) AS average_rating
                FROM book b
                JOIN book_rating br ON b.book_id = br.book_id
                GROUP BY
                    b.book_id,
                    b.isbn,
                    b.book_name,
                    b.book_description,
                    b.price,
                    b.year_published,
                    b.copies_sold
                HAVING AVG(br.rating) >= ?
                ORDER BY average_rating DESC
                """;

        return jdbcTemplate.queryForList(sql, rating);
    }

    // 4. Discount all books from a publisher
    @PatchMapping("/discount")
    public Map<String, Object> discountBooksByPublisher(
            @RequestParam String publisher,
            @RequestParam double discountPercent) {

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException(
                    "Discount percent must be between 0 and 100."
            );
        }

        String sql = """
                UPDATE book b
                SET price = ROUND(
                    b.price * (1 - CAST(? AS NUMERIC) / 100),
                    2
                )
                FROM publisher p
                WHERE b.publisher_id = p.publisher_id
                  AND LOWER(p.publisher_name) = LOWER(?)
                """;

        int booksUpdated =
                jdbcTemplate.update(sql, discountPercent, publisher);

        return Map.of(
                "publisher", publisher,
                "discount_percent", discountPercent,
                "books_updated", booksUpdated
        );
    }
}