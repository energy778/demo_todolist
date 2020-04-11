package ru.veretennikov.todolist.repr;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserRepr {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
//    поле "повторите пароль"
//    поле нужно только для уровня представления, в БД не идет
//    в том числе из-за этого для слоя БД нужна отдельная сущность Entity, не нужно лепить всё в одну сущность
//    к тому же на один класс персистенс уровня может приходится несколько классов представлений
    private String matchingPassword;

    // TODO: 029 29.03.20 временно. ибо нахер светить пароль
    @Override
    public String toString() {
        return "UserRepr{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

}
