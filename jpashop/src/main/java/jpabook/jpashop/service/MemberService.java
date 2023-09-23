package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor //생성자 만들어줌
@Transactional
 public class MemberService {

    //@Autowired
    private final MemberRepository memberRepository;
    //field injection

    /**회원 가입**/
    public Long join(Member member){
        //중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
        //pk값에 id가 들어가므로 값이 있음이 보장됨
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        //repo에서 매개변수의 이름과 동일한 이름이 있는지 확인

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니.");
        }
    }

    /**회원 조회**/
    @Transactional(readOnly = true) //조회 관련 기능에서 해당 anno를 사용하면 기능이 최적화됨
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberid){
        return memberRepository.findOne(memberid);
    }

}
