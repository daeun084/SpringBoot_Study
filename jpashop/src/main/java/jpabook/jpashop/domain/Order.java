package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    //다대원 관계 명시
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    //OrderItem class의 order 필드와 맵핑
    private List<OrderItem> orderItems = new ArrayList<>();
    //원래 모든 Entity는 각각 persist해야 함
    //cascade를 사용하면 컬렉션들을 같이 persist해주기 때문에 코드가 줄음

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    //cascade : Order를 저장할 때 delivery도 같이 persist해줌

    private LocalDateTime orderDate; //주문시간
    private OrderStatus status; //주문 상태[ORDER, CANCEL]


    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    //양방향 관계의 경우 연관관계 메서드는 ctrl을 잡고 있는 쪽에 위치하는 것이 좋음
}
