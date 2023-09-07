package com.example.study1.repository;

import com.example.study1.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    //Member type 객체를 파라미터로 받아 반환

    //Optional - 객체가 null일 경우를 대비
    Optional<Member> findByid(Long id);
    //id로 찾기
    Optional<Member> findByname(String name);
    //name으로 찾기

    List<Member> findAll();
}
