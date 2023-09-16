package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
//Spring과 관련된 테스트를 할 것임을 JUnit에게 알려줌
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    //injection

   @Test
   @Transactional
   @Rollback(false)
   //EntityManeger을 통한 모든 데이터 변경은 모두 Transactional 안에서 이루어져야 함
    public void TestMember() throws Exception {
       Member member = new Member();
       member.setUsername("MemberA");
       Long saveid = memberRepository.save(member);
       //Member 객체 생성 후 "MemberA"를 username으로 추가
       //MemberRepo에 member 객체 추가 후 id를 저

       Member findMember = memberRepository.find(saveid);


       Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
       Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

   }

}