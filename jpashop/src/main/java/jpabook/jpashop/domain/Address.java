package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
//JPA의 내장타입임
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
    //값 타입은 변경이 불가능하게 Setter을 제공하지 않는 것이 좋음
    //생성자를 통해서만 값을 추가할 수 있도록 설
}
