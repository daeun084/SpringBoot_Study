package com.example.study1.repository;

import com.example.study1.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository{

    //실제 정보 저장을 위해 map 객체 생성 / Key : id, Value : Member type 정보들
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        //store에 member 정보 저장하기 전에 ID 저장(sequence 사용)
        member.setId(++sequence);
        //store에 정보 저장(id, member)
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findByid(Long id) {
        return Optional.ofNullable(store.get(id));
        //반환값이 null일 경우가 있으면 null로 감싸서 return
    }

    @Override
    public Optional<Member> findByname(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                //member의 name이 파라미터로 들어온 name과 같은지 비교(filter)
                .findAny();
        //루프를 돌다가 하나라도 결과를 찾으면 바로 반환함
        //끝까지 찾았는데 일치하는 결과가 없으면 null이 없으면 optional에 감싸서 반환함
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        //store의 value들을 list 형태로 바꾸어 반환
    }

    public void clearstore(){
        store.clear();
    }
}
