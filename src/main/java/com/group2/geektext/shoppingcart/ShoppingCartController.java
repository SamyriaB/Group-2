package com.group2.geektext.shoppingcart;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}