package com.example.Todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Todo {
        @Id @GeneratedValue
        private Long id;
        private String title;
        private boolean completed = false;

}
