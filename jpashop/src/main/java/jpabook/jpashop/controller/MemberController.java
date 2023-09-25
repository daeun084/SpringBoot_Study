package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        //validation이라도 하기 위해 빈 껍데기라도 만들어서 가져감
        return "members/createMemberForm";
    }

    @PostMapping("members/new") //데이터를 실제로 등록하기 위한 맵핑
    //createMemberForm의 post method에 활
    public String create(@Valid MemberForm form, BindingResult result){
        //Valid annotation을 통해 form에 있는 validation 기능을 사용함을 명시
        //validate해서 생긴 오류가 result에 담김
        if(result.hasErrors()){
            return "members/createMemberForm";
        } //에러가 있으면 다시 html 호출
        //name을 제외한 나머지 값들이 인자로 들어오기 때문에 다시 로드해도 데이터는 유지


        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);

        return "redirect:/"; //리다이렉로 첫번째 페이지로 넘어감
    }


    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
