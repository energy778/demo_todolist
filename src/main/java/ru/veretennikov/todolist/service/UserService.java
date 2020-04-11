package ru.veretennikov.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * получаем данные со слоя repr, создаем объект и перекладываем его в таблицу БД
    */
    public void create(UserRepr userRepr){

        User user = new User();
//        user.setId();                             // сгенерируется spring data
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));

        repository.save(user);

    }

}
