package com.spring.springt3.controllers;

import com.spring.springt3.models.Book;
import com.spring.springt3.models.Category;
import com.spring.springt3.services.BookService;
import com.spring.springt3.services.CategoryService;
import com.spring.springt3.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class BookController {
    private BookService bookService;
    private CategoryService categoryService;


    @Autowired
    private UploadService uploadService;

    @Autowired
    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/index", "/", "/home"})
    public String index(Model model){
        List<Book> bookList= this.bookService.getAll();
        model.addAttribute("books",bookList);
        return "book/index";
    }

    @GetMapping("view/{id}")
    public String view(@PathVariable("id") Integer id, ModelMap modelMap){
        Book book = this.bookService.findOne(id);
        modelMap.addAttribute("book",book);
        return "book/view_detail";
    }

    @GetMapping("/update/{book_id}")
    public String showFormUpdate(@PathVariable Integer book_id,ModelMap modelMap){
        Book book = this.bookService.findOne(book_id);
        List<Category> categories = this.categoryService.getAll();
        modelMap.addAttribute("isNew",false);
        modelMap.addAttribute("book",book);
        modelMap.addAttribute("categories",categories);
        return "book/create-book";
    }

    @PostMapping("update/submit")
    public String updateSubmit(@ModelAttribute("book") Book book, MultipartFile file){
        String filename = this.uploadService.singleFileUpload(file, "room/");
        if (!file.isEmpty()){
            book.setThumbnail("/images-btb/" + filename);
        }
        this.bookService.update(book);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        this.bookService.delete(id);
        return "redirect:/index";
    }

    @GetMapping("/create")
    public String create(ModelMap modelMap){
        List<Category> categories = this.categoryService.getAll();
        modelMap.addAttribute("isNew",true);
        modelMap.addAttribute("categories",categories);
        modelMap.addAttribute("book",new Book());
        return "book/create-book";
    }

    @PostMapping("/create/submit")
    public String create(@Valid Book book, BindingResult bindingResult, MultipartFile file, Model model){
        String filename = this.uploadService.upload(file,"room/");

        book.setThumbnail("images-btb/" + filename);


        if (bindingResult.hasErrors()){
            model.addAttribute("isNew",true);
            return "book/create-book";
        }
       this.bookService.create(book);
        return "redirect:/index";
    }

    @GetMapping("/test-multi-upload")
    public String showUpload(){
        return "book/upload-file";
    }

    @PostMapping("/test-multi-upload/submit")
    public String testMultipleFileUpload(@RequestParam("file")List<MultipartFile> files){

        List<String> filenames = this.uploadService.upload(files,"test/");

        return "book/upload-file";
    }
}
