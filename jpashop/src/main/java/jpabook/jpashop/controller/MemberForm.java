package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수입니다")
    private String name;
    //name은 필수 항목으로 지정
    //validation을 통해 값이 비어있으면 오류처리해줌

    private String city;
    private String street;
    private String zipcode;

}
