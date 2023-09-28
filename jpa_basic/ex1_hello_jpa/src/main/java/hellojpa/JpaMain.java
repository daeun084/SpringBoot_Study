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
            List<Member> findMembers = em.createQuery("select m from Member", Member.class).getResultList();

            transaction.commit();
            //모든 데이터 변경은 Transaction 내부에서 일어나야 함
            //Transaction commit 시점에서
            //데이터 변경 유무를 확인하고
            //데이터가 바뀐 부분이 있다면 update query를 날려 update
        }catch (Exception e){
            transaction.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
