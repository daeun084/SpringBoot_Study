package com.example.study1;

import com.example.study1.repository.MemberRepository;
import com.example.study1.repository.MemoryMemberRepository;
import com.example.study1.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    //Bean에 직접 class를 등록한다는 의미
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
