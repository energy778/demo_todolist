package ru.veretennikov.todolist.repr;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserRepr {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
//    поле "повторите пароль"
//    нужно только для уровня представления, в БД не идет
//    в том числе из-за этого для слоя БД нужна отдельная сущность Entity, не нужно лепить всё в одну сущность
//    к тому же на один класс персистенс уровня может приходится несколько классов представлений
    private String matchingPassword;

    @Override
    public String toString() {
        return "UserRepr{" +
                ", username='" + username + '\'' +
                '}';
    }

}
