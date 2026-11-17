package com.lev2.rest.base;

import com.lev2.common.dto.ApiResponse;
import com.lev2.common.dto.PagedResult;
import com.lev2.entity.base.BaseEntity;
import com.lev2.entity.dto.base.BaseEntityDto;
import com.lev2.service.base.BaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public class BaseController<K , T extends BaseEntity<K> , DTO extends BaseEntityDto>  {
    protected final BaseService<K,T,DTO> service;

    public BaseController(BaseService<K,T,DTO> service) {
        this.service = service;
    }
    @GetMapping("all")
    public List<DTO> getAll(){
        return this.service.findAll();
    }
    @GetMapping("")
    public PagedResult<DTO> get(@RequestParam Map<String , Object> params){
        return this.service.search(params);
    }
    @GetMapping("{id}")
    public DTO get(@PathVariable K id){
        return this.service.getById(id);
    }

    @PostMapping("")
    public ApiResponse<DTO> post(@RequestBody DTO item){
        return ApiResponse.created("Created Successfully" ,this.service.insert(item) );
    }

    @PutMapping("")
    public ApiResponse<Integer> put(@RequestBody DTO item){
        this.service.update(item);
        return ApiResponse.success("Updated Successfully");
    }

    @PatchMapping("{id}")
    public ApiResponse<Integer> patch(@PathVariable K id ,  @RequestBody DTO item){
        this.service.partialUpdate(id,item);
        return ApiResponse.success("Updated Successfully");
    }


    @DeleteMapping("{id}")
    public ApiResponse<Integer> delete(@PathVariable K id){
        this.service.delete(id);
        return ApiResponse.noContent();
    }

}
