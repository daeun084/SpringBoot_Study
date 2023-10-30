package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jpabook.jpashop.domain.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * X to One (collection이 아닌 관계)
 *
 * Order
 * Order -> Member (Many to One)
 * Order -> Delivery (One to One)
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    /**Version_1**/
    @GetMapping("/api/vi/simple-orders")
    public List<Order> ordersV1() {
        //Entity를 그대로 반환함
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        //repo에서 search한 모든 것을 list type으로 반환함

        for (Order order : all) {
            order.getMember().getName();
            //hibernateModule의 forceLazyLoading option Off
            //강제 lazy loading의 또 다른 방법

            //order.getMember() 까지는 프록시 객체임
            //getName()을 사용하기 위해 DB에서 실제 member 객체의 정보를 가져옴
            //-> LAZY 강제 초기화 + 쿼리를 날려서 JPA가 데이터를 가져옴

            order.getDelivery().getAddress(); //강제 LAZY 초기
            //iterate 문을 통해 member와 address의 결과만 출력됨
        }

        return all;
        //무한루프에 빠짐
        //hibernate5Module 사용 필요 + 양방향 관계 필드에 @JsonIgnore 필요
    }


    /**Version_2**/
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        //반환값을 List로 바로 반환하지 않고 Result로 감싸야 하지만 간략한 예제이므로 생략

        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        //orders를 SimpleOrderDto type으로 변환 (.map)
        //Order type param을 통해 생성자를 만들어 필드 초기화
        return result;
        //1+N 문제 발생 : 쿼리가 너무 많이 나가서 문제 발생
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //LAZY 초기화
        }
    }


    /**Version_3**/
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
       List<Order> orders = orderRepository.findAllWithMemberDelivery();
       List<SimpleOrderDto> result = orders.stream()
               .map(o -> new SimpleOrderDto(o))
               .collect(Collectors.toList());
       return result;
    }


    /**Version_4**/
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
     }

}
