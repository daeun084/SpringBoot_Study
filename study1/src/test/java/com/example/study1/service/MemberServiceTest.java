package com.example.study1.service;

import com.example.study1.domain.Member;
import com.example.study1.repository.MemberRepository;
import com.example.study1.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemoryMemberRepository memoryMemberRepository;
    MemberService memberService;

    @BeforeEach
    public void beforeach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }
    //각 테스트 전 독립적으로 실행되는 구문
    //원활한 테스트를 위해 MemberService 클래스에 생성자 만들어 memoryMemberRepository 넘겨줌

    @AfterEach
    public void aftereach(){
       memoryMemberRepository.clearstore();
    }
    //메소드 실행이 끝날 때마다 실행되는 메소드


    @Test
    void join() {
        //given - 주어졌을 때
        Member member = new Member();
        member.setName("Bob");

        //when -
        long id = memberService.join(member);

        //then - 결과
        Member findName = memberService.findOne(id).get();
        Assertions.assertThat(member.getName()).isEqualTo(findName.getName());
    }

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

        /*
        try {
            memberService.join(member2);
            fail("예외가 발생해야 합니다");
            //비정상작동
        }catch (IllegalStateException e){
            //예외가 생기면 해당 구문 실행 -> 정상작동
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. 123123")
        }
         */

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}