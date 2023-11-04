package jpabook.jpashop.repository.query;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    //Order 관련 Entity는 OrderRepository에서 찾고
    //Entity가 아닌 화면의 쿼리 관련 내용들은 해당 Repository를 사용 -> 분리
    private final EntityManager em;


    public List<OrderQueryDto> findOrderQueryDtos() {
        //apiController에 있는 OrderDto를 사용하지 않고
        // OrderQueryDto를 새로 만든 이유
        //: 존재하던 Dto를 사용하면 repo가 controller를 참조하는 일이 발생하기 때문
        // 그냥 같은 패키지에서 따로 dto 생성해서 사
        List<OrderQueryDto> result = findOrders();
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderid());
            o.setOrderItems(orderItems);
            //o에 orderItems들을 Item으로 set
        });
        //findOrders에서 orderQueryDto의 생성자에는 컬렉션 필드를 집어넣지못함
        //따라서 forEach문을 통해 컬렉션 필드를 넣어줌 -> findOrderItems() 생성
        return result;
    }


    /**Version_5**/
    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderid())
                .collect(Collectors.toList());
        //orders의 id를 뽑아서 List 생성

        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                "from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
        //id List를 인자로 넣어서 orderItem 한 번에 뽑아옴

        //성능 최적화를 위해 map으로 바꿈
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));


        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderid())));
        //쿼리 한 번에 메모리에서 정보를 map으로 가져오고 매칭을 통해 result 값을 세팅 -> 쿼리 총 두 번
        return result;
    }


    /**Version_6**/
    public List<OrderFlatDto> findAllByDto_flat() {
        //OrderFlatDto 생성

        return em.createQuery("select new" +
                        "jpabook.jpashop.repository.query.OrderFlatDto(o.id, m.name, o.orderDate,\n" +
                        "o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                "from Order o" +
                " join o.member m" +
                " join o.delivery d" +
                " join o.orderItems oi" +
                " join oi.item i", OrderFlatDto.class)
                .getResultList();
        //일대다 조인을 했기 때문에 orderItems 개수 만큼 데이터 증가
        //쿼리가 한 번만 나간다는 것이 큰 장점
        //페이징도 가능함 -> Order가 아닌 OrderItem을 기준으로

        //리턴 타입이 OrderFlatDto이기 때문에 스펙에 맞지 않음
    }


    private List<OrderItemQueryDto> findOrderItems(Long orderid) {
        //일대다 관계이기 때문에 별개의 쿼리 작성
        return em.createQuery(
                "select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        "from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderid)
                .getResultList();

    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                "from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }



}
