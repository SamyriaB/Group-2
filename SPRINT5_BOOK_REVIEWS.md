# Sprint 5 – Book Comments and Ratings

## 1. Feature Overview

The Book Comments and Ratings feature allows users to submit ratings and comments for books in the GeekText application.

Users can submit a rating from 1 to 5 and leave a written comment for a book. Each rating and comment is saved in the PostgreSQL database with a timestamp. The application also allows users to retrieve comments for a specific book and view the average rating for a book.

## 2. HTTP Routes

The following HTTP routes were implemented and tested using IntelliJ's HTTP Client.

| HTTP Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/ratings` | Creates and saves a book rating. |
| POST | `/api/comments` | Creates and saves a book comment. |
| GET | `/api/comments/book/{bookId}` | Retrieves comments associated with a specific book. |
| GET | `/api/ratings/book/{bookId}/average` | Retrieves the average rating for a specific book. |

The average rating endpoint was tested with both Book 1 and Book 2.

## 3. Rating Requirements

Book ratings must be between 1 and 5.

Each rating is associated with a user ID and book ID. A timestamp is also saved when the rating is created.

Comments are associated with a user ID and book ID and include the comment text and timestamp.

## 4. Running and Testing the Application

The application was developed using Java and Spring Boot, with PostgreSQL as the database.

To run and test the feature:

1. Open the GeekText project in IntelliJ IDEA.
2. Verify that the Spring Boot run configuration has the required database environment variables.
3. Start the `GeektextApplication` run configuration.
4. Confirm that the application starts successfully and connects to the PostgreSQL database.
5. Open `test.http` in IntelliJ.
6. Run the POST and GET requests to test the book comments and ratings endpoints.
7. Review the HTTP response codes and returned data.

## 5. Sprint 5 Smoke Test Results

All five HTTP requests were successfully tested using IntelliJ's HTTP Client.

| Test | Expected Result | Actual Result |
|---|---|---|
| Create a book comment | HTTP 200 and saved comment information | Passed |
| Create a book rating | HTTP 200 and saved rating information | Passed |
| Retrieve comments for Book 1 | HTTP 200 and a list of comments | Passed |
| Retrieve average rating for Book 1 | HTTP 200 and a numeric average | Passed |
| Retrieve average rating for Book 2 | HTTP 200 and a numeric average | Passed |

All five requests returned HTTP 200, confirming that the endpoints were responding successfully and the feature was functioning during testing.

## 6. Conclusion

The Sprint 5 work completed the testing and documentation for the Book Comments and Ratings feature. The HTTP routes were tested successfully, and the results confirmed that comments, ratings, and average book ratings could be submitted and retrieved through the GeekText application.