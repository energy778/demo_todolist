package ru.veretennikov.todolist.persist.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
            mappedBy = "user",          // поле таблицы ToDo (есть автокомплишн)
            cascade = CascadeType.ALL,  // в 2 словах сложно. можно почитать например: https://habr.com/ru/post/271115/
            orphanRemoval = true        // типа "ведущее" из 1С: будем удалять те записи, в которых отсутствует значение поля, ответственного за связь
    )
    private List<ToDo> toDoList;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
