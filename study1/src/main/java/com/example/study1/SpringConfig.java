package com.example.study1;

import com.example.study1.repository.MemberRepository;
import com.example.study1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //SpringDataJpa가 만든 구현체가 등록됨

    @Bean
    //Bean에 직접 class를 등록한다는 의미
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    /*
    @Bean
    public MemberRepository memberRepository() {

       // return new MemoryMemberRepository();
       // return new JdbcMemberRepository(dataSource);
        //return new JpaMemberRepository(em);
    } */
}
