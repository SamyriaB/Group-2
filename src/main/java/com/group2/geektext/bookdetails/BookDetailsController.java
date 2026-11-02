package com.group2.geektext.bookdetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.util.StringUtils;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookDetailsController {

    // Query book + its author, genre and publisher
    private static final String BOOK_DETAILS_SQL = """
            SELECT
                b.book_id,
                b.isbn,
                b.book_name,
                b.book_description,
                b.price,
                b.year_published,
                b.copies_sold,
                a.first_name || ' ' || a.last_name AS author_name,
                g.genre_name,
                p.publisher_name
            FROM book b
            JOIN author a ON b.author_id = a.author_id
            JOIN genre g ON b.genre_id = g.genre_id
            JOIN publisher p ON b.publisher_id = p.publisher_id
            """;

    private final JdbcTemplate jdbcTemplate;

    public BookDetailsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1 Retrieve all books with their details
    @GetMapping
    public List<Map<String, Object>> getAllBooks() {
        return jdbcTemplate.queryForList(BOOK_DETAILS_SQL + " ORDER BY b.book_name");
    }

    // 2 Retrieve one book details by ISBN number
    @GetMapping("/{isbn}")
    public Map<String, Object> getBookByIsbn(@PathVariable String isbn) {
        List<Map<String, Object>> books =
                jdbcTemplate.queryForList(BOOK_DETAILS_SQL + " WHERE b.isbn = ?", isbn);

        if (books.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No book found with ISBN " + isbn); // Sends 404 if no book has this ISBN
        }
        return books.get(0);
    }

    // 3 Retrieve all books by specific author
    @GetMapping("/author/{authorId}")
    public List<Map<String, Object>> getBooksByAuthor(@PathVariable Integer authorId) {
        // First check if author exists, so we can send 404 instead of empty list
        Boolean authorExists = jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM author WHERE author_id = ?)",
                Boolean.class, authorId);

        if (!Boolean.TRUE.equals(authorExists)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No author found with ID " + authorId); // Sends 404 if author doesnt exist
        }

        return jdbcTemplate.queryForList(
                BOOK_DETAILS_SQL + " WHERE b.author_id = ? ORDER BY b.book_name", authorId);
    }

    // 4 Create new author
    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED) // Sends 201 when author is created
    public void createAuthor(@RequestBody Author author) {
        // First name, last name and publisher required
        if (!StringUtils.hasText(author.firstName()) || !StringUtils.hasText(author.lastName())
                || author.publisherId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "First name, last name and publisher ID are required");
        }

        // Check publisher exists, so we send 400 instead of database error
        Boolean publisherExists = jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM publisher WHERE publisher_id = ?)",
                Boolean.class, author.publisherId());

        if (!Boolean.TRUE.equals(publisherExists)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No publisher found with ID " + author.publisherId());
        }

        jdbcTemplate.update(
                "INSERT INTO author (first_name, last_name, biography, publisher_id) VALUES (?, ?, ?, ?)",
                author.firstName(), author.lastName(), author.biography(), author.publisherId());
    }

}