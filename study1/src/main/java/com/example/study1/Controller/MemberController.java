package com.example.study1.Controller;

import com.example.study1.domain.Member;
import com.example.study1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.List;


//Controller annotation이 있으면 자동으로 스프링빈에 등록됨

@Controller
public class MemberController {
    private MemberService memberService;

    //생성자를 사용해 MemberService class를 container에 등록
    @Autowired
    //container에 있는 memberService를 가져와 controller와 연결시켜줌 -> Dependency Injection
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/members/new")
    public String CreateForm(){
        return "members/createMemberForm";
        //members라는 direc을 만든 후 html 파일들 저장
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        //Member 객체를 만든 후 form의 이름으로 name 설정 후 MemberService에 member 넘김
        return "redirect:/";
        //회원가입이 끝난 후 홈화면으로 돌아감
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
