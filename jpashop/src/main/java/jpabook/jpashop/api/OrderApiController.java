package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    /**Version_1**/
    //Entity 직접 노출
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            //LAZY 강제 로딩
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
            //orderItems의 아이템들을 가져와서 iterate를 돌리면서 프록시 초기화
        }
        return all;
    }


    /**Version_2**/
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o)) //order객체를 넘겨서 Order type을 OrderDto type으로 변경
                .collect(Collectors.toList());
        return result;
    }

    @Data
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems; //추가된 항목
        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
            //Dto 내부에 Entity가 있으면 안됨 -> orderItem 다시 map해서 ItemDto로 타입 변경 필요
            //Entity에 대한 의존을 완전히 끊어야 함 -> OrderItemDto
        }
    }

    @Data
    static class OrderItemDto{
        private String itemName;
        private int orderPrice;
        private int count;
        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }


    /**Version_3**/
    //Entity를 DTO로 변환
    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3(){
        //Version_2와 orderRepository에 있는 함수만 변경됨
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }



    /**Version_3_1**/
    //ToOne 관게는 모두 fetchJoin
    //컬렉션은 지연로딩으로 조회하나 개별 최적화 옵션 사 : @BatchSize
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit)
    {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        //ToOne 관계는 모두 fetchJoin -> 쿼리 하나가
        //param을 통해 paging 추가

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        //지연로딩으로 컬렉션 조회 -> 쿼리 6
            //orderItems 3번
            //그 내부 Items 3번
        return result;
        //BatchSize를 사용하면 -> 지연로딩 최적화
            //OrderItem 한 번
            //그 내부 Items 한 번만 쿼리 실행
    }
    //version3보다 쿼리 수는 많지만 페이징 가능 + 데이터 전송량 줄어들음 이라는 장점 탑재
    //ToOne이 아닌 관계는 inquery방식으로 풀어낼 수 있음



}
