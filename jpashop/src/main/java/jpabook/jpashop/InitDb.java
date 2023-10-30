package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct //Spring이 제공하는 init 함수
    public void init(){
        initService.dbInit1();
        //애플리케이션 로딩 시점에 해당 함수를 호출
        //spring의 lifecycle에 따라 transaction이 잘 적용되지 않음
        //변동 사항에 대해서 따로 함수를 만들어 호출하는 것을 추천
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;
        //Entity manager을 가지고 바로 샘플 데이터 입력
        public void dbInit1(){
            Member member = createMember("UserA", "서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book", 20000, 100);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            //orderItems들을 가변적으로 넘겨 배열 타입으로 받음
            em.persist(order);
        }

        public void dbInit2(){
            Member member = createMember("UserB", "진주", "2", "2222");
            em.persist(member);

            Book book1 = createBook("SPRING1 Book", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 Book", 40000, 300);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            //orderItems들을 가변적으로 넘겨 배열 타입으로 받음
            em.persist(order);
        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private static Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }
}


