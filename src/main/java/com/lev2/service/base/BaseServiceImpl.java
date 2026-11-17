package com.lev2.service.base;

import com.lev2.dao.base.BaseRepo;
import com.lev2.common.dto.PagedResult;
import com.lev2.entity.base.BaseEntity;
import com.lev2.common.exception.NotFoundException;
import com.lev2.entity.dto.base.BaseEntityDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

public class BaseServiceImpl<
        K ,
        T extends BaseEntity<K>,
        DTO extends BaseEntityDto>
        implements BaseService<K,T, DTO> {

    protected final BaseRepo<K,T,DTO> repo;

    public BaseServiceImpl(BaseRepo<K,T,DTO> repo) {
        this.repo = repo;
    }
    @Override
    @Transactional
    public DTO insert(DTO item){
        return this.repo.insert(item);
    }
    @Override
    @Transactional
    public void update(DTO item) throws NotFoundException {
        this.repo.update(item);
    }

    @Transactional
    @Override
    public void partialUpdate(K id ,DTO item) throws NotFoundException {
        this.repo.partialUpdate(id , item);
    }

    @Override
    public DTO getById(K id){
        return  this.repo.getById(id);
    }

    @Override
    public List<DTO> findAll() {
        return this.repo.findAll();
    }

    @Override
    public PagedResult<DTO> search(Map<String, Object> filters) {
        return this.repo.search(filters);
    }

    @Override
    public long getCount(Map<String, Object> filters) {
        return this.repo.getCount(filters);
    }

    @Override
    public boolean checkExists(K id){
        return this.repo.checkExists(id);
    }

    @Transactional
    @Override
    public void delete(K id) throws NotFoundException {
        this.repo.delete(id);
    }

}
