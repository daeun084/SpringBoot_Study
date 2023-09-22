package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
    //Order class의 delivery field에 맵핑됨

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    //EnumType종류 두 가지 : ORDINAL(디폴트 / 숫자 / 중간에 다른 상태가 생기면 망함), STRING(으로 사용해야 함)
    private DeliveryStatus deliveryStatus; //READY, COMP

}
