package com.lev2.dao.base;

import com.lev2.common.dto.PagedResult;
import com.lev2.common.exception.InvalidOperationException;
import com.lev2.entity.base.BaseEntity;
import com.lev2.common.exception.NotFoundException;
import com.lev2.entity.dto.base.BaseEntityDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface BaseRepo<K, T extends BaseEntity<K>, DTO extends BaseEntityDto>  {
    DTO insert(DTO dto) throws InvalidOperationException;
    void update(DTO dto) throws NotFoundException;
    void partialUpdate(K id , DTO dto) throws NotFoundException;
    DTO getById(K id) throws NotFoundException;
    List<DTO> findAll();
    PagedResult<DTO> search(Map<String , Object> filters);
    long getCount(Map<String , Object> filters);
    boolean checkExists(K id);
    void delete(K id) throws NotFoundException;

}
