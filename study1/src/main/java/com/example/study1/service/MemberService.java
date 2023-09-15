package com.example.study1.service;

import com.example.study1.domain.Member;
import com.example.study1.repository.MemberRepository;
import com.example.study1.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


//MemberService를 스프링빈에 등록하기 위해 @Service 사용
@Transactional
public class MemberService {

    MemberRepository memberRepository;

    @Autowired //DI(Dependency Injection)
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }


    //회원가입
    public long join(Member member){
        //같은 이름이 있는 중복 회원X
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    //회원 중복을 검증한 후 통과하면 repo에 저장하고 id를 반환


    //ctrl + t로 extract Method 기능 사용해 함수를 따로 생성
    private void validateDuplicateMember(Member member) {
        memberRepository.findByname(member.getName())
        .ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        //ifPresent : 값이 있으면(null이 아니면) 해당 로직 실행
        //result가 Optional이기 때문에 가능 / 아니면 (result != null) 조건문
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 조회
    public Optional<Member> findOne(long memberId){
        return memberRepository.findByid(memberId);
    }
}
