package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //Persistence부터 시작해서 EntityManager 생성
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin(); //transaction 시작

        try {
            Team team = new Team();
            team.setName("Team A");
            em.persist(team);

            Member member = new Member();
            member.setName("Member1");

            /**객체를 테이블에 맞춤**/
            /*
            member.setTeamId(team.getId());
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = em.find(Team.class, team.getId());
             */


            /** 단방향 연관관계 **/
            //member.setTeam(team);
                //teamid가 아니라 직접 team table과 매핑
                //JPA가 알아서 team 객체에서 PK값을 꺼내 insert 사
            //Team findteam = member.getTeam();


            /** 양방향 연관관계 **/
           // Member findmember = em.find(Member.class, member.getId());
            //List<Member> members = findmember.getTeam().getMembers();
                //member가 속하는 팀의 멤버들을 리스트로 가져옴 -> 양방향


            //team.getMembers().add(member); //연관관계의 주인에 값 설정
            member.setTeam(team);

            em.persist(member);

        }catch (Exception e){
            transaction.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}


//==영속, 비영속 상태 ==//
/*
  List<Member> findMembers = em.createQuery("select m from Member", Member.class).getResultList();

            //비영속
            Member member = new Member();
           // member.setName("helloA");
            // member.setId(1L);

            //영속
            em.persist(member);
                //member가 영속성 컨텍스트에 저장함

            //준영속
            em.detach(member); //영속 컨텍스트에서 member를 분리

            //삭제
            em.remove(member); //DB에서 객체를 지움

            transaction.commit();
            //모든 데이터 변경은 Transaction 내부에서 일어나야 함
            //Transaction commit 시점에서
            //데이터 변경 유무를 확인하고
            //데이터가 바뀐 부분이 있다면 update query를 날려 update

 */
