package com.example.study1.repository;

import com.example.study1.domain.Member;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

//MemoryMemberRepository class를 test하기 위한 class 생성
public class MemoryMemberRepositoryTest {

    //test할 class에 대한 객체 생성
    MemoryMemberRepository repository = new MemoryMemberRepository();


    @AfterEach
    public void aftereach(){
        repository.clearstore();
    }
    //메소드 실행이 끝날 때마다 실행되는 메소드




    //@Test를 쓰고 메소드를 작성하면 해당 메소드가 test로 실행
    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        //검증을 위함
        //optional에서 값을 꺼낼 때 get() 사용
        Member result1 = repository.findByid(member.getId()).get();
        //System.out.println("result : " + (result1 == member));

        //jupiter assertions
      // Assertions.assertEquals(result1, member);
      // assertThat(member).isEqualTo(result1);

    }

    @Test
    public void findbyName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByname("spring2").get();
        Assertions.assertThat(result).isEqualTo(member1);


    }

}
