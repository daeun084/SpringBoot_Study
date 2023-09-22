package jpabook.jpashop.domain.Item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//싱글테이블 전략 사 : 한 테이블에 다 때려박음
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantiry;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
    //Category 클래스의 items 필드에 맵핑됨
}
