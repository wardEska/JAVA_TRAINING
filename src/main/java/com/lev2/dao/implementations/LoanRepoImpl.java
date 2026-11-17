package com.lev2.dao.implementations;

import com.lev2.dao.base.JPQLRepoImpl;
import com.lev2.dao.repositories.LoanRepo;
import com.lev2.entity.Book;
import com.lev2.entity.Loan;
import com.lev2.entity.Member;
import com.lev2.entity.dto.LoanDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;


@Repository
public class LoanRepoImpl extends JPQLRepoImpl<Integer , Loan, LoanDto> implements LoanRepo {
    public LoanRepoImpl(EntityManager entityManager) {
        super(entityManager, Loan.class);
    }



    @Override
    protected LoanDto mapEntityToDto(Loan item) {
        return new LoanDto(item.getId(),
                item.getBook().getId(),
                item.getBook().getTitle(),
                item.getMember().getId(),
                item.getMember().getName(),
                item.getLoanDate(),
                item.getDueDate(),
                item.getReturnDate());
    }

    @Override
    protected Loan mapDtoToEntity(LoanDto item) {
        Loan loan = new Loan();
        if(item.returnDate() != null)
            loan.setReturnDate(item.returnDate());

        if(item.dueDate() != null)
            loan.setDueDate(item.dueDate());

        if(item.loanDate() != null)
            loan.setLoanDate(item.loanDate());

        if(item.bookId() != null)
            loan.setBook(entityManager.getReference(Book.class , item.bookId()));

        if(item.memberId() != null)
            loan.setMember(entityManager.getReference(Member.class , item.memberId()));

        if(item.id() != null)
            loan.setId(item.id());
        return loan;
    }
}
