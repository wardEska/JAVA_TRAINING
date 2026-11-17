package com.lev2.service.implementations;

import com.lev2.dao.repositories.MemberRepo;
import com.lev2.entity.Member;
import com.lev2.service.base.BaseServiceImpl;
import com.lev2.service.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Integer , Member, Member> implements MemberService {
    @Autowired
    public MemberServiceImpl(MemberRepo repo) {
        super(repo);
    }
}
