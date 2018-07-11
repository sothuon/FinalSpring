package com.spring.springt3.repositories;

import com.github.javafaker.Faker;
import com.spring.springt3.models.Book;
import com.spring.springt3.models.filters.BookFilter;
import com.spring.springt3.repositories.providers.BookProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BookRepository {

    @SelectProvider(type = BookProvider.class, method = "getAllProvider")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "cate_id", property = "category.id"),
            @Result(column = "name", property = "category.name")
    })// use to bind column name and field oy know each other
    List<Book> getAll();

    @SelectProvider(type = BookProvider.class, method = "bookFilterProvider")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "cate_id", property = "category.id"),
            @Result(column = "name", property = "category.name")
    })
    List<Book> bookFilter(BookFilter filter);

    @Select("select * from book_tbl b inner join category_tbl c ON b.cate_id = c.id where b.id=#{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "cate_id", property = "category.id"),
            @Result(column = "name", property = "category.name")
    })
    Book findOne(@Param("id") Integer id);

    @Update("update book_tbl set title=#{title}, author=#{author}, publisher=#{publisher}, thumbnail=#{thumbnail} where id=#{id}")
    boolean update(Book book);

    @Delete("delete from book_tbl where id=#{id}")
    boolean delete(int id);

    @InsertProvider(type = BookProvider.class, method = "createProvider")
    boolean create(Book book);

   /* Faker faker = new Faker();
    List<Book> bookList = new ArrayList<>();

    {
        for (int i = 1; i < 11; i++) {
            Book book = new Book();
            book.setId(i);
            book.setTitle(faker.book().title());
            book.setAuthor(faker.book().author());
            book.setPublisher(faker.book().publisher());
            bookList.add(book);
        }
    }

    public List<Book> getAll() {
        return this.bookList;
    }

    public Book findOne(Integer id) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId() == id) {
                return bookList.get(i);
            }
        }
        return null;
    }

    public boolean update(Book book) {
        for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getId() == book.getId()) {
                    bookList.set(i, book);
                    return true;
                }
            }
            return false;
        }

    public boolean delete(int id){
            for (int i = 0; i <bookList.size() ; i++) {
               if (bookList.get(i).getId()==id){
                   bookList.remove(i);
                   return true;
               }
            }
            return false;
    }

    public boolean create(Book book){
        return bookList.add(book);
    }*/
}
