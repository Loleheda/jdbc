insert into AUTHORS (
    FIO,
    DATE_OF_BIRCH,
    DATE_OF_DEATH)
values ('Достоевский Ф М', '1821-10-30', '1881-01-28'); --1

insert into AUTHORS (
    FIO,
    DATE_OF_BIRCH)
values ('Стивен Эдвин Кинг', '1947-09-21'); --2

insert into AUTHORS (
    FIO,
    DATE_OF_BIRCH)
values ('Пелевин В О', '1962-11-22'); --3

insert into GENRES (NAME) values ('Ужасы'); --1
insert into GENRES (NAME) values ('Классика'); --2
insert into GENRES (NAME) values ('Повесть'); --3

insert into BOOKS (
    NAME,
    ISBN,
    AUTHOR_ID,
    GENRE_ID)
values ('Преступление и наказание', 'ISBN-00001', 1, 2);

insert into BOOKS (
    NAME,
    ISBN,
    AUTHOR_ID,
    GENRE_ID)
values ('Оно', 'ISBN-00023', 2, 1);

insert into BOOKS (
    NAME,
    ISBN,
    AUTHOR_ID,
    GENRE_ID)
values ('Желтая стрела', 'ISBN-00027', 3, 3);
