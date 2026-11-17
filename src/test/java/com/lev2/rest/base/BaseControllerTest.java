package com.lev2.rest.base;

import com.lev2.common.dto.PagedResult;
import com.lev2.entity.Book;
import com.lev2.service.base.BaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BaseControllerTest {
    @Mock
    BaseService<Integer , Book , Book> service;
    @Mock Book book;
    @Mock Book dto;
    @Mock
    private PagedResult<Book> pagedResult;

    private BaseController<Integer, Book, Book> controller;


    @BeforeEach
    void setup(){
        controller = new BaseController<>(service);
    }

    @Test
    void getAll_shouldReturnAllItems(){
        var expected = List.of(dto);
        when(service.findAll()).thenReturn(expected);

        var result = controller.getAll();

        assertNotNull(result);

        assertEquals(expected , result);

        verify(service, times(1)).findAll();

    }

    @Test
    void get_shouldReturnPagedResult(){

        Map<String, Object> params = Map.of(
                "page", 1,
                "pageSize", 10,
                "orderBy", "id"
        );

        when(service.search(params)).thenReturn(pagedResult);

        var result = controller.get(params);

        assertNotNull(result);

        assertEquals(pagedResult , result);

        verify(service, times(1)).search(params);

    }

}