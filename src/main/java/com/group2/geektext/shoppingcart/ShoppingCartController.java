package com.group2.geektext.shoppingcart;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

// Shopping Cart API
// Set up the PostgreSQL connection, then run the Spring Boot
// application in IntelliJ. Keep database passwords out of GitHub.
// The examples below use http://localhost:8080.
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final JdbcTemplate jdbcTemplate;

    public ShoppingCartController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Returns the user's cart, sorted by book name.
    // GET /api/cart?userId=1
    // Returns an empty list if the cart is empty or the user doesn't exist.
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

    // Adds up the prices of the books in the user's cart.
    // GET /api/cart/subtotal?userId=1
    // An empty cart has a subtotal of zero.
    // Returns 400 for nonpositive IDs or 404 if the user doesn't exist.
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

    // Adds a book to the user's cart.
    // POST /api/cart?userId=1&bookId=2
    // Pass both IDs in the URL. No request body is needed.
    // Returns 201 if added, 409 for duplicates, or 400 for
    // nonpositive IDs or a database constraint error.
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

    // Removes a book from this user's cart.
    // DELETE /api/cart?userId=1&bookId=2
    // Pass both IDs in the URL. No request body is needed.
    // Returns 200 if removed, 400 for nonpositive IDs,
    // or 404 if the entry isn't in the cart.
    // The book itself stays in the bookstore database.
    @DeleteMapping
    public ResponseEntity<String> removeBook(
            @RequestParam int userId,
            @RequestParam int bookId) {

        if (userId <= 0 || bookId <= 0) {
            return ResponseEntity.badRequest()
                    .body("userId and bookId must be positive.");
        }

        String sql = """
                DELETE FROM cart_item
                WHERE user_id = ? AND book_id = ?
                """;

        int rowsDeleted = jdbcTemplate.update(sql, userId, bookId);

        if (rowsDeleted == 0) {
            return ResponseEntity.status(404)
                    .body("Book not found in this user's cart.");
        }

        return ResponseEntity.ok("Book removed from cart.");
    }

    // Testing plan:
    // 1. Check a test user's cart and record the subtotal.
    // 2. Add an existing book that isn't already in the cart.
    // 3. Check that it appears once and the subtotal increases
    //    by the book's price.
    // 4. Try adding it again and check for a 409 response.
    // 5. Remove it and check that the cart and subtotal
    //    match what they were before the test.
    // 6. Also check invalid IDs and missing parameters.
    // Record the actual results after running these checks.
}