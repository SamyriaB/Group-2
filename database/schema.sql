-------------------------------------------------------------------
-- GeekText PostgreSQL schema (Group 2)
-- Run before seed.sql
-------------------------------------------------------------------

DROP TABLE IF EXISTS wishlist_book, wishlist, book_comment, book_rating,
    cart_item, book, author, genre, publisher, credit_card, users CASCADE;

-----------------------

-- users
-- username and password are required; name, email, home_address optional.
-- (Email cannot be updated - enforced in API.)
CREATE TABLE users
(
    user_id      INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    name         VARCHAR(100),
    email        VARCHAR(255),
    home_address VARCHAR(255)
);


--------------------------------------------------
-- credit_card  (User 1 --- N CreditCard)

CREATE TABLE credit_card
(
    card_id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          INT          NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    card_number      VARCHAR(19)  NOT NULL,
    cardholder_name  VARCHAR(100) NOT NULL,
    expiration_month INT          NOT NULL CHECK (expiration_month BETWEEN 1 AND 12),
    expiration_year  INT          NOT NULL
);


--------------------------------------------------
-- publisher

CREATE TABLE publisher
(
    publisher_id   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    publisher_name VARCHAR(100) NOT NULL UNIQUE
);


--------------------------------------------------
-- genre

CREATE TABLE genre
(
    genre_id   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    genre_name VARCHAR(50) NOT NULL UNIQUE
);


--------------------------------------------------
-- author  (Publisher 1 --- N Author)

CREATE TABLE author
(
    author_id    INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name   VARCHAR(50) NOT NULL,
    last_name    VARCHAR(50) NOT NULL,
    biography    TEXT,
    publisher_id INT REFERENCES publisher (publisher_id)
);


--------------------------------------------------
-- book  (Author/Genre/Publisher 1 --- N Book)

CREATE TABLE book
(
    book_id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    isbn             VARCHAR(13)    NOT NULL UNIQUE,
    book_name        VARCHAR(255)   NOT NULL,
    book_description TEXT,
    price            NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    year_published   INT,
    copies_sold      INT            NOT NULL DEFAULT 0 CHECK (copies_sold >= 0),
    author_id        INT            NOT NULL REFERENCES author (author_id),
    genre_id         INT            NOT NULL REFERENCES genre (genre_id),
    publisher_id     INT            NOT NULL REFERENCES publisher (publisher_id)
);


--------------------------------------------------
-- cart_item  (User N --- M Book)

CREATE TABLE cart_item
(
    user_id INT NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    book_id INT NOT NULL REFERENCES book (book_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, book_id)
);


--------------------------------------------------
-- book_rating  (rating must be between 1 and 5)

CREATE TABLE book_rating
(
    rating_id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id    INT       NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    book_id    INT       NOT NULL REFERENCES book (book_id) ON DELETE CASCADE,
    rating     INT       NOT NULL CHECK (rating BETWEEN 1 AND 5),
    date_stamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


--------------------------------------------------
-- book_comment

CREATE TABLE book_comment
(
    comment_id   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id      INT       NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    book_id      INT       NOT NULL REFERENCES book (book_id) ON DELETE CASCADE,
    comment_text TEXT      NOT NULL,
    date_stamp   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


--------------------------------------------------
-- wishlist  (name unique per user; max 3 per user - enforced in the API)

CREATE TABLE wishlist
(
    wishlist_id   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id       INT          NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    wishlist_name VARCHAR(100) NOT NULL,
    UNIQUE (user_id, wishlist_name)
);


--------------------------------------------------
-- wishlist_book  (Wishlist N --- M Book)

CREATE TABLE wishlist_book
(
    wishlist_id INT NOT NULL REFERENCES wishlist (wishlist_id) ON DELETE CASCADE,
    book_id     INT NOT NULL REFERENCES book (book_id) ON DELETE CASCADE,
    PRIMARY KEY (wishlist_id, book_id)
);