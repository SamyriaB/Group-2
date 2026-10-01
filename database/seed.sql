-------------------------------------------------------------------------------------------------
-- GeekText dummy data (Group 2)
-- Run after schema.sql, after creating fresh tables on railway.
-- IDs start at 1 in insert order, so numbers used below.
-- (author_id, book_id, user_id, ...) match rows inserted above them.
-- Credit card numbers are test numbers.
-------------------------------------------------------------------------------------------------

-----------------------------------------------------------
-- publisher  (ids 1-6)

INSERT INTO publisher (publisher_name)
VALUES ('Prentice Hall'),   -- 1
       ('Addison-Wesley'),  -- 2
       ('O''Reilly Media'), -- 3
       ('MIT Press'),       -- 4
       ('Microsoft Press'), -- 5
       ('CareerCup'); -- 6


-----------------------------------------------------------
-- genre  (ids 1-5)

INSERT INTO genre (genre_name)
VALUES ('Software Engineering'),  -- 1
       ('Programming Languages'), -- 2
       ('Algorithms'),            -- 3
       ('Software Architecture'), -- 4
       ('Career Development'); -- 5


-----------------------------------------------------------
-- author  (ids 1-16)

INSERT INTO author (first_name, last_name, biography, publisher_id)
VALUES ('Robert', 'Martin',
        'Software engineer and consultant known for his writing on clean code and software craftsmanship.', 1),   -- 1
       ('Andrew', 'Hunt', 'Programmer and co-author of a classic guide to pragmatic software development.', 2),   -- 2
       ('Erich', 'Gamma', 'Computer scientist and one of the "Gang of Four" authors of the design patterns catalog.',
        2),                                                                                                       -- 3
       ('Martin', 'Fowler', 'Software developer and author focused on refactoring, design and enterprise architecture.',
        2),                                                                                                       -- 4
       ('Joshua', 'Bloch', 'Software engineer who led the design of several Java platform features.', 2),         -- 5
       ('Steve', 'McConnell', 'Author and consultant writing about software construction and project estimation.',
        5),                                                                                                       -- 6
       ('Thomas', 'Cormen', 'Computer science professor and co-author of a widely used algorithms textbook.', 4), -- 7
       ('Frederick', 'Brooks', 'Computer architect and author of influential essays on software project management.',
        2),                                                                                                       -- 8
       ('Eric', 'Freeman', 'Computer scientist and co-author of a visual, beginner-friendly book series.', 3),    -- 9
       ('Brian', 'Goetz', 'Java language architect who writes about concurrency and language design.', 2),        -- 10
       ('Martin', 'Kleppmann', 'Researcher and author on distributed systems and data-intensive applications.',
        3),                                                                                                       -- 11
       ('Harold', 'Abelson', 'Computer science professor and co-author of a classic introductory programming textbook.',
        4),                                                                                                       -- 12
       ('Gayle', 'Laakmann McDowell', 'Software engineer and author of popular technical interview preparation books.',
        6),                                                                                                       -- 13
       ('Kathy', 'Sierra', 'Programming instructor and co-creator of a visual, beginner-friendly book series.',
        3),                                                                                                       -- 14
       ('Eric', 'Evans', 'Software designer and author known for introducing domain-driven design.', 2),          -- 15
       ('Michael', 'Feathers', 'Consultant and author focused on improving and testing legacy code bases.', 1); -- 16


-----------------------------------------------------------
-- book  (ids 1-18)
-- columns: isbn, book_name, book_description, price, year_published,
--          copies_sold, author_id, genre_id, publisher_id
INSERT INTO book (isbn, book_name, book_description, price, year_published, copies_sold, author_id, genre_id,
                  publisher_id)
