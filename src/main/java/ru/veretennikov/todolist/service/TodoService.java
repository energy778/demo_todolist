package ru.veretennikov.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.veretennikov.todolist.persist.entity.ToDo;
import ru.veretennikov.todolist.persist.entity.User;
import ru.veretennikov.todolist.persist.repo.TodoRepository;
import ru.veretennikov.todolist.repr.TodoRepr;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;

    public Optional<TodoRepr> findById(Long id){
        return repository.findById(id).map(TodoRepr::new);
    }

    public List<TodoRepr> findTodoByUserId(Long userId){
        return repository.findToDoByUserId(userId);
    }

    public void save(TodoRepr todoRepr, User user) {
        repository.save(new ToDo(todoRepr, user));
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }
}
