package com.lev2.dao.base;

import com.lev2.common.dto.PagedResult;
import com.lev2.entity.base.BaseEntity;
import com.lev2.common.exception.NotFoundException;
import com.lev2.helper.QueryBuilder;
import com.lev2.helper.QueryKeyWords;
import com.lev2.helper.ReflectionBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RepoImpl<K , T extends BaseEntity<K>> {
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    public RepoImpl(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }


    public void insert(T item) {
        this.entityManager.persist(item);
    }

    public void update(T item) throws NotFoundException {
        var entity = getById(item.getId());
        if(entity == null)
            throw new NotFoundException("The entity not found");
        entityManager.merge(item);
    }

    public T getById(K id) {
        return this.entityManager.find(entityClass,id);
    }
    public List<T> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }
    public PagedResult<T> search(Map<String , Object> filters){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> cq = cb.createQuery(entityClass);

        Root<T> root = cq.from(entityClass);

        List<Predicate> predicates = buildPredicates(filters,cb , root);


        buildOrderBy(filters, cq, cb, root);

        cq.select(root).where(predicates.toArray(new Predicate[0]));

        int page = QueryBuilder.getPageNumber(filters);
        int pageSize = QueryBuilder.getPageSize(filters);
        int offset = QueryBuilder.getOffset(page , pageSize);


        List<T> res =  entityManager
                .createQuery(cq)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();

        var count = getCount(filters);

        return new PagedResult<>(page, pageSize,count, res);


    }


    public long getCount(Map<String , Object> filters){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<T> root = cq.from(entityClass);

        List<Predicate> predicates = buildPredicates(filters, cb, root);

        cq.select(cb.count(root))
                .where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .getSingleResult();
    }
    public void delete(K id) throws NotFoundException {
        var item = getById(id);
        if(item == null)
            throw new NotFoundException("The item not found");

        entityManager.remove(item);
    }

    public List<Predicate> buildPredicates(Map<String , Object> filters, CriteriaBuilder cb, Root<T> root){
        List<Predicate> predicates = new ArrayList<>();

        filters.forEach((key, value) -> {
            var propertyName = ReflectionBuilder.getPropertyName(entityClass, key);

            if (value != null && propertyName != null) {
                predicates.add(cb.equal(root.get(propertyName), value));
            }
        });
        return predicates;
    }

    private void buildOrderBy(Map<String, Object> filters, CriteriaQuery<T> cq, CriteriaBuilder cb, Root<T> root) {
        var orderBy = QueryBuilder.getOrderBy(filters);
        if(orderBy == null)
            return;
        var orderByProperty = ReflectionBuilder.getPropertyName(entityClass,orderBy);
        if(orderByProperty == null)
            return;

        var orderDir = QueryBuilder.getOrderDir(filters);
        if(orderDir.equalsIgnoreCase(QueryKeyWords.ASC)){
            cq.orderBy(cb.asc(root.get(orderByProperty)));
        }else{
            cq.orderBy(cb.desc(root.get(orderByProperty)));
        }

    }

}
