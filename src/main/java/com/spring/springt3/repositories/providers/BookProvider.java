package com.spring.springt3.repositories.providers;

import com.spring.springt3.models.Book;
import com.spring.springt3.models.filters.BookFilter;
import org.apache.ibatis.jdbc.SQL;

public class BookProvider {

    public String getAllProvider(){
        return new SQL(){{
            SELECT("*");
            FROM("book_tbl b");
            INNER_JOIN("category_tbl c ON b.cate_id = c.id");
            ORDER_BY("b.id desc");
        }}.toString();
    }
    public String createProvider(Book book){
        return new SQL(){{
            INSERT_INTO("book_tbl");
            VALUES("title","#{title}");
            VALUES("author","#{author}");
            VALUES("publisher","#{publisher}");
            VALUES("thumbnail","#{thumbnail}");
            VALUES("cate_id", "#{category.id}");
        }}.toString();
    }

    public String bookFilterProvider(BookFilter bookFilter){
        return new SQL(){{
            SELECT("*");
            FROM("book_tbl b");
            INNER_JOIN("category_tbl c ON b.cate_id = c.id");

            if(bookFilter.getCateId() != null)
                WHERE("c.id = #{cateId}");

            if (bookFilter.getBookTitle() !=null)
                WHERE("b.title iLIKE '%' || #{bookTitle} || '%'");

            ORDER_BY("b.id desc");

        }}.toString();
    }
}
