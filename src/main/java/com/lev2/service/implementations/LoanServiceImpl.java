package com.lev2.service.implementations;

import com.lev2.common.exception.NotFoundException;
import com.lev2.dao.repositories.BookRepo;
import com.lev2.dao.repositories.LoanRepo;
import com.lev2.dao.repositories.MemberRepo;
import com.lev2.entity.Loan;
import com.lev2.entity.dto.LoanDto;
import com.lev2.service.base.BaseServiceImpl;
import com.lev2.service.services.LoanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoanServiceImpl extends BaseServiceImpl<Integer , Loan, LoanDto> implements LoanService {
    private final BookRepo bookRepo;
    private final MemberRepo memberRepo;
    @Autowired
    public LoanServiceImpl(LoanRepo repo , MemberRepo memberRepo , BookRepo bookRepo) {
        super(repo);
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
    }

    @Transactional
    @Override
    public LoanDto insert(LoanDto loan){
        this.canLoad(loan);
        return super.insert(loan);
    }


    @Transactional
    @Override
    public void update(LoanDto loan){
        this.canLoad(loan);
        super.update(loan);
    }
    @Transactional
    @Override
    public void partialUpdate(Integer id , LoanDto loan){
        this.canLoad(loan);
        super.partialUpdate(id , loan);
    }

    public void canLoad(LoanDto loan){
        if(loan.bookId() != null){
            var isBookExists = this.bookRepo.checkExists(loan.bookId());
            if(!isBookExists)
                throw new NotFoundException("No book found for id : " + loan.bookId());
        }
        if(loan.memberId() != null){
            var isMemberExists = this.memberRepo.checkExists(loan.memberId());
            if(!isMemberExists)
                throw new NotFoundException("No Member found for id : " + loan.memberId());
        }

    }

}
