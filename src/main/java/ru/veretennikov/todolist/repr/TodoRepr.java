package ru.veretennikov.todolist.repr;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.veretennikov.todolist.persist.entity.ToDo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TodoRepr {

    private Long id;

    @NotEmpty
    private String description;

    private Long userId;
    private String username;

    @NotNull
//    @DateTimeFormat(pattern = "dd-MM-yyyy")       // не взлетает
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    public TodoRepr(ToDo toDo) {
        this.id = toDo.getId();
        this.description = toDo.getDescription();
        this.targetDate = toDo.getTargetDate();
        this.userId = toDo.getUser().getId();
        this.username = toDo.getUser().getUsername();
    }

}