VALUES ('9780132350884', 'Clean Code', 'Principles and practices for writing readable, maintainable code.', 42.99, 2008,
        15200, 1, 1, 1),                                                                                 -- 1
       ('9780137081073', 'The Clean Coder', 'Professional conduct and habits for software developers.', 34.99, 2011,
        6100, 1, 5, 1),                                                                                  -- 2
       ('9780134494166', 'Clean Architecture', 'Rules for structuring software systems that are easy to change.', 37.99,
        2017, 8900, 1, 4, 1),                                                                            -- 3
       ('9780135957059', 'The Pragmatic Programmer (20th Anniversary Edition)',
        'Practical advice for becoming a more effective programmer.', 49.99, 2019, 12800, 2, 1, 2),      -- 4
       ('9780201633610', 'Design Patterns', 'A catalog of reusable object-oriented design solutions.', 54.99, 1994,
        11400, 3, 4, 2),                                                                                 -- 5
       ('9780134757599', 'Refactoring (2nd Edition)', 'Techniques for improving the design of existing code safely.',
        47.99, 2018, 7600, 4, 1, 2),                                                                     -- 6
       ('9780134685991', 'Effective Java (3rd Edition)', 'Best practices for writing robust, efficient Java programs.',
        44.99, 2017, 13900, 5, 2, 2),                                                                    -- 7
       ('9780735619678', 'Code Complete (2nd Edition)', 'A practical handbook of software construction.', 39.99, 2004,
        9800, 6, 1, 5),                                                                                  -- 8
       ('9780262033848', 'Introduction to Algorithms (3rd Edition)',
        'Comprehensive coverage of algorithms and data structures.', 89.99, 2009, 14600, 7, 3, 4),       -- 9
       ('9780201835953', 'The Mythical Man-Month (Anniversary Edition)',
        'Essays on managing software projects and teams.', 29.99, 1995, 5400, 8, 1, 2),                  -- 10
       ('9780596007126', 'Head First Design Patterns', 'A visual, hands-on introduction to design patterns.', 44.99,
        2004, 10300, 9, 4, 3),                                                                           -- 11
       ('9780321349606', 'Java Concurrency in Practice', 'Writing correct and scalable multithreaded Java code.', 49.99,
        2006, 4700, 10, 2, 2),                                                                           -- 12
       ('9781449373320', 'Designing Data-Intensive Applications',
        'How to build reliable, scalable and maintainable data systems.', 59.99, 2017, 12100, 11, 4, 3), -- 13
       ('9780262510875', 'Structure and Interpretation of Computer Programs (2nd Edition)',
        'Fundamental ideas of programming and abstraction.', 55.00, 1996, 3900, 12, 2, 4),               -- 14
       ('9780984782857', 'Cracking the Coding Interview (6th Edition)',
        'Programming questions and strategies for technical interviews.', 39.95, 2015, 16800, 13, 5, 6), -- 15
       ('9780596009205', 'Head First Java (2nd Edition)', 'A beginner-friendly, visual introduction to Java.', 39.99,
        2005, 8300, 14, 2, 3),                                                                           -- 16
       ('9780321125217', 'Domain-Driven Design', 'Modeling complex business domains in software.', 64.99, 2003, 4200,
        15, 4, 2),                                                                                       -- 17
       ('9780131177055', 'Working Effectively with Legacy Code',
        'Strategies for safely changing and testing existing code bases.', 52.99, 2004, 3600, 16, 1, 1); -- 18

-----------------------------------------------------------
-- users  (ids 1-5)  - passwords are dummy placeholders
-- users 4 and 5 leave some optional fields empty on purpose

INSERT INTO users (username, password, name, email, home_address)
VALUES ('jdoe', 'Password123!', 'John Doe', 'jdoe@example.com', '123 Main St, Miami, FL 33101'),             -- 1
       ('asmith', 'Password123!', 'Anna Smith', 'asmith@example.com', '45 Ocean Dr, Miami Beach, FL 33139'), -- 2
       ('mgarcia', 'Password123!', 'Maria Garcia', 'mgarcia@example.com', '780 Coral Way, Miami, FL 33145'), -- 3
       ('klee', 'Password123!', NULL, NULL, NULL),                                                           -- 4
       ('tnguyen', 'Password123!', 'Tom Nguyen', 'tnguyen@example.com', NULL); -- 5


