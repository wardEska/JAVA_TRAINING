package com.lev2.service.implementations;

import com.lev2.dao.repositories.BookRepo;
import com.lev2.entity.Book;
import com.lev2.service.base.BaseServiceImpl;
import com.lev2.service.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends BaseServiceImpl<Integer , Book, Book> implements BookService {
    @Autowired
    public BookServiceImpl(BookRepo repo) {
        super(repo);
    }
}
