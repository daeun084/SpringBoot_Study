package jpabook.jpashop.controller;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    //Item field
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    //Book의 field
    private String author;
    private String isbn;
}
