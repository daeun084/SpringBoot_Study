package jpabook.jpashop.domain.Item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
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
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
    //Category 클래스의 items 필드에 맵핑됨


    //==비즈니스 로직==//
    /**재고 수량  증가**/
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**재고 수량  감소**/
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock<0){
            //재고 수량이 0보다 작으면 안됨 -> exception 생성해서 throw
            throw new NotEnoughStockException("need more Stock");
        }
        this.stockQuantity = restStock;
        //Exception이 나지 않는다면 현재 재고수량 변경
    }
}
