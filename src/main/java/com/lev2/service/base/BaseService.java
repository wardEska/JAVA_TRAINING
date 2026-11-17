package com.lev2.service.base;

import com.lev2.common.dto.PagedResult;
import com.lev2.entity.base.BaseEntity;
import com.lev2.common.exception.NotFoundException;
import com.lev2.entity.dto.base.BaseEntityDto;

import java.util.List;
import java.util.Map;

public interface BaseService<K,T extends BaseEntity<K>, DTO extends BaseEntityDto> {
    DTO insert(DTO item);
    void update(DTO item) throws NotFoundException;
    void partialUpdate(K id , DTO item) throws NotFoundException;
    DTO getById(K id);
    List<DTO> findAll();
    PagedResult<DTO> search(Map<String , Object> filters);
    long getCount(Map<String , Object> filters);
    boolean checkExists(K id);
    void delete(K id) throws NotFoundException;
}
