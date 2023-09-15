package com.example.study1.service;

import com.example.study1.domain.Member;
import com.example.study1.repository.MemberRepository;
import com.example.study1.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    @Rollback(false)
    //db에 커밋
    void join() {
        //given - 주어졌을 때
        Member member = new Member();
        member.setName("Bob");

        //when -
        long id = memberService.join(member);

        //then - 결과
        Member findMember = memberRepository.findByid(id).get();
        assertEquals(member.getName(), findMember.getName());    }

    @Test
    public void 중복회원예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //IllegalStateException error가 생겼을 때 오른쪽 코드를 실행하도록 함

    }

}