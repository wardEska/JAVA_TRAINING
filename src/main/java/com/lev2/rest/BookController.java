package com.lev2.rest;

import com.lev2.entity.Book;
import com.lev2.rest.base.BaseController;
import com.lev2.service.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController extends BaseController<Integer , Book, Book> {
    @Autowired
    public BookController(BookService service) {
        super(service);
    }
}
