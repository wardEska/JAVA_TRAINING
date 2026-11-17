package com.lev2.dao.implementations;

import com.lev2.dao.base.JPQLRepoImpl;
import com.lev2.dao.repositories.MemberRepo;
import com.lev2.entity.Book;
import com.lev2.entity.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepoImpl extends JPQLRepoImpl<Integer , Member, Member> implements MemberRepo {
    public MemberRepoImpl(EntityManager entityManager) {
        super(entityManager, Member.class);
    }

    @Override
    protected Member mapEntityToDto(Member item) {
        return item;
    }

    @Override
    protected Member mapDtoToEntity(Member item) {
        return item;
    }
}
