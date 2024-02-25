-- Authors
INSERT INTO authors (id, name) VALUES (1000, 'Haruki Murakami');
INSERT INTO authors (id, name) VALUES (2000, 'George R.R. Martin');

-- Books
INSERT INTO books (id, title, publisher, published_date) VALUES (1000, 'Kafka on the Shore', 'Shinchosha', '2002-09-12');
INSERT INTO books (id, title, publisher, published_date) VALUES (2000, 'A Game of Thrones', 'Bantam Books', '1996-08-06');

-- Reviews
INSERT INTO reviews (id, book_id, content, rating, created_date) VALUES (1000, 1000, 'Mystical and enthralling.', 5.0, '2024-02-01');
INSERT INTO reviews (id, book_id, content, rating, created_date) VALUES (2000, 2000, 'A captivating tale of power and betrayal.', 4.5, '2024-02-02');

-- BooksAuthors - many-to-many relationship between books and authors
INSERT INTO books_authors (book_id, author_id) VALUES (1000, 1000);
INSERT INTO books_authors (book_id, author_id) VALUES (2000, 2000);