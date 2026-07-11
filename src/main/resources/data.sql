CREATE TABLE IF NOT EXISTS book
(
    id
    bigint
    auto_increment,
    title
    text
    not
    null,
    description
    text
    not
    null,
    isbn
    varchar
(
    255
) not null
    );
INSERT INTO book (title, description, isbn)
VALUES ('A Song of Ice And Fire I', 'Never-ending book series', '1'),
       ('A Song of Ice And Fire II', 'Never-ending book series', '2'),
       ('A Song of Ice And Fire III', 'Never-ending book series', '3'),
       ('A Song of Ice And Fire IV', 'Never-ending book series', '4'),
       ('A Song of Ice And Fire V', 'Never-ending book series', '5');
CREATE TABLE IF NOT EXISTS author
(
    id
    bigint
    auto_increment,
    name
    text
    not
    null,
    viaf
    text
    not
    null
);
INSERT INTO author (name)
VALUES ('J.R.R. Martin');
CREATE TABLE IF NOT EXISTS author_book
(
    author_id
    bigint
    not
    null
    references
    author
(
    id
),
    book_id bigint not null
    references book
(
    id
),
    primary key
(
    author_id,
    book_id
)
    );
INSERT INTO author_book (author_id, book_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5);