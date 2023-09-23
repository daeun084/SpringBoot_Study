package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //스프링 빈으로 관리
@RequiredArgsConstructor
public class MemberRepository {
    @PersistenceContext
    private final EntityManager em;
    //JPA가 제공하는 표준 annotation
    //스프링이 EntityManager을 만들어 injection해줌

    public void save(Member member){
        em.persist(member);
        //JPA가 member를 저장
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
        //멤버를 찾아서 반환해줌
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
        //JPQL, 반환타입
        //결과를 List형태로 만들어 반환함

        //SQL : table 대상 쿼리
        //JDQL : Entity 대상 쿼리
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
