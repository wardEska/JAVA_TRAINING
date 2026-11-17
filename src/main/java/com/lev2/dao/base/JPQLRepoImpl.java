package com.lev2.dao.base;

import com.lev2.common.dto.PagedResult;
import com.lev2.common.exception.InvalidOperationException;
import com.lev2.entity.base.BaseEntity;
import com.lev2.common.exception.NotFoundException;
import com.lev2.entity.dto.base.BaseEntityDto;
import com.lev2.helper.QueryBuilder;
import com.lev2.helper.QueryKeyWords;
import com.lev2.helper.ReflectionBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JPQLRepoImpl<K , T extends BaseEntity<K>, DTO extends  BaseEntityDto> implements BaseRepo<K,T, DTO> {

    protected String aliasName;

    protected EntityManager entityManager;

    protected final Class<T> entityClass;

    public JPQLRepoImpl(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        aliasName = entityClass.getSimpleName().substring(0,1);

    }

    @Override
    public DTO insert(DTO dto) throws InvalidOperationException {
        var entity = this.mapDtoToEntity(dto);
        if(entity.getId() != null){
            throw new InvalidOperationException("The id should be null on inserting");
        }
        entityManager.persist(entity);

        return this.mapEntityToDto(entity);
    }


    @Override
    public void update(DTO dto) throws NotFoundException {
        var item = this.mapDtoToEntity(dto);
        var entity = getById(item.getId());
        if(entity == null) {
            throw new NotFoundException("The entity not found");
        }

        var properties = ReflectionBuilder.getAllProperties(entityClass);

        StringBuilder queryBuilder = new StringBuilder("Update " + entityClass.getSimpleName() + " " + aliasName );

        Map<String,Object> parameters = new HashMap<>();

        properties.forEach((property->{
            if(ReflectionBuilder.isPrimaryKey(property)){
                return;
            }
            Object value = ReflectionBuilder.getPropertyValue(property , item);
                if(parameters.isEmpty()){
                    queryBuilder.append(" Set ");
                }else{
                    queryBuilder.append(" , ");
                }
                queryBuilder.append("  " + property.getName() + " = :"+property.getName() + " ");
                parameters.put(property.getName() , value);
        }));

        if(parameters.isEmpty()){
            return;
        }

        queryBuilder.append("Where "+aliasName + ".id = :id");
        parameters.put("id",item.getId());

        var query = entityManager.createQuery(queryBuilder.toString());
        parameters.forEach(query :: setParameter);
        query.executeUpdate();
    }

    @Override
    public void partialUpdate(K id, DTO dto) throws NotFoundException {
        var item = this.mapDtoToEntity(dto);
        var entity = getById(id);
        if(entity == null) {
            throw new NotFoundException("The entity not found");
        }
        item.setId(id);

        List<Field> properties = ReflectionBuilder.getAllProperties(entityClass);

        StringBuilder queryBuilder = new StringBuilder("Update " + entityClass.getSimpleName() + " " + aliasName );

        Map<String,Object> parameters = new HashMap<>();

        properties.forEach((property->{
            if(ReflectionBuilder.isPrimaryKey(property)){
                return;
            }
            var value = ReflectionBuilder.getPropertyValue(property , item);
            if(value != null && value != ""){
                if(parameters.isEmpty()){
                    queryBuilder.append(" Set ");
                }else{
                    queryBuilder.append(" , ");
                }
                queryBuilder.append("  " + property.getName() + " = :"+property.getName() + " ");
                parameters.put(property.getName() , value);
            }
        }));

        if(parameters.isEmpty()){
            return;
        }

        queryBuilder.append("Where "+aliasName + ".id = :id");
        parameters.put("id",item.getId());

        var query = entityManager.createQuery(queryBuilder.toString());
        parameters.forEach(query :: setParameter);
        query.executeUpdate();
    }

    @Override
    public DTO getById(K id) throws NotFoundException {
        var item = entityManager.find(entityClass , id);
        if(item == null)
            throw new NotFoundException("The item not found - " + id);
        return this.mapEntityToDto(item);
    }

    @Override
    public List<DTO> findAll() {
        var items = entityManager.createQuery(QueryKeyWords.FROM + " " + entityClass.getSimpleName(), entityClass).getResultList();
        return items.stream().map(x->this.mapEntityToDto(x)).toList();
    }

    @Override
    public PagedResult<DTO> search(Map<String, Object> filters) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(QueryKeyWords.SELECT);
        queryBuilder.append(" " + aliasName +" " + QueryKeyWords.FROM + " " + entityClass.getSimpleName() + " " + aliasName);

        Map<String, Object> params = buildWhereQuery(filters , queryBuilder);

        queryBuilder.append(getOrderByQuery(filters));

        TypedQuery<T> query = entityManager.createQuery(queryBuilder.toString() , entityClass);

        params.forEach(query :: setParameter);

        int pageSize = QueryBuilder.getPageSize(filters);
        int page = QueryBuilder.getPageNumber(filters);
        int offset = QueryBuilder.getOffset(page , pageSize);

        List<T> list = query.setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();

        long count = getCount(filters);

        return new PagedResult<>(page,pageSize,count,list.stream().map(x->this.mapEntityToDto(x)).toList());
    }

    @Override
    public long getCount(Map<String, Object> filters) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(QueryKeyWords.SELECT);
        queryBuilder.append("\nCount(" + aliasName + ") FROM "+entityClass.getSimpleName() + " " + aliasName);

        Map<String,Object> params = buildWhereQuery(filters , queryBuilder);

        TypedQuery<Long> query = entityManager.createQuery(queryBuilder.toString()  , Long.class);
        params.forEach(query :: setParameter);

        return query.getSingleResult();
    }

    @Override
    public boolean checkExists(K id) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(QueryKeyWords.SELECT);
        queryBuilder.append("\nCount(" + aliasName + ") FROM "+entityClass.getSimpleName() + " " + aliasName);

        var primaryKeyProp = ReflectionBuilder.getPrimaryProperty(entityClass);

        queryBuilder.append(" where " + primaryKeyProp.getName() + " = :"+primaryKeyProp.getName());


        TypedQuery<Long> query = entityManager.createQuery(queryBuilder.toString()  , Long.class)
                .setParameter(primaryKeyProp.getName() , id);

        return query.getSingleResult() > 0;
    }

    @Override
    public void delete(K id) throws NotFoundException{
        var primary = ReflectionBuilder.getPrimaryProperty(entityClass);
        String query = "DELETE FROM " + entityClass.getSimpleName() + " " + aliasName
                + " where "
                + aliasName
                + "."
                + primary.getName()
                + " = :"
                +primary.getName();

        entityManager.createQuery(query).setParameter(primary.getName(),id).executeUpdate();
    }

    protected String getOrderByQuery(Map<String, Object> filters){
        var orderBy = QueryBuilder.getOrderBy(filters);
        if(orderBy == null){
            return "";
        }
        var orderDir = QueryBuilder.getOrderDir(filters);
        return  "\nOrder by "+ orderBy + " " +orderDir+"\n";
    }

    protected Map<String, Object> buildWhereQuery(Map<String, Object> filters, StringBuilder queryBuilder) {
        queryBuilder.append("\nWhere 1=1\n");
        Map<String, Object> params = new HashMap<>();
        filters.keySet().forEach((k)->{
            if(filters.get(k) != null){
                String propertyName = ReflectionBuilder.getPropertyName(entityClass ,k);
                if(propertyName != null){
                    queryBuilder.append("and " +aliasName +"."+propertyName +"=:"+k + "\n");
                    params.put(k , filters.get(k));
                }
            }
        });
        return params;
    }

    protected abstract DTO mapEntityToDto(T item);
    protected abstract T mapDtoToEntity(DTO item);

}
