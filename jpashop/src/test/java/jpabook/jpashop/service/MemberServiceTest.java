package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() throws Exception{

        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveid = memberService.join(member);

        //then
        Assert.assertEquals(member, memberRepository.findOne(saveid));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원예외()throws Exception{
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("kim1");
        member2.setName("kim1");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 함

        //then
        fail("예외가 발생해야 합니다");
        //해당 코드 전에 예외가 터졌으므로 핸들링이 필요함을 명시
    }

}