package com.lev2.entity.dto;

import com.lev2.entity.dto.base.BaseEntityDto;

import java.time.LocalDate;

public record LoanDto(Integer id,
                      Integer bookId,
                      String bookTitle,
                      Integer memberId,
                      String memberName,
                      LocalDate loanDate,
                      LocalDate dueDate,
                      LocalDate returnDate) implements BaseEntityDto
{
}
