package com.group2.geektext.shoppingcart;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final JdbcTemplate jdbcTemplate;

    public ShoppingCartController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Map<String, Object>> getCart(@RequestParam int userId) {

        String sql = """
                SELECT
                    b.book_id,
                    b.isbn,
                    b.book_name,
                    b.book_description,
                    b.price,
                    b.year_published,
                    b.copies_sold
                FROM cart_item c
                JOIN book b ON c.book_id = b.book_id
                WHERE c.user_id = ?
                ORDER BY b.book_name
                """;

        return jdbcTemplate.queryForList(sql, userId);
    }

    @GetMapping("/subtotal")
    public ResponseEntity<?> getSubtotal(@RequestParam int userId) {

        if (userId <= 0) {
            return ResponseEntity.badRequest()
                    .body("userId must be positive.");
        }

        String userSql = "SELECT COUNT(*) FROM users WHERE user_id = ?";

        Integer userCount = jdbcTemplate.queryForObject(
                userSql, Integer.class, userId);

        if (userCount == null || userCount == 0) {
            return ResponseEntity.status(404)
                    .body("User not found.");
        }

        String sql = """
                SELECT COALESCE(SUM(b.price), 0)
                FROM cart_item c
                JOIN book b ON c.book_id = b.book_id
                WHERE c.user_id = ?
                """;

        BigDecimal subtotal = jdbcTemplate.queryForObject(
                sql, BigDecimal.class, userId);

        return ResponseEntity.ok(Map.of("subtotal", subtotal));
    }

    @PostMapping
    public ResponseEntity<String> addBook(
            @RequestParam int userId,
            @RequestParam int bookId) {

        if (userId <= 0 || bookId <= 0) {
            return ResponseEntity.badRequest()
                    .body("userId and bookId must be positive.");
        }

        String sql = """
                INSERT INTO cart_item (user_id, book_id)
                VALUES (?, ?)
                """;

        try {
            jdbcTemplate.update(sql, userId, bookId);

            return ResponseEntity.status(201)
                    .body("Book added to cart.");

        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(409)
                    .body("Book is already in this user's cart.");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Unable to add book. Check that the user and book exist.");
        }
    }
}