create table category_tbl(
id serial primary key,
name varchar
);
create table book_tbl(
  id serial primary key,
  title varchar,
  author varchar,
  publisher varchar,
  thumbnail varchar,
  cate_id int references category_tbl(id)
);