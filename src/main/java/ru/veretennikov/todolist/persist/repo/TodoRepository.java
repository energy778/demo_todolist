package ru.veretennikov.todolist.persist.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.veretennikov.todolist.persist.entity.ToDo;
import ru.veretennikov.todolist.repr.TodoRepr;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<ToDo, Long> {

    @Query("select new ru.veretennikov.todolist.repr.TodoRepr(t) from ToDo as t " +     // благодаря спец конструктору в TodoRepr не нужно перечислять поля
            "where t.user.id = :userId")
    List<TodoRepr> findToDoByUserId(@Param("userId") Long userId);

}
