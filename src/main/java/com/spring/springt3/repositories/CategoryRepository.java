package com.spring.springt3.repositories;

import com.spring.springt3.models.Category;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository {

    @Select("select * from category_tbl")
    List<Category> getAll();
}
