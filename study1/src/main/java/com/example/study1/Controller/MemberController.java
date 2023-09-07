package com.example.study1.Controller;

import com.example.study1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//Controller annotation이 있으면 자동으로 스프링빈에 등록됨

@Controller
public class MemberController {
    private MemberService meberService;

    //생성자를 사용해 MemberService class를 container에 등록
    @Autowired
    //container에 있는 memberService를 가져와 controller와 연결시켜줌 -> Dependency Injection
    public MemberController(MemberService meberService) {
        this.meberService = meberService;
    }

}
