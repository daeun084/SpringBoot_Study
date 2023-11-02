package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;


    public void save(Order order) {
        em.persist(order);
    }
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        //JPQL 쿼리를 문자열로 생성 : 복잡하고 번거로움

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);//최대 1000건

        //동적으로 파라미터 설정
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    /**JPA Criteria**/
    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" +
                    orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
        //실무에서 사용하기에는 너무 복잡해서 추천하지 않 + 유지보수하기 너무 힘든 방식
    }

    /**Fetch Join**/
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o form Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
        //한 번 쿼리로 Order 조회 + member, delivery 정보 같이 가져옴
        //member와 delivery 필드가 LAZY이지만 해당 속성을 무시하고 모든 필드의 값을 채워서 반환함

    }

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery("select new " +
                        "jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                " join o.member m" +
                " join o.delivery d",OrderSimpleQueryDto.class)
                .getResultList();
    }
    //query를 직접 짜기 때문에 더 정확한 select값을 얻을 수 있음


    ///api/v3/orders
    //fetch join
    public List<Order> findAllWithItem() {
        return em.createQuery("select distinct o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d" +
                " join fetch o.orderItems oi" +
                    //일대다 조인으로 인해 orderItem을 기준으로 Order객체의 데이터 수가 달라짐
                " join fetch oi.item i"
                , Order.class).getResultList();
        //distinct : 일대다 조인이 있기 떄문에 DB row가 증가함
        //-> order Entity 조회 수도 증가함
        //distinct -> 같은 Entity가 조회되면 애플리케이션에서 중복을 거름(중복 조회 거름)
        //단점 : 페이징 불가
            //hibernate측에서 db에서 모든 데이터를 불러와 애플리케이션에 올려두고 메모리에서 페이징 시도 -> 큰 장애 위험

    }

    /**Version 3_1**/
    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                "select o form Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        //paging 추가
    }
}
