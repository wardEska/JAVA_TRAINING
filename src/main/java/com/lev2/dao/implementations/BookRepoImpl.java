package com.lev2.dao.implementations;

import com.lev2.dao.base.JPQLRepoImpl;
import com.lev2.dao.repositories.BookRepo;
import com.lev2.entity.Book;
import com.lev2.entity.base.BaseEntity;
import com.lev2.entity.dto.base.BaseEntityDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepoImpl extends JPQLRepoImpl<Integer, Book, Book> implements BookRepo {
    public BookRepoImpl(EntityManager entityManager) {
        super(entityManager, Book.class);
    }

    @Override
    protected Book mapEntityToDto(Book item) {
        return item;
    }

    @Override
    protected Book mapDtoToEntity(Book item) {
        return item;
    }
}
