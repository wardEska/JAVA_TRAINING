package com.lev2.rest;

import com.lev2.entity.Member;
import com.lev2.rest.base.BaseController;
import com.lev2.service.services.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("members")
public class MemberController extends BaseController<Integer , Member, Member> {
    public MemberController(MemberService service) {
        super(service);
    }
}
