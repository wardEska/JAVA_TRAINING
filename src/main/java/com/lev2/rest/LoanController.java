package com.lev2.rest;

import com.lev2.entity.Loan;
import com.lev2.entity.dto.LoanDto;
import com.lev2.rest.base.BaseController;
import com.lev2.service.services.LoanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("loans")
public class LoanController extends BaseController<Integer , Loan, LoanDto> {
    public LoanController(LoanService service) {
        super(service);
    }
}