-----------------------------------------------------------
-- credit_card  (public test card numbers)

INSERT INTO credit_card (user_id, card_number, cardholder_name, expiration_month, expiration_year)
VALUES (1, '4111111111111111', 'John Doe', 8, 2028),
       (1, '5555555555554444', 'John Doe', 3, 2027),
       (2, '378282246310005', 'Anna Smith', 11, 2029),
       (3, '6011111111111117', 'Maria Garcia', 5, 2028);


-----------------------------------------------------------
-- cart_item

INSERT INTO cart_item (user_id, book_id)
VALUES (1, 1),
       (1, 7),
       (2, 9),
       (3, 4),
       (3, 13),
       (3, 15);


-----------------------------------------------------------
-- book_rating  (1-5)
-- some books have no ratings on purpose

INSERT INTO book_rating (user_id, book_id, rating, date_stamp)
VALUES (1, 1, 5, '2026-09-01 10:15:00'),
       (2, 1, 4, '2026-09-02 14:30:00'),
       (3, 1, 5, '2026-09-03 09:45:00'),
       (1, 4, 5, '2026-09-04 18:20:00'),
       (5, 4, 4, '2026-09-05 11:05:00'),
       (2, 5, 3, '2026-09-06 16:40:00'),
       (4, 6, 4, '2026-09-07 13:10:00'),
       (1, 7, 5, '2026-09-08 08:55:00'),
       (3, 7, 4, '2026-09-09 19:25:00'),
       (2, 9, 2, '2026-09-10 12:00:00'),
       (4, 9, 3, '2026-09-11 15:35:00'),
       (5, 10, 3, '2026-09-12 10:50:00'),
       (3, 11, 5, '2026-09-13 17:15:00'),
       (1, 13, 5, '2026-09-14 20:05:00'),
       (2, 13, 5, '2026-09-15 09:30:00'),
       (5, 15, 4, '2026-09-16 14:45:00'),
       (4, 15, 1, '2026-09-17 11:20:00'),
       (3, 16, 4, '2026-09-18 16:00:00');


-----------------------------------------------------------
-- book_comment
INSERT INTO book_comment (user_id, book_id, comment_text, date_stamp)
VALUES (1, 1, 'Changed the way I name variables and write functions.', '2026-09-01 10:20:00'),
       (2, 1, 'Great advice, though some examples feel a bit dated.', '2026-09-02 14:35:00'),
       (3, 1, 'Every developer should read this at least once.', '2026-09-03 09:50:00'),
       (1, 4, 'Full of practical tips I started using right away.', '2026-09-04 18:25:00'),
       (2, 9, 'Very thorough, but heavy reading for a beginner.', '2026-09-10 12:05:00'),
       (3, 11, 'The visual style makes the patterns easy to remember.', '2026-09-13 17:20:00'),
       (1, 13, 'Best explanation of distributed data systems I have found.', '2026-09-14 20:10:00'),
       (5, 15, 'Helped me prepare for my internship interviews.', '2026-09-16 14:50:00'),
       (4, 15, 'Too focused on puzzles for my taste.', '2026-09-17 11:25:00');


-----------------------------------------------------------
-- wishlist  (user 1 has the maximum of 3)

INSERT INTO wishlist (user_id, wishlist_name)
VALUES (1, 'Must Read'),           -- 1
       (1, 'Java'),                -- 2
       (1, 'Gift Ideas'),          -- 3
       (2, 'Algorithms Practice'), -- 4
       (3, 'Architecture'); -- 5


-----------------------------------------------------------
-- wishlist_book

INSERT INTO wishlist_book (wishlist_id, book_id)
VALUES (1, 5),
       (1, 13),
       (1, 17),
       (2, 12),
       (2, 16),
       (3, 10),
       (4, 9),
       (4, 15),
       (5, 3),
       (5, 17);