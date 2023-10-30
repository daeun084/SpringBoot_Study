package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    //id 이름이 다르기 때문에 따로 맵핑
    private long id;

    @NotEmpty
    private String name;


    @Embedded
    //내장타입임을 명시 /  Embedded, Embedable 중 하나만 있어도 괜찮음
    private Address address;

    @JsonIgnore //order class와 양방향 관계이기에 Json 무한 루프를 피하기 위해 annotation 적용
    @OneToMany(mappedBy = "member")
    //일대다 관계 명시
    //Order 클래스의 member 필드에 의해 맵핑됨
    //양방향 관계에서 Order.member가 연관관계의 주인이 됨
    private List<Order> orders = new ArrayList<>();
}
