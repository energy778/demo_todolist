package ru.veretennikov.todolist.persist.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.veretennikov.todolist.repr.TodoRepr;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "todos")
@NoArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")           // имя колонки, по которой будут соединяться (по факту имя ЭТОЙ колонки)
    private User user;

    @Column
    private LocalDate targetDate;

    public ToDo(TodoRepr todoRepr, User user) {
        this.description = todoRepr.getDescription();
        this.targetDate = todoRepr.getTargetDate();
        this.user = user;
    }

}
