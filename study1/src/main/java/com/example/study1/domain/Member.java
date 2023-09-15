package com.example.study1.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    //pk, db에서 가져와서 사용하는 값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
