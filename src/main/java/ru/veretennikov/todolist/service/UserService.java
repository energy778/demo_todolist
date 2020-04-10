package ru.veretennikov.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.veretennikov.todolist.persist.entity.User;
import ru.veretennikov.todolist.persist.repo.UserRepository;
import ru.veretennikov.todolist.repr.UserRepr;

import javax.transaction.Transactional;

@Service
@Transactional          // работаем с БД
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    /**
     * получаем данные со слоя repr, создаем объект и перекладываем его в таблицу БД
    */
    public void create(UserRepr userRepr){

        User user = new User();
//        user.setId(userRepr.getId());         // сгенерируется
        user.setUsername(userRepr.getUsername());
        user.setPassword(userRepr.getPassword());

        repository.save(user);

    }

}
